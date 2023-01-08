package com.chenyudaima.config;

import com.chenyudaima.exception.SecurityException;
import com.chenyudaima.model.Result;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public Result<?> Exception(HttpServletRequest request, Exception e) {
        return new Result<>(500, e.getMessage(), null);
    }

    @ExceptionHandler(value= HttpRequestMethodNotSupportedException.class)
    public Result<?> HttpRequestMethodNotSupportedException(HttpServletRequest request, Exception e) {
        return new Result<>(405, "请求方法不支持", null);
    }

    @ExceptionHandler(value=NoHandlerFoundException.class)
    public Result<?> NoHandlerFoundException(HttpServletRequest request, Exception e) {
        return new Result<>(404, "没有找到访问资源", null);
    }

    @ExceptionHandler(value= SecurityException.class)
    public Result<?> PermissionException(HttpServletRequest request, Exception e) {
        return new Result<>(403, "没有权限访问",null);
    }

    @ExceptionHandler(value= {IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})

    public Result<?> IllegalArgumentException(HttpServletRequest request, Exception e) {
        return new Result<>(401,"不合法的参数",null);
    }

}
