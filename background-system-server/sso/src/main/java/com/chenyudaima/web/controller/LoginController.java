package com.chenyudaima.web.controller;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysUser;
import com.chenyudaima.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 登录界面
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title","这是一个title");
        return "login";
    }

    /**
     * 登录请求
     */
    @PostMapping("/doLogin")
    @ResponseBody
    public Result<?> doLogin(String userName, String password, String code, HttpServletResponse resp) throws IOException {
        //调用数据库 找不到则
        //验证成功则创建token返回给用户
        //return loginService.doLogin(userName,password);
        Cookie cookie = new Cookie("tttaaaa","张三");
        return Result.success("你好");

    }

    /**
     * 注册页面
     */

    /**
     * 注册请求
     */

}
