package com.chenyudaima.config;


import com.chenyudaima.util.SpringUtil;
import com.chenyudaima.web.interceptor.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * web配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 给Controller加上前缀 （当作项目部署路径）
     */
    private static final String PATH = "/system";

    /**
     * 配置拦截器 （会自动查找实现Interceptor接口的类进行配置）
     */
    public void addInterceptors(InterceptorRegistry registry) {
        Set<Interceptor> set = new TreeSet<>((o1, o2) -> o2.priority() - o1.priority());

        SpringUtil.getApplicationContext().getBeansOfType(Interceptor.class)
                .forEach((k,v) -> set.add(v));

        /**
         * 拦截器的添加顺序就是执行顺序
         */
        set.forEach((interceptor ->
            registry.addInterceptor(interceptor)
                    .addPathPatterns(interceptor.getAddPathPatterns())
                    .excludePathPatterns(processingPath(interceptor.getExcludePathPatterns()))
        ));
    }


    /**
     * 转换器优先级提升（解决统一响应格式，响应字符串出现错误的问题）
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }


    /**
     * Controller统一前缀
     * 不过在拦截需要加上这个前端
     * 比如 "/login" 需要改成“/system/login”
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //给有RestController注解的控制器加上PATH前缀
        configurer.addPathPrefix(PATH, c -> c.isAnnotationPresent(RestController.class));

        WebMvcConfigurer.super.configurePathMatch(configurer);
    }


    private List<String> processingPath(String... paths) {
        if(paths == null || paths.length == 0) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (String path : paths) {
            list.add(PATH + path);
        }
        return list;
    }
}