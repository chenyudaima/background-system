package com.chenyudaima.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis mapper包扫描配置
 * @author 沉鱼代码
 * @date 2023/2/3
 */
@MapperScan("com.chenyudaima.mapper")
@Configuration
public class MybatisConfig {
}
