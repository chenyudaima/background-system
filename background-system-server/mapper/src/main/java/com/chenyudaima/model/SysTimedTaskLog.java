package com.chenyudaima.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 定时任务日志表
 */
@Data
public class SysTimedTaskLog implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;

    private String timedTaskId;

    private String executeParam;
    private String executeResult;
    private Date startExecuteTime;

    private Integer executeStatus;

    private Long elapsedTime;
}