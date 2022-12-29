package com.chenyudaima.util;

/**
 * 本地线程工具类
 */
public class ThreadLocalUtil {
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    /**
     * 从当前线程副本变量拿
     */
    public static Object get() {
        return THREAD_LOCAL.get();
    }

    /**
     * 放到当前线程副本变量中
     */
    public static void set(Object o) {
        THREAD_LOCAL.set(o);
    }

    /**
     * 使用完ThreadLocal后，执行remove操作，避免出现内存溢出情况
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
