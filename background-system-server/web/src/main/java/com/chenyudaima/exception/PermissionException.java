package com.chenyudaima.exception;

/**
 * 权限异常
 * @author 沉鱼代码
 * @date 2022/12/30
 */
public class PermissionException extends RuntimeException{
    public PermissionException() {
        super();
    }
    public PermissionException(String message) {
        super(message);
    }
}

