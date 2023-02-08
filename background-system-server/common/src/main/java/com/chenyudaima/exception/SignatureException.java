package com.chenyudaima.exception;

/**
 * 签名异常
 * @author 沉鱼代码
 * @date 2023/2/6
 */
public class SignatureException extends RuntimeException {

    public SignatureException() {
        super();
    }
    public SignatureException(String message) {
        super(message);
    }
}
