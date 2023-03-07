package com.chenyudaima.task;

import com.alibaba.fastjson.JSONException;
import com.chenyudaima.model.SysTimedTask;
import com.chenyudaima.util.SpringUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务管理
 *
 * @author 沉鱼代码
 * @date 2023/3/1
 */
public class TaskService {

    /**
     * 定时任务线程池
     */
    private static final ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

    /**
     * key 任务id
     * value 定时任务
     */
    private static final Map<String, ScheduledFuture<?>> map = new HashMap<>();

    static {
        threadPoolTaskScheduler.initialize();
    }

    /**
     * 根据任务id启动定时任务
     * @param sysTimedTask 参数
     */
    public static synchronized void startTimeTask(SysTimedTask sysTimedTask) throws ClassNotFoundException, JSONException {
        ScheduledFuture<?> sFuture = map.get(sysTimedTask.getId());

        //说明上一条并发线程启动过，防止重复启动
        if(sFuture != null) {
            return;
        }

        TimeTask timeTask = (TimeTask) SpringUtil.getBean(Class.forName(sysTimedTask.getClassName()));

        timeTask.setSysTimedTask(sysTimedTask);

        //提交定时任务
        ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(timeTask, new CronTrigger(sysTimedTask.getCron()));

        map.put(sysTimedTask.getId(), schedule);
    }

    /**
     * 根据任务id关闭定时任务
     * @param id 任务id
     */
    public static synchronized void stopTimeTask(String id) {
        Optional.ofNullable(map.get(id)).ifPresent(scheduledFuture -> {
            //取消定时任务
            scheduledFuture.cancel(true);

            //从map中删除掉
            map.remove(id);
        });
    }


}
