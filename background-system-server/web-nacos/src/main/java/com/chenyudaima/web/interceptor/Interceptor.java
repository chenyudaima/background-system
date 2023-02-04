package com.chenyudaima.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Interceptor implements HandlerInterceptor {

    /**
     * 拦截路径
     */
    public abstract String[] getAddPathPatterns();


    /**
     * 排除路径
     */
    public abstract String[] getExcludePathPatterns();


    /**
     * 拦截器优先级 数字越大越优先
     */
    public abstract int priority();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
