//package com.chenyudaima.web.filter;
//
//import com.chenyudaima.constant.HttpMethod;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * 跨域过滤器 只过滤OPTIONS请求
// * @author 沉鱼代码
// * @date 2023/3/1
// */
//@WebFilter(urlPatterns = {"/*"})
//public class RequestFilter1 implements Filter {
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest servletRequest = (HttpServletRequest) request;
//        HttpServletResponse servletResponse = (HttpServletResponse) response;
//
//        if(servletRequest.getMethod().equals(HttpMethod.OPTIONS)) {
//            servletResponse.setHeader("access-control-allow-origin","*");
//            servletResponse.setHeader("Access-Control-Allow-Credentials", "true");
//            servletResponse.setHeader("access-control-allow-methods", "*");
//            servletResponse.setHeader("Access-Control-Max-Age", "3600");
//
//            servletResponse.setHeader("access-control-allow-headers", "*");
//            servletResponse.setStatus(200);
//            response.getWriter().println("ok");
//            return;
//        }
//
//
//        chain.doFilter(request, response);
//
//
//
//
//    }
//}
