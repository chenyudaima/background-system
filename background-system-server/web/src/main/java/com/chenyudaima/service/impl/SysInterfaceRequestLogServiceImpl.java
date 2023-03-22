package com.chenyudaima.service.impl;

import com.chenyudaima.mapper.SysInterfaceRequestLogMapper;
import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysInterfaceRequestLog;
import com.chenyudaima.service.SysInterfaceRequestLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 沉鱼代码
 * @date 2023/3/22
 */
@Service
@RequiredArgsConstructor
public class SysInterfaceRequestLogServiceImpl implements SysInterfaceRequestLogService {

    private final SysInterfaceRequestLogMapper sysInterfaceRequestLogMapper;

    @Override
    public Result<?> query(SysInterfaceRequestLog sysInterfaceRequestLog, int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();

        map.put("sysInterfaceRequestLogList", sysInterfaceRequestLogMapper.select(sysInterfaceRequestLog, page, pageSize));
        map.put("total", sysInterfaceRequestLogMapper.selectCount(sysInterfaceRequestLog));

        return Result.success(map);
    }
}
