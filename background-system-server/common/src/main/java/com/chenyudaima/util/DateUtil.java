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
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Date date) {
        return format.format(date);
    }

    public static Date parse(String date) throws ParseException {
        return format.parse(date);
    }
}
