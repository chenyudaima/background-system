package com.chenyudaima.exception.security;

/**
 * 没有权限访问路径异常
 * @author 沉鱼代码
 * @date 2023/3/15
 */
public class SecurityPathException extends  SecurityException {
    public SecurityPathException() {}

    public SecurityPathException(String message) {
        super(message);
    }
}
