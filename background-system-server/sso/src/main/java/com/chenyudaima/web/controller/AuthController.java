package com.chenyudaima.web.controller;

import com.chenyudaima.model.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 沉鱼代码
 * @date 2022/12/29
 */
@RestController
public class AuthController {
    /**
     * 验证token
     */
    @PostMapping("/")
    public Result<Boolean> (String token) {

    }

}
