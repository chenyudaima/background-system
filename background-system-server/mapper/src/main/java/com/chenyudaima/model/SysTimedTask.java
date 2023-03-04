package com.chenyudaima.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 定时任务表
 */
@Data
public class SysTimedTask implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String className;

    private String cron;

    private String param;

    private Integer status;

    private String description;
}