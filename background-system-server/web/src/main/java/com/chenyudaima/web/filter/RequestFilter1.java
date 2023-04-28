package com.chenyudaima.web.filter;

import com.chenyudaima.constant.HttpHeader;
import com.chenyudaima.constant.HttpMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 跨域过滤器
 * postman等工具不会跨域，所以别人调用接口不会发生跨域
 * 是浏览器限制了这些请求的才会发生跨域
 * @author 沉鱼代码
 * @date 2023/3/1
 */
@WebFilter(urlPatterns = {"/*"})
public class RequestFilter1 implements Filter {

    /**
     * 允许请求的源地址跨域
     */
    private static final List<String> ALLOW_ORIGINS = new ArrayList<>();

    static {
        // 后面不用带/
        ALLOW_ORIGINS.add("http://localhost:3000");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        //获取请求的源地址
        String origin = servletRequest.getHeader(HttpHeader.K_REQUEST_ORIGIN);

        //匹配到允许的则添加，否则不添加，不添加就会跨域失败，更安全
        if(ALLOW_ORIGINS.contains(origin)) {
            servletResponse.setHeader(HttpHeader.K_RESPONSE_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        }

        servletResponse.setHeader(HttpHeader.K_RESPONSE_ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");

        servletResponse.setHeader(HttpHeader.K_RESPONSE_ACCESS_CONTROL_ALLOW_HEADERS, "signature,authorization");

        servletResponse.setHeader(HttpHeader.K_RESPONSE_ACCESS_CONTROL_ALLOW_METHODS, "*");

        servletResponse.setHeader(HttpHeader.K_RESPONSE_ACCESS_CONTROL_MAX_AGE, "3600");

        //预检请求需要快速返回
        if(servletRequest.getMethod().equals(HttpMethod.OPTIONS)) {
            return;
        }

        chain.doFilter(request, response);
    }

}
