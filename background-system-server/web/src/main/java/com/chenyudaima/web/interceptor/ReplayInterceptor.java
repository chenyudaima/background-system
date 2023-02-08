package com.chenyudaima.web.interceptor;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 防重放拦截器
 * @author 沉鱼代码
 * @date 2023/2/7
 */
@Component
public class ReplayInterceptor extends Interceptor {
    @Override
    public String[] getAddPathPatterns() {
        return new String[0];
    }

    @Override
    public String[] getExcludePathPatterns() {
        return new String[0];
    }

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        return true;
    }
}
