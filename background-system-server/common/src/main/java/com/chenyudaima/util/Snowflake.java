package com.chenyudaima.util;

import com.chenyudaima.properties.SnowflakeProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 雪花算法
 * 依赖时钟，所以服务器时钟不能回拨
 * 最多可以部署 32 * 32 = 1024 台机器
 * 速度：单线程执行获取一千条id只需要5毫秒
 * 单线程执行获取一千万条只需要5秒
 */

@Component
@RequiredArgsConstructor
public class Snowflake {

    /**
     * application配置文件注入
     */
    private final SnowflakeProperties snowflakeProperties;

    /**
     * 起始的时间戳
     */
    private final static long START_TIMESTAMP = 1480166465631L;

    /**
     * 序列号占用的位数
     */
    private final static long SEQUENCE_BIT = 12;

    /**
     * 机器标识占用的位数
     */
    private final static long MACHINE_BIT = 5;

    /**
     * 数据中心占用的位数
     */
    private final static long DATACENTER_BIT = 5;

    /**
     * 数据中心最大值
     */
    public final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);

    /**
     * 机器标识最大值
     */
    public final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);

    /**
     * 顺序最大值
     */
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    /**
     * 序列号
     */
    private long sequence = 0L;

    /**
     * 上一次时间戳
     */
    private long lastTimeStamp = -1L;

    /**
     * 获取id
     * 线程安全的
     * 当前时间小于上次时间会抛异常（时钟被拨动）
     */
    public synchronized long nextId() {
        long currTimeStamp = getNewTimeStamp();
        if (currTimeStamp < lastTimeStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currTimeStamp == lastTimeStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currTimeStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastTimeStamp = currTimeStamp;

        return (currTimeStamp - START_TIMESTAMP) << TIMESTAMP_LEFT //时间戳部分
                | snowflakeProperties.getDatacenterId() << DATACENTER_LEFT       //数据中心部分
                | snowflakeProperties.getMachineId() << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private long getNextMill() {
        long mill = getNewTimeStamp();
        while (mill <= lastTimeStamp) {
            mill = getNewTimeStamp();
        }
        return mill;
    }

    private long getNewTimeStamp() {
        return System.currentTimeMillis();
    }

}

