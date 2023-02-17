package com.chenyudaima.web.controller;

import com.chenyudaima.model.Result;
import com.chenyudaima.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public Result<?> login(String account, String password) {
        return loginService.login(account, password);
    }

}
