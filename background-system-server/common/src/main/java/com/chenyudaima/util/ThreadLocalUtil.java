package com.chenyudaima.util;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 本地线程工具类
 *
 * 如果在线程池中执行，使用完一定要执行remove()方法
 *
 * 在父子线程参数传递中，InheritableThreadLocal 只能针对 new Thread()这种方式建立的线程,对于线程池创建的线程,线程是池化以后反复使用的,这个时候父子线程间的值传递已经没有意义了
 * 所以使用阿里开源的TransmittableThreadLocal
 *
 * ThreadLocal 和 TransmittableThreadLocal可以同时使用，互不影响
 */
public class ThreadLocalUtil {
    private static final ThreadLocal<Object> THREAD_LOCAL = new ThreadLocal<>();

    private static  final TransmittableThreadLocal<Object> TRANSMITTABLE_THREAD_LOCAL = new TransmittableThreadLocal<>();

    /**
     * 从当前线程中拿
     */
    public static Object get() {
        return THREAD_LOCAL.get();
    }

    /**
     * 放到当前线程中
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


    /**
     * 放到当前线程中，子线程也能拿
     * @param o
     */
    public static void tSet(Object o) {
        TRANSMITTABLE_THREAD_LOCAL.set(o);
    }

    /**
     * 从当前线程中或者父线程中拿
     */
    public static Object tGet() {
        return TRANSMITTABLE_THREAD_LOCAL.get();
    }

    public static void tRemove() {
        TRANSMITTABLE_THREAD_LOCAL.remove();
    }
}
