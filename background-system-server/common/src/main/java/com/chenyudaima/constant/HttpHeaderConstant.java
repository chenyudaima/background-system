package com.chenyudaima.constant;

/**
 * Http Header常量
 * @author 沉鱼代码
 * @date 2023/2/1
 */
public class HttpHeaderConstant {

    /**
     * key 请求头 content-type
     */
    public static final String K_REQUEST_HEADER_CONTENT_TYPE = "content-type";

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
     * 指定传输数据为二进制类型 （文件传输格式）
     */
    public static final String V_CONTENT_TYPE_MULTIPART_FORM_DATA = "multipart/form-data";



    /**
     * key 请求头 Authorization 在本系统中是授权的Token
     */
    public static final String K_REQUEST_HEADER_AUTHORIZATION = "Authorization";

    /**
     * 自定义请求头 参数签名
     */
    public static final String K_REQUEST_HEADER_SIGNATURE = "Signature";




    /**
     * key 响应头 content-disposition
     */
    public static final String K_RESPONSE_HEADER_CONTENT_DISPOSITION = "content-disposition";
}
