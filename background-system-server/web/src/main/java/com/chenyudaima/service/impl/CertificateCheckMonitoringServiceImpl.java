package com.chenyudaima.service.impl;

import com.chenyudaima.model.CertificateCheckLog;
import com.chenyudaima.service.CertificateCheckMonitoringService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 沉鱼代码
 * @date 2023/4/14
 */
@Service
public class CertificateCheckMonitoringServiceImpl implements CertificateCheckMonitoringService {

    private CertificateCheckLog certificateCheckLog;


    @Override
    public void set(CertificateCheckLog certificateCheckLog) {
        this.certificateCheckLog = certificateCheckLog;
    }

    @Override
    public CertificateCheckLog get() {

        return certificateCheckLog;

    }
}
