package com.chenyudaima.service;

import com.chenyudaima.model.CertificateCheckLog;
import com.chenyudaima.model.Result;

/**
 * @author 沉鱼代码
 * @date 2023/4/10
 */
public interface CertificateCheckLogService {
    Result<?> query(CertificateCheckLog certificateCheckLog, int page, int pageSize);

    Result<?> add(CertificateCheckLog certificateCheckLog);

    Result<?> queryById(String id);

}
