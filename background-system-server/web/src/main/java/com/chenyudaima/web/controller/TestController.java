package com.chenyudaima.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.DataSource;

/**
 * 测试控制器 不需要权限
 * @author 沉鱼代码
 * @date 2023/2/3
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private DataSource dataSource;
    @GetMapping
    public String s() {
        return dataSource.getClass().getName();
    }
}
