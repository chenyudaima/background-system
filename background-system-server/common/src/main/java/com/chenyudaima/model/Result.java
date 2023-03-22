package com.chenyudaima.model;


import java.io.Serializable;

/**
 * 接口响应结果
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private T data;

    public Result() {}

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result<?> success() {
        return new Result<>(200,null, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200,null, data);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(500, message,null);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}