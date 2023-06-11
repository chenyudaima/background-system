package com.chenyudaima.util.opc;

import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;

/**
 * 节点基础属性
 * @author 沉鱼代码
 * @date 2023/2/10
 */
public class OpcNode {
    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 当前节点路径
     */
    private String nodePath;

    /**
     * 节点描述
     */
    private String nodeDescription;

    /**
     * 节点参数
     */
    private Map<String,Object> parameters;

    /**
     * 父节点
     */
    private OpcNode parentNode;

    /**
     * 子节点
     */
    private List<OpcNode> childNodes;


    /**
     * 节点回调方法
     */
    private BiConsumer<OpcNode, DataValue> biConsumer;

    /**
     * Ua监控
     */
    private UaMonitoredItem uaMonitoredItem;

    ///**
    // * 当前节点锁
    // */
    //private final ReentrantLock reentrantLock = new ReentrantLock();



    ///**
    // * 用于过滤重复节点值
    // */
    //private DataValue dataValue;



    public UaMonitoredItem getUaMonitoredItem() {
        return uaMonitoredItem;
    }

    public void setUaMonitoredItem(UaMonitoredItem uaMonitoredItem) {
        this.uaMonitoredItem = uaMonitoredItem;
    }

    public BiConsumer<OpcNode, DataValue> getBiConsumer() {
        return biConsumer;
    }

    public void setBiConsumer(BiConsumer<OpcNode, DataValue> biConsumer) {
        this.biConsumer = biConsumer;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeDescription() {
        return nodeDescription;
    }

    public void setNodeDescription(String nodeDescription) {
        this.nodeDescription = nodeDescription;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getNodePath() {
        return nodePath;
    }

    public void setNodePath(String nodePath) {
        this.nodePath = nodePath;
    }

    public OpcNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(OpcNode parentNode) {
        this.parentNode = parentNode;
    }

    public List<OpcNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<OpcNode> childNodes) {
        this.childNodes = childNodes;
    }

}
