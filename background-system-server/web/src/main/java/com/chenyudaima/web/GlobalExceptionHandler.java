package com.chenyudaima.web;

import com.chenyudaima.model.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 * @author 沉鱼代码
 * @date 2022/12/21
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public Result<?> Exception(HttpServletRequest request, Exception e) {
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(value=RuntimeException.class)
    public Result<?> RuntimeException(HttpServletRequest request, Exception e) {
        return Result.error(e.getMessage());
    }
}
