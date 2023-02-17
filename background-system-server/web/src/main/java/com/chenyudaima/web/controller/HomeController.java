package com.chenyudaima.web.controller;

import com.chenyudaima.constant.HttpHeader;
import com.chenyudaima.constant.RequestAttribute;
import com.chenyudaima.model.Result;
import com.chenyudaima.service.HomeService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    /**
     * 查询用户信息
     */
    @GetMapping("/userInfo")
    public Result<?> userInfo() {
        return homeService.userInfo();
    }

    /**
     * 查询导航栏
     */
    @GetMapping("/navigation")
    public Result<?> navigation() {
        return homeService.navigation();
    }


    /**
     * 注销 退出登录
     */
    @GetMapping("/logout")
    public Result<?> logout(@RequestHeader(HttpHeader.K_REQUEST_HEADER_AUTHORIZATION) String token) {
        return homeService.logout(token);
    }



    //冻结的时候把redis中的key清除，然后用户就会提示重新登录，登录之后就会显示冻结
}
