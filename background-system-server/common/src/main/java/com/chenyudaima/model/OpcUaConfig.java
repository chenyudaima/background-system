package com.chenyudaima.model;


import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscriptionManager;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * Opc连接属性（包括Http，OpcUa）
 */
public class OpcUaConfig {
    /**
     * opc连接地址
     */
    private String opcUaUrl;

    /**
     * 账号
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 命名空间索引
     */
    private int namespaceIndex = 2;

    /**
     * 请求超时时间（毫秒）
     */
    private int requestTimeout = 5000;

    /**
     * 连接失败多久发起一次连接 （毫秒）
     */
    private int connect = 5000;

    /**
     * 请求的发布间隔
     */
    private double requestedPublishingInterval = 100.0;

    /**
     * 当心跳达到多少开始重连
     */
    private int heartbeatAbnormal;

    /**
     * 间隔多久心跳 + 1 （单位毫秒）
     */
    private int heartbeatInterval;

    /**
     * 订阅监听器 可用于重置心跳
     */
    private UaSubscriptionManager.SubscriptionListener subscriptionListener;


    public String getOpcUaUrl() {
        return opcUaUrl;
    }

    public void setOpcUaUrl(String opcUaUrl) {
        this.opcUaUrl = opcUaUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNamespaceIndex() {
        return namespaceIndex;
    }

    public void setNamespaceIndex(int namespaceIndex) {
        this.namespaceIndex = namespaceIndex;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public int getConnect() {
        return connect;
    }

    public void setConnect(int connect) {
        this.connect = connect;
    }

    public double getRequestedPublishingInterval() {
        return requestedPublishingInterval;
    }

    public void setRequestedPublishingInterval(double requestedPublishingInterval) {
        this.requestedPublishingInterval = requestedPublishingInterval;
    }

    public int getHeartbeatAbnormal() {
        return heartbeatAbnormal;
    }

    public void setHeartbeatAbnormal(int heartbeatAbnormal) {
        this.heartbeatAbnormal = heartbeatAbnormal;
    }

    public int getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(int heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    public UaSubscriptionManager.SubscriptionListener getSubscriptionListener() {
        return subscriptionListener;
    }

    public void setSubscriptionListener(UaSubscriptionManager.SubscriptionListener subscriptionListener) {
        this.subscriptionListener = subscriptionListener;
    }
}
