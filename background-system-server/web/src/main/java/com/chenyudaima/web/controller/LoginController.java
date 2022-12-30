package com.chenyudaima.web.controller;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysUser;
import com.chenyudaima.service.SysUserService;
import com.chenyudaima.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 沉鱼代码
 * @date 2022/12/30
 */
@RestController
public class LoginController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public Result<?> login(String account,String password) {
        return sysUserService.login(account,password);
    }

}
