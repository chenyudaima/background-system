package com.chenyudaima.web.controller;

import com.chenyudaima.model.Result;
import com.chenyudaima.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 沉鱼代码
 * @date 2022/12/29
 */
@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 验证token
     */
    @PostMapping("/verification")
    public Result<Boolean> s(String token) {
        Claims claims = jwtUtil.parseToken(token);


        return Result.error("");
    }

}
