package com.chenyudaima.service;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysInterfaceRequestLog;

/**
 * @author 沉鱼代码
 * @date 2023/3/22
 */
public interface SysInterfaceRequestLogService {
    Result<?> query(SysInterfaceRequestLog sysInterfaceRequestLog, int page, int pageSize);

}
