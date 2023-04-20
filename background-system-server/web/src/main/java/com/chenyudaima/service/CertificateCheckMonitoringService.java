package com.chenyudaima.service;

import com.chenyudaima.model.CertificateCheckLog;

import java.util.Map;

/**
 * @author 沉鱼代码
 * @date 2023/4/14
 */
public interface CertificateCheckMonitoringService {
    void set(CertificateCheckLog certificateCheckLog);

    CertificateCheckLog get();
}
