package com.chenyudaima.exception.request;

/**
 * 请求异常
 * @author 沉鱼代码
 * @date 2023/3/16
 */
public class RequestException extends RuntimeException {

    public RequestException() {}

    public RequestException(String message) {
        super(message);
    }
}
