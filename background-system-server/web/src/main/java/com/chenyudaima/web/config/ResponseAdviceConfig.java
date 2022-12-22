package com.chenyudaima.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 沉鱼代码
 * @date 2022/12/21
 */
@Configuration
public class ResponseAdviceConfig implements WebMvcConfigurer {


    /**
     * 提升MappingJackson2HttpMessageConverter的优先级
     * 防止统一包装处理
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }
}
