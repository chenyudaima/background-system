package com.chenyudaima.web.controller;

import com.chenyudaima.model.Result;
import com.chenyudaima.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 沉鱼代码
 * @date 2022/12/30
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result<?> login(String account,String password) {
        return loginService.login(account,password);
    }

}
