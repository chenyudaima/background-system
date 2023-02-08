package com.chenyudaima.exception;

/**
 * 参数签名异常
 * @author 沉鱼代码
 * @date 2023/2/6
 */
public class SignException extends RuntimeException {

    public SignException() {
        super();
    }
    public SignException(String message) {
        super(message);
    }
}
