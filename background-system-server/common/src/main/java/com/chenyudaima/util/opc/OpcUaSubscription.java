package com.chenyudaima.util.opc;

import com.chenyudaima.config.OpencvConfig;
import com.chenyudaima.config.ThreadPoolConfig;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscriptionManager;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

/**
 * OpcUa订阅
 * @author 沉鱼代码
 * @date 2023/2/10
 */
public class OpcUaSubscription {
    private static final Logger log = LoggerFactory.getLogger(OpcUaSubscription.class);
    private final OpcUaConfig opcUaConfig;

    /**
     * OpcUa连接对象 每次执行connect的时候会创建
     */
    private OpcUaClient opcUaClient;

    /**
     * Ua订阅 每次执行connect的时候会创建
     */
    private UaSubscription subscription;

    /**
     * 当前订阅对象心跳
     */
    private int heartbeat = 0;

    /**
     * key 获取订阅节点的回调函数
     * value 节点的订阅回调函数
     * key里面的节点对应的回调函数是value
     */
    private final Map<Supplier<Collection<OpcNode>>, BiConsumer<OpcNode, DataValue>> nodeMap = new HashMap<>();


    /**
     * 当前订阅对象的线程池
     */
    private final ThreadPoolExecutor executor;

    private OpcUaSubscription(OpcUaConfig opcUaConfig, ThreadPoolExecutor executor) throws Exception {
        this.opcUaConfig = opcUaConfig;
        this.executor = executor;
        if(opcUaConfig.getSubscriptionListener() == null) {
            opcUaConfig.setSubscriptionListener(new UaSubscriptionManager.SubscriptionListener() {
            });
        }

        if(opcUaConfig.getHeartbeatInterval() == 0) {
            opcUaConfig.setHeartbeatInterval(1000);
        }

        if(opcUaConfig.getHeartbeatAbnormal() == 0) {
            opcUaConfig.setHeartbeatAbnormal(300);
        }

    }

    /**
     * 创建OpcUa订阅对象
     * @param opcUaConfig OpcUa配置
     * @param executor 线程池
     * @return OpcUa订阅对象
     */
    public static OpcUaSubscription create(OpcUaConfig opcUaConfig, ThreadPoolExecutor executor) throws Exception {
        return new OpcUaSubscription(opcUaConfig, executor);
    }

    /**
     * 连接 连接失败会无限重试
     */
    private void connect() {
        try {
            //关闭上一次的连接
            opcUaClient.disconnect().get();
        } catch (Exception e) {

        }

        try {
            //创建连接
            opcUaClient = OpcUaUtil.createOpcUaClient(opcUaConfig);

            //发起连接
            opcUaClient.connect().get();

            //创建订阅主体
            opcUaClient.getSubscriptionManager().addSubscriptionListener(opcUaConfig.getSubscriptionListener());

            //创建UA订阅对象
            subscription = opcUaClient.getSubscriptionManager().createSubscription(opcUaConfig.getRequestedPublishingInterval()).get();

        } catch (Exception e) {
            log.error("OpcUa连接失败:{},地址:{},等待{}秒继续...",e.getMessage(), opcUaConfig.getOpcUaUrl(), opcUaConfig.getConnect());
            try {
                Thread.sleep(opcUaConfig.getConnect());
            } catch (Exception e1) {

            }
            connect();
        }
    }

    /**
     * 重连，在心跳异常的时候执行
     */
    private void reconnect() {
        try {
            //发起连接
            connect();

            //重置心跳
            heartbeat = 0;

            //订阅节点
            nodeMap.forEach((supplier, biConsumer) -> {
                Collection<OpcNode> opcNodes = supplier.get();
                for (OpcNode node : opcNodes) {
                    executor.execute(() -> {
                        node.setBiConsumer(biConsumer);
                        subscription(node);
                    });

                }
            });

        }catch (Exception e) {
            log.error("OpcUa重连失败,{},{}", opcUaConfig.getOpcUaUrl(),e.getMessage());
            reconnect();
        }
    }

    /**
     * 开启心跳检测线程 （在run的时候执行，全局只执行一次）
     */
    private void heartbeat() {
        executor.execute(() -> {
            while (true) {
                if (heartbeat > opcUaConfig.getHeartbeatAbnormal()) {
                    log.error("OpcUa心跳异常，因为过了" + opcUaConfig.getHeartbeatAbnormal() + "秒没有执行回调函数，正在发起重连...");
                    reconnect();
                }

                heartbeat ++;

                try {
                    Thread.sleep(opcUaConfig.getHeartbeatInterval());
                } catch (InterruptedException e) {
                    log.error("心跳线程执行sleep失败，{}", e.getMessage());
                }
            }
        });
    }

    /**
     * 节点订阅
     * @param node 节点
     */
    private void subscription(OpcNode node) {
        UaMonitoredItem item = null;

        //节点id
        ReadValueId readValueId = new ReadValueId(
                new NodeId(opcUaConfig.getNamespaceIndex(), node.getNodePath()),
                AttributeId.Value.uid(),
                null,
                QualifiedName.NULL_VALUE
        );

        //监测参数
        MonitoringParameters parameters = new MonitoringParameters(
                subscription.nextClientHandle(),
                1000.0,     // 采样间隔
                null,                 // 过滤器，null表示使用默认值
                uint(10),            // 队列大小
                true          // 抛弃古老的
        );

        try {
            //创建监测请求
            MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(
                    readValueId, MonitoringMode.Reporting, parameters
            );

            item = subscription.createMonitoredItems(TimestampsToReturn.Both, Collections.singletonList(request), (x, v) -> {
                x.setValueConsumer((uaMonitoredItem, dataValue) -> {
                    executor.execute(() -> {
                        //重置心跳
                        heartbeat = 0;

                        //排除Bad或者null的节点值
                        if(dataValue.getValue().isNull() || dataValue.getStatusCode().isBad()) {
                            return;
                        }

                        //对节点上锁 (没抢到的进入等待状态)
                        node.getReentrantLock().lock();

                        try {
                            if (node.getDataValue() != null) {
                                //判断节点值是否发生改变 没有发生改变则不执行
                                if(node.getDataValue().getValue().getValue().toString().equals(dataValue.getValue().getValue().toString())) {
                                    return;
                                }
                            }

                            //发生改变
                            node.setDataValue(dataValue);

                        }finally {
                            node.getReentrantLock().unlock();
                        }

                        //执行回调函数
                        node.getBiConsumer().accept(node, dataValue);
                    });
                });
            }).get().get(0);

            node.setUaMonitoredItem(item);

            if(item.getStatusCode().isGood()) {
                log.info("OpcUa节点订阅成功,{}",node.getNodePath());
            }

        } catch (Exception e) {
            log.error("OpcUa节点{}，订阅失败,{}", node.getNodePath(), e.getMessage());
        }
    }

    /**
     * 运行 （全局只需执行一次）
     */
    public void run() {
        executor.execute(() -> {
            //发起连接
            connect();

            //开启心跳线程
            heartbeat();

            //订阅节点
            nodeMap.forEach((supplier, biConsumer) -> {
                Collection<OpcNode> opcNodes = supplier.get();
                for (OpcNode node : opcNodes) {
                    executor.execute(() -> {
                        node.setBiConsumer(biConsumer);
                        subscription(node);
                    });

                }
            });

        });
    }


    /**
     * 添加订阅节点
     * @param supplier 获取订阅节点的回调函数，Collection中的元素可以是OpcNode的子类
     * @param biConsumer 节点的订阅回调函数（节点值不是bad，不为null，和上次的值不同，才会执行） 注意：在回调函数中，可以把OpcNode强转为子类来获取元素
     */
    public void add(Supplier<Collection<OpcNode>> supplier, BiConsumer<OpcNode, DataValue> biConsumer) {
        nodeMap.put(supplier, biConsumer);
    }


}
