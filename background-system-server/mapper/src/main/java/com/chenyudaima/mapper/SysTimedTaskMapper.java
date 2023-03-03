package com.chenyudaima.mapper;

import com.chenyudaima.model.SysTimedTask;

import java.util.List;


public interface SysTimedTaskMapper {

    SysTimedTask selectByClassName(String className);

    List<SysTimedTask> selectAll();

    long selectCount(SysTimedTask sysTimedTask);

    List<SysTimedTask> select(SysTimedTask sysTimedTask, int page, int pageSize);

}
