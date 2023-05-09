package com.chenyudaima.config;


import com.chenyudaima.util.SpringUtil;
import com.chenyudaima.web.interceptor.Interceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * web controller配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    /**
     * 给Controller加上前缀 （当作项目部署路径）
     */
    public static final String PATH = "/system_api";

    /**
     * 配置拦截器 （会自动查找实现Interceptor接口的类进行配置）
     */
    public void addInterceptors(InterceptorRegistry registry) {
        Set<Interceptor> set = new TreeSet<>((o1, o2) -> o1.priority() - o2.priority());

        SpringUtil.getApplicationContext().getBeansOfType(Interceptor.class)
                .forEach((k,v) -> set.add(v));

        /**
         * 拦截器的添加顺序就是执行顺序
         */
        set.forEach((interceptor ->
            registry.addInterceptor(interceptor)
                    .addPathPatterns(processingPath(interceptor.getAddPathPatterns()))
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
        //给所有有RestController注解的控制器加上PATH前缀
        configurer.addPathPrefix(PATH, c -> c.isAnnotationPresent(RestController.class));

        WebMvcConfigurer.super.configurePathMatch(configurer);
    }

    ///**
    // * 配置跨域
    // */
    //@Override
    //public void addCorsMappings(CorsRegistry registry) {
    //    registry.addMapping("/**")
    //            //放行哪些原始域
    //            .allowedOrigins("http://192.168.0.49:3000")
    //            //是否发送Cookie信息
    //            .allowCredentials(true)
    //            //放行哪些原始域(请求方式)
    //            .allowedMethods("GET","POST", "PUT", "DELETE")
    //            //放行哪些原始域(头部信息)
    //            .allowedHeaders("*")
    //            .maxAge(3600)
    //            //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
    //            .exposedHeaders("Authorization", "signature");
    //    WebMvcConfigurer.super.addCorsMappings(registry);
    //}


    private String[] processingPath(String[] paths) {
        if(paths == null || paths.length == 0) {
            return paths;
        }

        for (int i = 0; i < paths.length; i++) {
            paths[i] = PATH + paths[i];
        }

        return paths;
    }
}