package com.chenyudaima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 沉鱼代码
 * @date 2022/12/20
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title","这是一个title");
        return "login";
    }
}
