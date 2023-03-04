package com.chenyudaima;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @SpringBootApplication StringBoot启动类注解
 * @ServletComponentScan 表示自动扫描Servlet组件 (比如 @ WebFilter)
 * @EnableTransactionManagement 启动事务管理器
 * @EnableScheduling 开启SpringTask定时器（系统的动态定时任务不是用的SpringTask）
 */
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
//@EnableScheduling
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}