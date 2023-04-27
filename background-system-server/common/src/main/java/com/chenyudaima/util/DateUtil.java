package com.chenyudaima.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具操作
 * @author 沉鱼代码
 * @date 2023/4/4
 */
public class DateUtil {
    public static final SimpleDateFormat DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat TIME = new SimpleDateFormat("HH:mm:ss");

    public static String format(Date date) {
        return DATE_TIME.format(date);
    }

    public static Date parse(String date) throws ParseException {
        return DATE_TIME.parse(date);
    }

    /**
     * 时间转字符串
     */
    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 字符串转时间
     */
    public static Date parse(String date, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(date);
    }
}
