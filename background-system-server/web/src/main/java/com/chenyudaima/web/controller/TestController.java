package com.chenyudaima.web.controller;

import com.chenyudaima.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    //@Autowired
    //private HttpServletRequest request;


    @PostMapping("/upload")
    public String upload(@RequestBody  MultipartFile file) {
        return file.getName();
    }

    @PostMapping("/1")
    public String dd(String name,HttpServletRequest request) {
        return name;
    }


}
