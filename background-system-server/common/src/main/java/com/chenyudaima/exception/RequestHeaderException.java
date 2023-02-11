package com.chenyudaima.exception;

/**
 * 请求头异常
 * @author 沉鱼代码
 * @date 2023/2/9
 */
public class RequestHeaderException extends RuntimeException {
    public RequestHeaderException() {}

    public RequestHeaderException(String message) {
        super(message);
    }
}
