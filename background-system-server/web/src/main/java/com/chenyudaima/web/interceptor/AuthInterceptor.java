package com.chenyudaima.web.interceptor;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器
 *
 * @author 沉鱼代码
 * @date 2023/2/27
 */
@Component
public class AuthInterceptor extends Interceptor {
    @Override
    public String[] getAddPathPatterns() {
        return new String[]{
                "/**"
        };
    }

    @Override
    public String[] getExcludePathPatterns() {
        return new String[]{
                "/login/**",
                "/test/**"
        };
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前请求的用户是否有请求该路径的权限
        System.out.println(request.getServletPath());

        return true;
    }
}
