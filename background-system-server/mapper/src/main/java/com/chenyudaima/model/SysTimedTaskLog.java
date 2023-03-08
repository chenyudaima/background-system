package com.chenyudaima.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName sys_timed_task_log
 */
@Data
public class SysTimedTaskLog implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private String timedTaskId;

    private String executeParam;
    private String executeResult;
    private Date startExecuteTime;

    private Integer executeStatus;

    private Long elapsedTime;
}