package com.chenyudaima.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 接口请求日志表
 */
@Data
public class SysInterfaceRequestLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private Date requestTime;

    private Long spendTime;

    private String requestUrl;

    private String requestMethod;

    private String requestIp;

    private String requestParam;

    private String responseResult;

    private Integer status;
}