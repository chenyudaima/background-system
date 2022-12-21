package com.chenyudaima.controller;

import com.chenyudaima.model.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 沉鱼代码
 * @date 2022/12/20
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public void login(HttpServletResponse resource) throws IOException {

        //鉴权 调用sso接口，鉴权失败 响应
        System.out.println("进来了");
        resource.setContentType("text/html");
        //resource.addHeader("");
        //响应到sso登录中心
        resource.sendRedirect("http://localhost:8084/login");
    }



}
