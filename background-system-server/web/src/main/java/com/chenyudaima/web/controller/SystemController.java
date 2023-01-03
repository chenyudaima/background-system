package com.chenyudaima.web.controller;

import com.chenyudaima.model.Result;
import com.chenyudaima.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 沉鱼代码
 * @date 2023/1/3
 */
@RestController
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private SystemService systemService;

    @GetMapping("/updateToken")
    public Result<?> updateToken(@RequestHeader("Authorization") String token) {
        return systemService.updateToken(token);
    }
}
