package com.chenyudaima.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 沉鱼代码
 * @date 2022/12/21
 */

@Component
public class SecurityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果用户没有token或者验证token失败则抛异常

        //throw new RuntimeException("拦截");
        return true;
    }
}
