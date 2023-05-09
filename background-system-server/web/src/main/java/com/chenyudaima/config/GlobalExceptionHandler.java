package com.chenyudaima.config;

import com.chenyudaima.exception.request.RequestHeaderException;
import com.chenyudaima.exception.security.SecurityException;
import com.chenyudaima.exception.SignException;
import com.chenyudaima.exception.security.SecurityPathException;
import com.chenyudaima.model.Result;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
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
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * 统一异常处理
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value={Throwable.class})
    public Result<?> Exception(HttpServletRequest request, Exception e) {
        log.error("程序异常:\n {}", e.toString());
        return new Result<>(500, e.getMessage(), null);
    }

    /**
     * sql异常，做日志处理，更方便排查问题
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value={
            DataAccessException.class,
            SQLException.class
    })
    public Result<?> SQLException(HttpServletRequest request, Exception e) {
        log.error("SQL异常:\n{}", e.toString());
        return new Result<>(500, e.getMessage(), null);
    }

    @ExceptionHandler(value= HttpRequestMethodNotSupportedException.class)
    public Result<?> HttpRequestMethodNotSupportedException(HttpServletRequest request, Exception e) {
        log.error("请求方法不支持:\n {}", e.toString());
        return new Result<>(405, "请求方法不支持", null);
    }

    @ExceptionHandler(value=NoHandlerFoundException.class)
    public Result<?> NoHandlerFoundException(HttpServletRequest request, Exception e) {
        log.error("没有找到访问资源:\n {}", e.toString());
        return new Result<>(404, "没有找到访问资源", null);
    }

    @ExceptionHandler(value= SecurityException.class)
    public Result<?> PermissionException(HttpServletRequest request, Exception e) {
        log.error("403权限异常:\n {}", e.toString());
        return new Result<>(403, e.getMessage(),null);
    }

    @ExceptionHandler(value= SecurityPathException.class)
    public Result<?> SecurityPathException(HttpServletRequest request, Exception e) {
        log.error("402权限路径异常:\n {}", e.toString());
        return new Result<>(402, e.getMessage(),null);
    }


    @ExceptionHandler(value= {
            IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class,

            SignException.class,
            /**
             * 注解参数校验器异常
             */
            BindException.class,
            ValidationException.class,
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class
    })
    public Result<?> ValidationException(HttpServletRequest request, Exception e) {
        log.error("参数校验异常:\n {}", e.toString());
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
        log.error("请求头异常:\n {}", e.toString());
        return new Result<>(400, "错误的请求头", null);
    }

}
