package com.chenyudaima.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口响应结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success() {
        return new Result<>(200,"成功",null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200,"成功",data);
    }
    public static <T> Result<T> error() {
        return new Result<>(500,"失败",null);
    }
    public static <T> Result<T> error(String message) {
        return new Result<>(500,message,null);
    }
}