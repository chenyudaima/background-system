package com.chenyudaima.config;

import com.chenyudaima.util.JwtUtil;
import com.chenyudaima.util.SpringBeanUtil;
import com.chenyudaima.web.interceptor.Interceptor;
import com.chenyudaima.web.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.*;

/**
 * web配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private SecurityInterceptor securityInterceptor;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 给Controller加上前缀 （当作项目部署路径）
     */
    private static final String PATH = "/system";

    /**
     * 配置拦截器 （会自动查找实现Interceptor接口的类进行配置）
     */
    public void addInterceptors(InterceptorRegistry registry) {
        Set<Interceptor> set = new TreeSet<>((o1, o2) -> o2.priority() - o1.priority());

        SpringBeanUtil.getApplicationContext().getBeansOfType(Interceptor.class)
                .forEach((k,v) -> set.add(v));

        /**
         * 拦截器的添加顺序就是执行顺序
         */
        set.forEach((interceptor -> {
            registry.addInterceptor(securityInterceptor)
                    .addPathPatterns(securityInterceptor.getAddPathPatterns())
                    .excludePathPatterns(processingPath(securityInterceptor.getExcludePathPatterns()));
        }));
    }


    /**
     * 转换器优先级提升
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
        configurer.addPathPrefix(PATH, c -> c.isAnnotationPresent(RestController.class));
        WebMvcConfigurer.super.configurePathMatch(configurer);
    }


    private List<String> processingPath(String... paths) {
        List<String> list = new ArrayList<>();
        for (String path : paths) {
            list.add(PATH + path);
        }
        return list;
    }
}