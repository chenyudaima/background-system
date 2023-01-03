package com.chenyudaima.web.controller;

import com.chenyudaima.model.Result;
import com.chenyudaima.service.HomeService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 沉鱼代码
 * @date 2023/1/3
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/userInfo")
    public Result<Map<String,Object>> userInfo(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        return homeService.userInfo(Integer.parseInt(claims.getId()));
    }
}
