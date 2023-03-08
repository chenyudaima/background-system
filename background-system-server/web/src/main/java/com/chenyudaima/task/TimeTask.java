package com.chenyudaima.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.chenyudaima.mapper.SysTimedTaskLogMapper;
import com.chenyudaima.model.SysTimedTask;
import com.chenyudaima.model.SysTimedTaskLog;
import com.chenyudaima.util.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 定时任务基类
 *
 * @author 沉鱼代码
 * @date 2023/2/28
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class TimeTask implements Runnable {

    private SysTimedTaskLogMapper sysTimedTaskLogMapper;

    private Snowflake snowflake;

    @Autowired
    public void setSysTimedTaskLogMapper(SysTimedTaskLogMapper sysTimedTaskLogMapper) {
        this.sysTimedTaskLogMapper = sysTimedTaskLogMapper;
    }

    @Autowired
    public void setSnowflakeIdWorker(Snowflake snowflake) {
        this.snowflake = snowflake;
    }

    private Map<String, String> paramMap;

    private SysTimedTask sysTimedTask;

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        SysTimedTaskLog sysTimedTaskLog = new SysTimedTaskLog();
        sysTimedTaskLog.setStartExecuteTime(new Date());
        sysTimedTaskLog.setId(snowflake.nextId());
        sysTimedTaskLog.setExecuteParam(sysTimedTask.getParam());
        sysTimedTaskLog.setTimedTaskId(sysTimedTask.getId());

        String result;
        int status = 1;

        try {
            result = run(paramMap);
        } catch (Exception e) {
            result = e + "\n" + e.getStackTrace()[0].toString();
            status = 0;
        }

        time = System.currentTimeMillis() - time;
        sysTimedTaskLog.setExecuteStatus(status);
        sysTimedTaskLog.setElapsedTime(time);
        sysTimedTaskLog.setExecuteResult(result);
        sysTimedTaskLogMapper.insert(sysTimedTaskLog);
    }

    public void setSysTimedTask(SysTimedTask sysTimedTask) {
        if (sysTimedTask.getParam() != null) {
            try {
                paramMap = JSON.parseObject(sysTimedTask.getParam(), new TypeReference<Map<String, String>>() {
                });
            }catch (Exception e) {
                throw new JSONException("参数需要标准JSON格式，JSON中的value非数字需要带 双引号\"\" 或者 单引号''");
            }
        }

        this.sysTimedTask = sysTimedTask;
    }


    abstract String run(Map<String, String> paramMap) throws Exception;
}
