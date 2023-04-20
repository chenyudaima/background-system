package com.chenyudaima.service.impl;

import com.chenyudaima.mapper.CertificateCheckLogMapper;
import com.chenyudaima.model.CertificateCheckLog;
import com.chenyudaima.model.Result;
import com.chenyudaima.service.CertificateCheckLogService;
import com.chenyudaima.service.CertificateCheckMonitoringService;
import com.chenyudaima.util.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 沉鱼代码
 * @date 2023/4/10
 */
@Service
@RequiredArgsConstructor
public class CertificateCheckLogServiceImpl implements CertificateCheckLogService {

    private final CertificateCheckLogMapper certificateCheckLogMapper;

    private final Snowflake snowflake;

    private CertificateCheckMonitoringService certificateCheckMonitoringService;

    @Override
    public Result<?> query(CertificateCheckLog certificateCheckLog, int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("certificateCheckLogList", certificateCheckLogMapper.select(certificateCheckLog, page, pageSize));
        map.put("total", certificateCheckLogMapper.selectCount(certificateCheckLog));
        return Result.success(map);
    }

    @Override
    public Result<?> add(CertificateCheckLog certificateCheckLog) {
        if(certificateCheckLog.getType() == 0) {
            certificateCheckMonitoringService.set(certificateCheckLog);
        }

        certificateCheckLog.setCreateTime(new Date());
        certificateCheckLog.setId(String.valueOf(snowflake.nextId()));
        return Result.success(certificateCheckLogMapper.insert(certificateCheckLog));
    }

    @Override
    public Result<?> queryById(String id) {
        return Result.success(certificateCheckLogMapper.selectById(id));
    }
}
