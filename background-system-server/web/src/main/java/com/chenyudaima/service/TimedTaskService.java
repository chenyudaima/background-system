package com.chenyudaima.service;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysTimedTask;
import com.chenyudaima.model.SysTimedTaskLog;

/**
 * @author 沉鱼代码
 * @date 2023/3/1
 */
public interface TimedTaskService {
    Result<?> query(SysTimedTask sysTimedTask, int page, int pageSize);

    Result<?> update(SysTimedTask sysTimedTask);

    Result<?> deleteById(String id);

    Result<?> deleteByIdBatch(String[] ids);

    Result<?> add(SysTimedTask sysTimedTask);

    Result<?> querySysTimedTaskLog(SysTimedTaskLog sysTimedTaskLog, int page, int pageSize);

}
