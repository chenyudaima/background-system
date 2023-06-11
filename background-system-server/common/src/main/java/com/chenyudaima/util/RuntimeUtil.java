package com.chenyudaima.util;

import com.chenyudaima.constant.Property;

/**
 * 运行环境工具
 * @author 沉鱼代码
 * @date 2023/3/21
 */
public class RuntimeUtil {

    /**
     * 检测是否是window运行环境
     */
    public static boolean isWindow() {
        return System.getProperty(Property.OS_NAME).contains("Windows");
    }

    /**
     * 检测是否是Linux运行环境
     */
    public static boolean isLinux() {
        return System.getProperty(Property.OS_NAME).contains("Linux");
    }

    /**
     * 检测是否是单元测试环境
     * @return true 是   false 否
     */
    public static boolean isRunningTest() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement e : stackTrace) {
            if (e.toString().lastIndexOf("junit") > -1) {
                return true;
            }
        }
        return false;
    }

}
