package com.chenyudaima.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.chenyudaima.constant.RequestAttribute;
import com.chenyudaima.mapper.SysInterfaceRequestLogMapper;
import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysInterfaceRequestLog;
import com.chenyudaima.util.HttpDataUtil;
import com.chenyudaima.util.Snowflake;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 接口请求日志切面
 * @author 沉鱼代码
 * @date 2023/3/22
 */
@Aspect
@Component
@RequiredArgsConstructor
public class InterfaceRequestLogAspect {

    private final SysInterfaceRequestLogMapper sysInterfaceRequestLogMapper;

    private final HttpServletRequest request;

    private final Snowflake snowflake;

    /**
     * 代理的控制器，如果只需要单个控制器的日志，可以使用自定义注解
     */
    @Pointcut(value = "execution(public * com.chenyudaima.web.controller..*.*(..))")
    public void include() {
    }

    /**
     * 排除测试控制器
     */
    @Pointcut(value = "execution(public * com.chenyudaima.web.controller.TestController.*(..))")
    public void exclude() {
    }

    @Around("include() && !exclude()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long time = System.currentTimeMillis();

        //Signature signature = point.getSignature();
        //String name = signature.getName();//方法名
        //String className = signature.getDeclaringType().getName();//类名

        SysInterfaceRequestLog sysInterfaceRequestLog = new SysInterfaceRequestLog();
        sysInterfaceRequestLog.setSpendTime(System.currentTimeMillis() - time);
        sysInterfaceRequestLog.setRequestUrl(request.getServletPath());
        sysInterfaceRequestLog.setRequestMethod(request.getMethod());

        TreeMap<String, String> params = HttpDataUtil.getParams(request);
        if(params.size() > 0) {
            sysInterfaceRequestLog.setRequestParam(JSON.toJSONString(params, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue));
        }

        sysInterfaceRequestLog.setId(String.valueOf(snowflake.nextId()));

        Optional.ofNullable(request.getAttribute(RequestAttribute.CLAIMS)).ifPresent(claims -> {
            sysInterfaceRequestLog.setUserId(((Claims)claims).getId());
        });

        sysInterfaceRequestLog.setRequestTime(new Date());

        sysInterfaceRequestLog.setRequestIp(request.getRemoteAddr());

        //执行控制器并返回结果
        try {
            Object result = point.proceed();

            if(result == null) {
            }else if(result instanceof Result) {
                String json = JSON.toJSONString(result, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
                if(json.length() > 1024) {
                    json = json.substring(0, 1024);
                }
                sysInterfaceRequestLog.setResponseResult(json);

            }else {
                sysInterfaceRequestLog.setResponseResult(result.toString());
            }
            sysInterfaceRequestLog.setStatus(1);
            sysInterfaceRequestLogMapper.insert(sysInterfaceRequestLog);
            return result;
        }catch (Exception e) {
            sysInterfaceRequestLog.setStatus(0);
            sysInterfaceRequestLog.setResponseResult(e.toString());
            sysInterfaceRequestLogMapper.insert(sysInterfaceRequestLog);
            throw e;
        }
    }

}
