package com.chenyudaima.web.controller;

import com.chenyudaima.constant.Property;
import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * 测试控制器 不需要权限
 * @author 沉鱼代码
 * @date 2023/2/3
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private String logPath;

    @PostMapping
    public Result<?> query(MultipartFile file) throws Exception {
        return Result.success(file.getOriginalFilename() + "校验成功");
    }

}
