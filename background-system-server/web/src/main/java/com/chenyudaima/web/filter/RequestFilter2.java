package com.chenyudaima.web.filter;

import com.chenyudaima.constant.HttpHeader;
import com.chenyudaima.constant.HttpMethod;
import com.chenyudaima.exception.request.RequestHeaderException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求增强过滤器
 * 增强HttpServletRequest保存流 (在post请求中的json格式需要保存流)
 *
 * @author 沉鱼代码
 * @date 2023/2/6
 */
@WebFilter(urlPatterns = {"/*"})
public class RequestFilter2 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;

        if (servletRequest.getContentType() == null && !servletRequest.getMethod().equals(HttpMethod.GET)) {
            throw new RequestHeaderException();
        }

        if (
                (servletRequest.getMethod().equals(HttpMethod.POST) ||
                        servletRequest.getMethod().equals(HttpMethod.PUT) ||
                        servletRequest.getMethod().equals(HttpMethod.PATCH) ||
                        servletRequest.getMethod().equals(HttpMethod.DELETE)) &&
                        servletRequest.getContentType().contains(HttpHeader.V_CONTENT_TYPE_APPLICATION_JSON)
        ) {
            chain.doFilter(new RequestWrapper(servletRequest), response);
        } else {
            chain.doFilter(request, response);
        }
    }

}
