package com.chenyudaima.web.controller.home.log;

import com.chenyudaima.model.CertificateCheckLog;
import com.chenyudaima.model.Result;
import com.chenyudaima.service.CertificateCheckLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 合格证核对日志
 * @author 沉鱼代码
 * @date 2023/4/10
 */
@RestController
@RequestMapping("/home/log/certificateCheckLog")
@RequiredArgsConstructor
public class CertificateCheckLogController {

    private final CertificateCheckLogService certificateCheckLogService;

    @GetMapping
    public Result<?> query(CertificateCheckLog certificateCheckLog, int page, int pageSize) {
        return certificateCheckLogService.query(certificateCheckLog, page, pageSize);
    }

    @GetMapping("/queryById")
    public Result<?> queryById(String id) {
        return certificateCheckLogService.queryById(id);
    }

    @PostMapping
    public Result<?> add(@RequestBody CertificateCheckLog certificateCheckLog) {
        return certificateCheckLogService.add(certificateCheckLog);
    }
}
