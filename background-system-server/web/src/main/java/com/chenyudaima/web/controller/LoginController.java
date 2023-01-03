package com.chenyudaima.web.controller;

import com.chenyudaima.model.Result;
import com.chenyudaima.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 沉鱼代码
 * @date 2022/12/30
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result<?> login(String account,String password) {
        return loginService.login(account,password);
    }

    @GetMapping("/updateToken")
    public Result<?> updateToken(@RequestHeader("Authorization") String token) {
        return loginService.updateToken(token.substring(6));
    }

}
