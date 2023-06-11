package com.chenyudaima.web.controller.business;

import com.chenyudaima.model.Result;
import com.chenyudaima.util.hk.HkApp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 沉鱼代码
 * @date 2023/5/23
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/home/business/hkApp")
public class HkAppController {

    @PostMapping("/control")
    public Result<?> control(int dwPTZControlCmd, int dwStop, int dwSpeed) {
        return Result.success(HkApp.control(dwPTZControlCmd, dwStop, dwSpeed));
    }

    @PostMapping("/presetPointOperation")
    public Result<?> presetPointOperation(int dwPresetIndex) {
        return Result.success(HkApp.presetPointOperation(dwPresetIndex));
    }

}
