package com.chenyudaima;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 沉鱼代码
 * @date 2022/12/20
 */
@SpringBootApplication
@MapperScan("com.chenyudaima.mapper")
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class,args);
    }
}