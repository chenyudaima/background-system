package com.chenyudaima.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * 本地线程工具类
 * @author 沉鱼代码
 * @date 2023/1/31
 */
public class ThreadPoolUtil {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            //线程池核心数
            20,

            //最大核心数
            35,

            //线程闲置超时时间
            8,
            TimeUnit.SECONDS,

            //工作队列
            new LinkedBlockingDeque<>(50),

            //线程池创建工厂
            new ThreadFactory() {
                private final AtomicInteger mCount = new AtomicInteger(1);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "OPC---" + mCount.getAndIncrement());
                }
            },

            //拒绝策略
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    //不带返回值
    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    //带返回值(可以监听线程池子线程执行状态及执行结果)
    public static Future submit(Runnable runnable){
        return executor.submit(runnable);
    }
}
