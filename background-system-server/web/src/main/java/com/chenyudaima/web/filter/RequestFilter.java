package com.chenyudaima.web.filter;

import com.chenyudaima.constant.HttpHeader;
import com.chenyudaima.constant.HttpMethod;
import com.chenyudaima.exception.RequestHeaderException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求过滤器
 * 增强HttpServletRequest保存流 (在post请求中的json格式需要保存流)
 *
 * @author 沉鱼代码
 * @date 2023/2/6
 */
@WebFilter(urlPatterns = {"/*"})
public class RequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        //
        //if(servletRequest.getContentType() != null) {
        //    throw new RequestHeaderException("没有Content-Type请求头");
        //}

        if (
                (servletRequest.getMethod().equals(HttpMethod.POST) ||
                        servletRequest.getMethod().equals(HttpMethod.PUT) ||
                        servletRequest.getMethod().equals(HttpMethod.PATCH) ||
                        servletRequest.getMethod().equals(HttpMethod.DELETE)) &&
                        (
                                servletRequest.getContentType().contains(HttpHeader.V_CONTENT_TYPE_APPLICATION_JSON)
                        )

        ) {
            chain.doFilter(new RequestWrapper(servletRequest), response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
