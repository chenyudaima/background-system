package com.chenyudaima.web.controller.monitoring;

import com.chenyudaima.model.Result;
import com.chenyudaima.service.CertificateCheckMonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 合格证核对监控
 * @author 沉鱼代码
 * @date 2023/4/11
 */
@RequiredArgsConstructor
@RequestMapping("/home/monitoring/certificateCheckMonitoring")
@RestController
public class CertificateCheckMonitoringController {

    private final CertificateCheckMonitoringService certificateCheckMonitoringService;

    /**
     * 监控页面轮询调用该接口
     * 如果status为false说明没有新数据
     * 如果为true说明有新数据
     *
     * @return
     */
    @GetMapping
    public Result<?> get() {
        return Result.success(certificateCheckMonitoringService.get());
    }

    /**
     * 让监控画面进入等待
     */
    //@GetMapping("/delete")
    //public Result<?> delete() {
    //    return Result.success(certificateCheckMonitoringService.delete());
    //}
}
