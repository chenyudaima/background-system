package com.chenyudaima.web.controller;

import com.chenyudaima.constant.RequestAttribute;
import com.chenyudaima.model.Result;
import com.chenyudaima.service.HomeService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HomeService homeService;

    @GetMapping("/userInfo")
    public Result<Map<String,Object>> userInfo() {
        Claims claims = (Claims) request.getAttribute(RequestAttribute.CLAIMS);
        return homeService.userInfo(Integer.parseInt(claims.getId()));
    }
}
