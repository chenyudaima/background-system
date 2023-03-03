package com.chenyudaima.service.impl;

import com.chenyudaima.mapper.SysTimedTaskMapper;
import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysTimedTask;
import com.chenyudaima.service.TimedTaskService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 沉鱼代码
 * @date 2023/3/1
 */
@Service
public class TimedTaskServiceImpl implements TimedTaskService {

    private SysTimedTaskMapper sysTimedTaskMapper;
    @Override
    public Result<?> query(SysTimedTask sysTimedTask, int page, int pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("timedTaskList", sysTimedTaskMapper.select(sysTimedTask, page, pageSize));
        map.put("total", sysTimedTaskMapper.selectCount(sysTimedTask));

        return Result.success(map);
    }
}
