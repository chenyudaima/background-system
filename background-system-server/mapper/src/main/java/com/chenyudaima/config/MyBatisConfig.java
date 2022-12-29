package com.chenyudaima.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.chenyudaima.mapper")
public class MyBatisConfig {

}
