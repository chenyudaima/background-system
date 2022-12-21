package com.chenyudaima.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录
 * @author 沉鱼代码
 * @date 2022/12/20
 */
@RestController
public class TestController {

    @GetMapping("/login")
    public String login(HttpServletResponse resource) throws IOException {

        return "你好";
    }

    @GetMapping("/text")
    public int text() {
        return 23;
    }




}
