package com.chenyudaima.config;

import com.chenyudaima.web.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 沉鱼代码
 * @date 2022/12/21
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

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

    /**
     * 提升MappingJackson2HttpMessageConverter的优先级
     * 防止统一包装处理
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }
}
