package com.chenyudaima.config;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * HttpUtil配置
 */
public class HttpConfig {
    /**
     * 连接池
     */
    public static final PoolingHttpClientConnectionManager HTTP_CLIENT_CONNECTION_MANAGER = new PoolingHttpClientConnectionManager();

    /**
     * 初始化连接池
     */
    static {
        HTTP_CLIENT_CONNECTION_MANAGER.setMaxTotal(50);//整个连接池最大连接数
        HTTP_CLIENT_CONNECTION_MANAGER.setDefaultMaxPerRoute(5);//每路由最大连接数，默认值是2
    }

    /**
     * 字符集
     */
    public static final String ENCODING = "UTF-8";


    /**
     * 连接超时（单位毫秒）
     */
    public static final int CONNECTION_TIMEOUT = 50000;

    /**
     * 响应超时（单位毫秒）
     */
    public static final int RESPONSE_TIMEOUT = 50000;


}
