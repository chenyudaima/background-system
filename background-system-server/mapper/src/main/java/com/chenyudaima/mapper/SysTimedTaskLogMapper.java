package com.chenyudaima.mapper;

import com.chenyudaima.model.SysTimedTaskLog;

import java.util.List;

/**
* @author cydm
* @description 针对表【sys_timed_task_log(定时任务日志表)】的数据库操作Mapper
* @createDate 2023-03-03 17:27:49
* @Entity com.chenyudaima.model.SysTimedTaskLog
*/
public interface SysTimedTaskLogMapper {

    int insert(SysTimedTaskLog sysTimedTaskLog);

    int deleteBySysTimedTaskId(String timedTaskId);

    int deleteBySysTimedTaskIds(String[] timedTaskIds);

    List<SysTimedTaskLog> select(SysTimedTaskLog sysTimedTaskLog, int page, int pageSize);

    long selectCount(SysTimedTaskLog sysTimedTaskLog);

}
