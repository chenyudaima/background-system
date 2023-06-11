package com.chenyudaima.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具操作
 *
 * @author 沉鱼代码
 * @date 2023/4/4
 */
public class DateUtil {
    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static String format(Date date) {
        return format.format(date);
    }



    public static Date parseDate(String inputDate) {
        return parseDate(inputDate, new String[]{
                "yyyy-MM-dd HH:mm:ss",
                "yyyy/MM/dd HH:mm:ss",
                "yyyy年MM月dd日",
                "HH:mm:ss",
                "yyyy-MM-dd",
                "yyyy/MM/dd"
        });
    }

    /**
     * 解析字符串为时间
     * @param inputDate 字符串
     * @param patterns 字符串的时间格式可能有哪些
     * @return
     */
    public static Date parseDate(String inputDate, String[] patterns) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        for (String pattern : patterns) {
            simpleDateFormat.applyPattern(pattern);

            //设置解析日期格式是否严格解析日期
            simpleDateFormat.setLenient(false);

            Date date = simpleDateFormat.parse(inputDate, new ParsePosition(0));
            if (date != null) {
                return date;
            }
        }
        return null;
    }


}
