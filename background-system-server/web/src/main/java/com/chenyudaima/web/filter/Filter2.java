package com.chenyudaima.web.filter;



import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 文件过滤器
 * @author 沉鱼代码
 * @date 2023/2/7
 */
@WebFilter(urlPatterns = {"/*"})
public class Filter2 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request,response);
    }
}
