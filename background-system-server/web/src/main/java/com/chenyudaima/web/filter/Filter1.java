package com.chenyudaima.web.filter;

import com.chenyudaima.constant.HttpHeaderConstant;
import com.chenyudaima.constant.HttpMethodConstant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求过滤器
 * 增强HttpServletRequest保存流 (在post请求中的json格式需要保存流)
 * @author 沉鱼代码
 * @date 2023/2/6
 */
@WebFilter(urlPatterns = {"/*"})
public class Filter1 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;

        if (
            (servletRequest.getMethod().equals(HttpMethodConstant.POST) ||
                    servletRequest.getMethod().equals(HttpMethodConstant.PUT) ||
                    servletRequest.getMethod().equals(HttpMethodConstant.PATCH) ||
                    servletRequest.getMethod().equals(HttpMethodConstant.DELETE)) &&
                    servletRequest.getContentType().contains(HttpHeaderConstant.V_CONTENT_TYPE_APPLICATION_JSON)
        ) {
            chain.doFilter(new RequestWrapper(servletRequest), response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
