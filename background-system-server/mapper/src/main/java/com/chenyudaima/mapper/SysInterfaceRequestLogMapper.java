package com.chenyudaima.mapper;

import com.chenyudaima.model.SysInterfaceRequestLog;

import java.util.List;

public interface SysInterfaceRequestLogMapper {


    int insert(SysInterfaceRequestLog record);


    List<SysInterfaceRequestLog> select(SysInterfaceRequestLog sysInterfaceRequestLog, int page, int pageSize);

    long selectCount(SysInterfaceRequestLog sysInterfaceRequestLog);

    int deleteByDay(int day);

}
