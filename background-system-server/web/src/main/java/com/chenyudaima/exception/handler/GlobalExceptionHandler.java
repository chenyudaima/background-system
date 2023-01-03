package com.chenyudaima.exception.handler;

import com.chenyudaima.exception.SecurityException;
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

    @ExceptionHandler(value= SecurityException.class)
    public Result<?> PermissionException(HttpServletRequest request, Exception e) {
        return new Result<>(403,e.getMessage(),null);
    }

}
