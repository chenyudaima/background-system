package com.chenyudaima.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 1 先查看是否有权限访问
 * 2 再查看参数是否被篡改
 * 3 参数是否被重放
 * -2 身份是否合法
 */
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
     * 拦截器优先级 数字越小越优先
     */
    public abstract int priority();

    /**
     * 拦截处理
     * @param request 请求对象
     * @param response 响应对象
     * @param handler
     * @return true为放行，false为拦截（false拦截之后不会有响应Result，也可以用抛异常的方式进行全局异常处理并响应Result）
     * @throws Exception
     */
    @Override
    public abstract boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;


}
