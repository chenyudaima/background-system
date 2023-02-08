package com.chenyudaima.web.controller;

import com.chenyudaima.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


    /**
     * application/x-www-form-urlencoded
     * multipart/form-data
     */
    @PostMapping("/1")
    public String dd(SysUser sysUser) {
        return sysUser.getName();
    }

    /**
     * application/json
     */
    @PostMapping("/1.2")
    public String dde(@RequestBody SysUser sysUser) {
        return sysUser.getName();
    }


    /**
     * application/x-www-form-urlencoded
     * multipart/form-data
     */
    @PostMapping("/2")
    public String d(String name) {
        return name;
    }

    /**
     * 路径参数
     */
    @GetMapping("/8")
    public String dGE(String name) {
        return name;
    }


    /**
     * application/x-www-form-urlencoded
     * multipart/form-data
     */

    @DeleteMapping("/3")
    public String dw(String name) {
        return name;
    }


    /**
     * application/x-www-form-urlencoded
     * multipart/form-data
     */
    @DeleteMapping("/4")
    public String dwe(SysUser user) {
        return user.getName();
    }


    /**
     * application/json
     */
    @DeleteMapping("/5")
    public String dwew(@RequestBody SysUser user) {
        return user.getName();
    }


}
