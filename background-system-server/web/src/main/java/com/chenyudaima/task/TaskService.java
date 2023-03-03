package com.chenyudaima.task;

import com.chenyudaima.model.SysTimedTask;
import com.chenyudaima.util.SpringUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务管理
 *
 * @author 沉鱼代码
 * @date 2023/3/1
 */
public class TaskService {
    private static final ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

    /**
     * key 任务类名
     * value 定时任务
     */
    private static final Map<String, ScheduledFuture<?>> map = new ConcurrentHashMap<>();

    static {
        threadPoolTaskScheduler.initialize();
    }

    /**
     * 根据参数启动定时任务
     *
     * @param sysTimedTask 参数
     */
    public static void startTimeTask(SysTimedTask sysTimedTask) throws Exception {
        ScheduledFuture<?> sFuture = map.get(sysTimedTask.getClassname());

        if(sFuture != null) {
            return;
        }

        TimeTask timeTask = (TimeTask) SpringUtil.getBean(Class.forName(sysTimedTask.getClassname()));

        timeTask.setParam(sysTimedTask.getParam());

        //提交定时任务
        ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(timeTask, new CronTrigger(sysTimedTask.getCron()));

        map.put(sysTimedTask.getClassname(), schedule);
    }

    /**
     * 根据参数关闭定时任务
     * @param sysTimedTask 参数
     */
    public static void stopTimeTask(SysTimedTask sysTimedTask) {
        ScheduledFuture<?> scheduledFuture = map.get(sysTimedTask.getClassname());

        if (scheduledFuture == null) {
            return;
        }

        //取消定时任务
        scheduledFuture.cancel(true);

        //从map中删除掉
        map.remove(sysTimedTask.getClassname());
    }


}
