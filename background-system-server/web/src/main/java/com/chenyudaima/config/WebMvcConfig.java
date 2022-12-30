package com.chenyudaima.config;

import com.chenyudaima.web.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private SecurityInterceptor securityInterceptor;

    /**
     * 配置拦截器
     */
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 拦截器的添加顺序就是执行顺序
         * 所以请求先执行loginInterceptor，再执行securityInterceptor
         */
        //registry.addInterceptor(loginInterceptor)
        //        .addPathPatterns("/*")
        //        .excludePathPatterns("/login");

        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/*")
                .excludePathPatterns("/login");
    }


    /**
     * 转换器优先级提升
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }

}