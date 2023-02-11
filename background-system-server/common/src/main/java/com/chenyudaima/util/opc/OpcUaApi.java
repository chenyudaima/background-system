package com.chenyudaima.util.opc;

import com.chenyudaima.model.OpcUaConfig;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;

/**
 * OpcUa Api
 * @author 沉鱼代码
 * @date 2023/2/10
 */
public class OpcUaApi {
    private final OpcUaConfig opcUaConfig;

    private final OpcUaClient opcUaClient;

    public static OpcUaApi create(OpcUaConfig opcUaConfig) throws Exception {
        return new OpcUaApi(opcUaConfig);
    }


    public OpcUaApi(OpcUaConfig opcUaConfig) throws Exception {
        this.opcUaConfig = opcUaConfig;
        opcUaClient = OpcUaUtil.createOpcUaClient(opcUaConfig);
    }


    /**
     * 读取指定节点值
     * @param nodePath 节点路径 xx.xx.xx
     * @return 节点值
     */
    public DataValue readValue(String nodePath) throws Exception {
        try {
            //经过测试 maxAge 设置为0是效率最高的
            return opcUaClient.readValue(0, TimestampsToReturn.Neither, new NodeId(opcUaConfig.getNamespaceIndex(), nodePath)).get();
        }catch (Exception e) {

            //如果发生异常则关闭连接，重新连接，再尝试一次获取值
            opcUaClient.disconnect().get();
            opcUaClient.connect().get();
            return opcUaClient.readValue(0, TimestampsToReturn.Neither, new NodeId(opcUaConfig.getNamespaceIndex(), nodePath)).get();
        }

    }

}
