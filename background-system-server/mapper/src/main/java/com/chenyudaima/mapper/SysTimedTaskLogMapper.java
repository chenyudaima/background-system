package com.chenyudaima.mapper;

import com.chenyudaima.model.SysTimedTaskLog;

import java.util.List;


public interface SysTimedTaskLogMapper {

    int insert(SysTimedTaskLog sysTimedTaskLog);

    int deleteBySysTimedTaskId(String timedTaskId);

    int deleteBySysTimedTaskIds(String[] timedTaskIds);

    List<SysTimedTaskLog> select(SysTimedTaskLog sysTimedTaskLog, int page, int pageSize);

    long selectCount(SysTimedTaskLog sysTimedTaskLog);

    int deleteByDay(int day);


}
