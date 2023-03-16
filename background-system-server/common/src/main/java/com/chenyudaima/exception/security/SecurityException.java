package com.chenyudaima.exception.security;

/**
 * 权限异常
 * @author 沉鱼代码
 * @date 2022/12/30
 */
public class SecurityException extends RuntimeException {
    public SecurityException() {
        super();
    }
    public SecurityException(String message) {
        super(message);
    }
}

