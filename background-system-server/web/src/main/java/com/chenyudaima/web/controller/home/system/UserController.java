package com.chenyudaima.web.controller.home.system;

import com.chenyudaima.model.Result;
import com.chenyudaima.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 *  用户管理
 * @author 沉鱼代码
 * @date 2023/2/19
 */
@RequestMapping("/home/system/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Result<?> query() {
        return userService.query();
    }

    @PostMapping
    public Result<?> add() {
        return userService.query();
    }

    @PatchMapping
    public Result<?> update() {
        return userService.query();
    }

    @DeleteMapping
    public Result<?> delete() {
        return userService.query();
    }
}
