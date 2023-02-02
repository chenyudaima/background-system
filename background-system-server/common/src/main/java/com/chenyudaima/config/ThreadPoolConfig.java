package com.chenyudaima.config;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadPoolUtil配置
 * @author 沉鱼代码
 * @date 2023/2/1
 */
public class ThreadPoolConfig {

    /**
     * 获取当前运行机器的可用CPU核心数
     */
    private static final int POLLER_THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 获取默认配置线程池
     * @return 线程池
     */
    public static ThreadPoolExecutor getExecutor() {

        return new ThreadPoolExecutor(
                //线程池核心数
                POLLER_THREAD_COUNT + 1,

                //线程池允许的最大线程数
                POLLER_THREAD_COUNT * 8,

                //线程闲置超时时间
                10,
                TimeUnit.SECONDS,

                //工作队列
                new LinkedBlockingDeque<>(64),

                //线程池创建工厂
                new ThreadFactory() {
                    private final AtomicInteger mCount = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "LocalThreadPool" + mCount.getAndIncrement());
                    }
                },

                //拒绝策略
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

}
