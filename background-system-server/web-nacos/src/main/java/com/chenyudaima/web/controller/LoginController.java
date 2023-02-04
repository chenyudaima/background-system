package com.chenyudaima.web.controller;

import com.chenyudaima.model.Result;
import com.chenyudaima.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result<?> login(String account, String password) {
        return loginService.login(account, password);
    }

}
