package com.chenyudaima.config;

import com.baomidou.mybatisplus.extension.api.R;
import com.chenyudaima.exception.SecurityException;
import com.chenyudaima.exception.SignatureException;
import com.chenyudaima.model.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.stream.Collectors;

/**
 * 统一异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public Result<?> Exception(HttpServletRequest request, Exception e) {
        e.printStackTrace();
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


    @ExceptionHandler(value= {IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class,

            SignatureException.class,
            /**
             * 注解参数校验器异常
             */
            BindException.class,
            ValidationException.class,
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class})
    public Result<?> ValidationException(HttpServletRequest request, Exception e) {
        String message;
        e.printStackTrace();
        Result<?> result = new Result<>();
        result.setCode(401);

        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            message = ex.getBindingResult().getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining("; "));

        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException)e;
            message = ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("; "));

        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            message = ex.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining("; "));
        }else if(e instanceof SignatureException) {
            message = e.getMessage();
        }else {
            message = "不合法的参数";
        }

        result.setMessage(message);

        return result;
    }

}
