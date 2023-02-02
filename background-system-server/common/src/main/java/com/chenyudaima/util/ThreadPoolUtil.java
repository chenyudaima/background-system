package com.chenyudaima.util;

import com.chenyudaima.config.ThreadPoolConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
/**
 * 线程池工具类
 * @author 沉鱼代码
 * @date 2023/1/31
 */
public class ThreadPoolUtil {

    private static final ThreadPoolExecutor executor = ThreadPoolConfig.getExecutor();

    public enum RunStatusEnum {

        /**
         * 当前排队线程数
         */
        QUEUE_SIZE,

        /**
         * 当前活动线程数
         */
        ACTIVE_COUNT,

        /**
         * 执行完成线程数
         */
        COMPLETED_TASK_COUNT,

        /**
         * 总线程数
         */
        TASK_COUNT;

    }

    /**
     * 多线程执行任务
     * @param runnable 任务
     */
    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    /**
     * 多线程执行任务
     * @param runnable 任务
     * @return 线程执行状态及执行结果
     */
    public static Future<?> submit(Runnable runnable) {
        return executor.submit(runnable);
    }

    /**
     * 多线程执行任务（带返回值） 不能用Lambda表达式
     * @param callable 任务
     * @return 线程执行状态及执行结果
     */
    public static<T> Future<?> submit(Callable<T> callable) {
        return executor.submit(callable);
    }


    /**
     * 等待线程池所有任务执行完成
     * @param timeout 愿意等待的时间
     * @param unit 时间单位
     * @return 是否完成
     * @throws InterruptedException
     */
    public static boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return executor.awaitTermination(timeout, unit);
    }

    /**
     * 查看线程池运行状态
     * @return 线程池运行状态
     */
    public static Map<ThreadPoolUtil.RunStatusEnum, Long> getRunStatus() {
        HashMap<ThreadPoolUtil.RunStatusEnum, Long> map = new HashMap<>();

        //当前排队线程数
        map.put(ThreadPoolUtil.RunStatusEnum.QUEUE_SIZE, (long) executor.getQueue().size());

        //当前活动线程数
        map.put(ThreadPoolUtil.RunStatusEnum.ACTIVE_COUNT, (long) executor.getActiveCount());

        //执行完成线程数
        map.put(ThreadPoolUtil.RunStatusEnum.COMPLETED_TASK_COUNT, executor.getCompletedTaskCount());

        //总线程数
        map.put(ThreadPoolUtil.RunStatusEnum.TASK_COUNT, executor.getTaskCount());

        return map;
    }


    /**
     * 修改线程池核心数
     * @param corePoolSize 线程池核心数
     */
    public static void setCorePoolSize(int corePoolSize) {
        executor.setCorePoolSize(corePoolSize);
    }

    /**
     * 修改线程池允许的最大线程数
     * @param maximumPoolSize 线程池允许的最大线程数
     */
    public static void setMaximumPoolSize(int maximumPoolSize) {
        executor.setMaximumPoolSize(maximumPoolSize);
    }

    /**
     * 修改线程池线程闲置超时时间
     * @param time 时间
     * @param unit 时间单位
     */
    public static void setKeepAliveTime(long time, TimeUnit unit) {
        executor.setKeepAliveTime(time, unit);
    }

    /**
     * 修改线程池线程工厂
     * @param threadFactory 线程工厂
     */
    public static void setThreadFactory(ThreadFactory threadFactory) {
        executor.setThreadFactory(threadFactory);
    }

    /**
     * 修改线程池拒绝策略
     * @param handler 拒绝策略
     */
    public static void setRejectedExecutionHandler(RejectedExecutionHandler handler) {
        executor.setRejectedExecutionHandler(handler);
    }

}
