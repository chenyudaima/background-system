package com.chenyudaima.constant;

/**
 * Http Header常量
 * @author 沉鱼代码
 * @date 2023/2/1
 */
public class HttpHeader {

    /**
     * key 请求头 content-type
     */
    public static final String K_REQUEST_CONTENT_TYPE = "Content-Type";

    public static final String V_CONTENT_TYPE_TEXT_XML = "text/xml";

    /**
     * JSON字符串格式
     */
    public static final String V_CONTENT_TYPE_APPLICATION_JSON = "application/json";

    /**
     * 参数是key=value形式的键值对格式（表单提交数据格式）
     */
    public static final String V_CONTENT_TYPE_APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";


    /**
     * 指定传输数据为二进制类型 （文件传输格式） 一定有boundary这个可选参数
     */
    public static final String V_CONTENT_TYPE_MULTIPART_FORM_DATA = "multipart/form-data";

    public static final String V_CONTENT_TYPE_TEXT_PLAIN = "text/plain";



    /**
     * key 请求头 Authorization 在本系统中是授权的Token
     */
    public static final String K_REQUEST_AUTHORIZATION = "Authorization";


    /**
     * key 请求头 参数签名
     */
    public static final String K_REQUEST_SIGNATURE = "Signature";

    /**
     * key 请求头 客户端信息
     */
    public static final String K_REQUEST_USER_AGENT = "User-Agent";

    /**
     * key 请求头 客户端访问源的地址
     */
    public static final String K_REQUEST_ORIGIN = "Origin";



    /**
     * key 响应头 下载的时候是从该头部获取文件名
     */
    public static final String K_RESPONSE_CONTENT_DISPOSITION = "Content-Disposition";

    /**
     * key 响应头 用于跨域，允许请求的源地址跨域,不能用*,不能写多个，只能写一个精确匹配
     */
    public static final String K_RESPONSE_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

    /**
     * key 响应头 用于跨域,值为true的情况下将响应暴露给前端的js代码,比如js可以获取cookie
     */
    public static final String K_RESPONSE_ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";

    /**
     * key 响应头 用于跨域,允许的请求头 不能用*
     */
    public static final String K_RESPONSE_ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

    /**
     * key 响应头 用于跨域,允许的请求方法 可以用*
     */
    public static final String K_RESPONSE_ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

    /**
     * key 响应头 用于跨域,预检请求的返回结果（Access-Control-Allow-Methods和Access-Control-Allow-Headers提供的信息）可以被缓存多久
     */
    public static final String K_RESPONSE_ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
}
