package com.chenyudaima.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

/**
 * application自定义属性配置扫描
 * @author 沉鱼代码
 * @date 2023/3/7
 */
@Configuration
@ConfigurationPropertiesScan("com.chenyudaima.properties")
public class PropertiesConfig {
}
