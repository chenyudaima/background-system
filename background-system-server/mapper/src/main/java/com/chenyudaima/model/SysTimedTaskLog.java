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
    private String id;

    private String timedTaskId;

    private String executeParam;
    private String executeResult;

    @JsonF(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startExecuteTime;

    private Integer executeStatus;

    private Long elapsedTime;
}