package com.chenyudaima.web.controller.home.log;

import com.chenyudaima.model.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统日志
 * @author 沉鱼代码
 * @date 2023/2/28
 */
@RestController
@RequestMapping("/home/log/sysSystemErrorLog")
@RequiredArgsConstructor
public class SysSystemRunLogController {

    @GetMapping
    public Result<?> query() {
        return null;
    }
}
