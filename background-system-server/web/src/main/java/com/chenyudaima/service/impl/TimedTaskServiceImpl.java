package com.chenyudaima.service.impl;

import com.chenyudaima.mapper.SysTimedTaskLogMapper;
import com.chenyudaima.mapper.SysTimedTaskMapper;
import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysTimedTask;
import com.chenyudaima.model.SysTimedTaskLog;
import com.chenyudaima.service.TimedTaskService;
import com.chenyudaima.task.TaskService;
import com.chenyudaima.task.TimeTask;
import com.chenyudaima.util.SpringUtil;
import com.chenyudaima.util.ThreadPoolUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author 沉鱼代码
 * @date 2023/3/1
 */
@Service
@RequiredArgsConstructor
public class TimedTaskServiceImpl implements TimedTaskService {

    private final SysTimedTaskMapper sysTimedTaskMapper;

    private final SysTimedTaskLogMapper sysTimedTaskLogMapper;

    @Override
    public Result<?> query(SysTimedTask sysTimedTask, int page, int pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("sysTimedTaskList", sysTimedTaskMapper.select(sysTimedTask, page, pageSize));
        map.put("total", sysTimedTaskMapper.selectCount(sysTimedTask));

        return Result.success(map);
    }

    @Transactional(timeout = 60)
    public Result<?> update(SysTimedTask sysTimedTask) {
        int count = sysTimedTaskMapper.update(sysTimedTask);

        if(count == 0) {
            return Result.success();
        }

        TaskService.stopTimeTask(sysTimedTask.getId());
        try {
            if(sysTimedTask.getStatus() != 0) {
                TaskService.startTimeTask(sysTimedTask);
            }
        } catch (ClassNotFoundException e) {
            sysTimedTask.setStatus(0);
            sysTimedTaskMapper.update(sysTimedTask);
            TaskService.stopTimeTask(sysTimedTask.getId());
            throw new RuntimeException(sysTimedTask.getClassName() + "类不存在");
        }
        return Result.success();
    }

    @Transactional(timeout = 60)
    public Result<?> deleteById(String id) {
        sysTimedTaskMapper.deleteById(id);
        sysTimedTaskLogMapper.deleteBySysTimedTaskId(id);
        TaskService.stopTimeTask(id);
        return Result.success();
    }

    @Transactional(timeout = 60)
    public Result<?> deleteByIdBatch(String[] ids) {
        sysTimedTaskMapper.deleteByIdBatch(ids);
        sysTimedTaskLogMapper.deleteBySysTimedTaskIds(ids);
        for (String id : ids) {
            TaskService.stopTimeTask(id);
        }
        return Result.success();
    }

    @Transactional(timeout = 60)
    public Result<?> add(SysTimedTask sysTimedTask) {
        sysTimedTaskMapper.insert(sysTimedTask);

        try {
            if(sysTimedTask.getStatus() != 0) {
                TaskService.startTimeTask(sysTimedTask);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(sysTimedTask.getClassName() + "不存在");
        }

        return Result.success();
    }

    @Override
    public Result<?> querySysTimedTaskLog(SysTimedTaskLog sysTimedTaskLog, int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("sysTimedTaskLogList", sysTimedTaskLogMapper.select(sysTimedTaskLog, page, pageSize));
        map.put("total", sysTimedTaskLogMapper.selectCount(sysTimedTaskLog));
        return Result.success(map);
    }

    @Override
    public Result<?> run(SysTimedTask sysTimedTask) {
        try {
            TimeTask timeTask = (TimeTask) SpringUtil.getBean(Class.forName(sysTimedTask.getClassName()));
            timeTask.setSysTimedTask(sysTimedTask);
            ThreadPoolUtil.submit(timeTask).get();
        } catch (Exception e) {
            throw new RuntimeException("执行失败,{}", e);
        }
        return Result.success();
    }


}
