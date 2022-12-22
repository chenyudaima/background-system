package com.chenyudaima.web.config;

import com.chenyudaima.web.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 沉鱼代码
 * @date 2022/12/21
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private SecurityInterceptor securityInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 拦截器的添加顺序就是执行顺序
         * 所以请求先执行loginInterceptor，再执行securityInterceptor
         */

        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/*")
                .excludePathPatterns();

    }
}
