package com.chenyudaima.config;

import com.chenyudaima.exception.RequestHeaderException;
import com.chenyudaima.exception.SecurityException;
import com.chenyudaima.exception.SignException;
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

    @ExceptionHandler(value={
            Throwable.class,
            Exception.class,
            AssertionError.class, //使用assert关键字断言出现的异常
    })
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
        String message;
        if(e instanceof SecurityException) {
            message = e.getMessage();
        }else {
            message = "没有权限访问";
        }
        return new Result<>(403, message,null);
    }


    @ExceptionHandler(value= {IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class,

            SignException.class,
            /**
             * 注解参数校验器异常
             */
            BindException.class,
            ValidationException.class,
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class})
    public Result<?> ValidationException(HttpServletRequest request, Exception e) {
        String message;
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
        }else if(e instanceof SignException) {
            message = e.getMessage();
        }else {
            message = "不合法的参数";
        }

        result.setMessage(message);

        return result;
    }

    /**
     * 请求头异常
     */
    @ExceptionHandler(value = {RequestHeaderException.class})
    public Result<?> RequestHeaderException(HttpServletRequest request, Exception e) {
        return new Result<>(400, "错误的请求头", null);
    }

}
