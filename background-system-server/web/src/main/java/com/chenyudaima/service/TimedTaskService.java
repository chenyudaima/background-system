package com.chenyudaima.service;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysTimedTask;

/**
 * @author 沉鱼代码
 * @date 2023/3/1
 */
public interface TimedTaskService {
    Result<?> query(SysTimedTask sysTimedTask, int page, int pageSize);

}
