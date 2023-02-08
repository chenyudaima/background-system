package com.chenyudaima.config;

import com.chenyudaima.constant.CharSet;
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
        //整个连接池最大连接数
        HTTP_CLIENT_CONNECTION_MANAGER.setMaxTotal(50);

        //每路由最大连接数，默认值是2
        HTTP_CLIENT_CONNECTION_MANAGER.setDefaultMaxPerRoute(5);
    }

    /**
     * 字符集
     */
    public static final String ENCODING = CharSet.UTF8;


    /**
     * 连接超时（单位毫秒）
     */
    public static final int CONNECTION_TIMEOUT = 50000;

    /**
     * 响应超时（单位毫秒）
     */
    public static final int RESPONSE_TIMEOUT = 50000;


}
