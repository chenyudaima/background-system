package com.chenyudaima.web.controller;

import com.chenyudaima.model.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 沉鱼代码
 * @date 2022/12/20
 */
@Controller
public class LoginController {

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
    public Result<?> doLogin(String userName,String password,String code) {
        //调用数据库 找不到则
        return Result.error("用户名或密码错误！");

    }

    /**
     * 注册页面
     */

    /**
     * 注册请求
     */

}
