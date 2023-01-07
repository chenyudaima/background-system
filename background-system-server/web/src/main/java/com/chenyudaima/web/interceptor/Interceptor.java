package com.chenyudaima.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

public interface Interceptor extends HandlerInterceptor {

    /**
     * 拦截路径
     */
    String[] getAddPathPatterns();


    /**
     * 排除路径
     */
    String[] getExcludePathPatterns();


    /**
     * 拦截器优先级 数字越大越优先
     */
    int priority();
}
