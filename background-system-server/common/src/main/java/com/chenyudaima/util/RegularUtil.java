package com.chenyudaima.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则解析工具类
 * 使用的时候注意特殊符号用\\转义
 */
public class RegularUtil {
    public static final Pattern BEGIN_NUMBER = Pattern.compile("(\\d+(\\.\\d+)?)");

    public static final Pattern LAST_NUMBER = Pattern.compile("(\\d+(\\.\\d+)?)$");

    public static final Pattern BEGIN_INTEGER = Pattern.compile("(\\d+)");

    public static final Pattern LAST_INTEGER = Pattern.compile("(\\d+)$");

    public static final Pattern PHONE = Pattern.compile("(1[3-9]\\d{9})");

    public static final Pattern ID_ENTITY_CARD = Pattern.compile("\\d{17}[\\d|x|X]|\\d{15}");

    public static final Pattern HH_mm_ss = Pattern.compile("([0-1][0-9]|(2[0-3])):([0-5][0-9]):([0-5][0-9])");

    public static final Pattern YYYY_MM_dd = Pattern.compile("(20\\d{2}([\\.\\-/|年月\\s]{1,3}\\d{1,2}){2}日?)");


    /**
     * 从字符串提取第一个数字(包括整数和小数)
     * @param text 字符串
     * @return 第一个数字
     * 示例:String s = getBeginNumber("QQ323.22EE6666"); //323.22
     */
    public static String getBeginNumber(String text) {
        Matcher m = BEGIN_NUMBER.matcher(text);
        while (m.find()) {
            return m.group();
        }
        return null;
    }

    /**
     * 从字符串提取所有数字(包括整数和小数)
     * @param text 字符串
     * @param separate 分隔符
     * @return 所有数字
     * 示例:String s = getBeginNumber("QQ323.22EE6666"," "); //323.22 6666
     */
    public static String getAllNumber(String text,String separate) {
        StringBuilder stringBuilder = new StringBuilder();
        Matcher m = BEGIN_NUMBER.matcher(text);
        while (m.find()) {
            stringBuilder.append(m.group() + separate);
        }
        return stringBuilder.toString();
    }

    /**
     * 从字符串提取最后一个数字(包括整数和小数)
     * @param text 字符串
     * @return 最后一个数字
     * 示例:String s = getBeginNumber("QQ323.22EE5.6666"); //5.6666
     */
    public static String getLastNumber(String text) {
        Matcher m = LAST_NUMBER.matcher(text.trim());
        while (m.find()) {
            return m.group();
        }
        return null;
    }

    /**
     * 从字符串提取第一个整数
     * @param text 字符串
     * @return 第一个整数
     * 示例:String s = getBeginNumber("QQ323.22EE5.6666"); //323
     */
    public static String getBeginInteger(String text) {
        Matcher m = BEGIN_INTEGER.matcher(text);
        while (m.find()) {
            return m.group();
        }
        return null;
    }

    /**
     * 从字符串提取所有整数
     * @param text 字符串
     * @param separate 分隔符
     * @return 所有整数
     * 示例:String s = getBeginNumber("QQ323.22EE6666"," "); //323 22 6666
     */
    public static String getAllInteger(String text, String separate) {
        StringBuilder stringBuilder = new StringBuilder();
        Matcher m = BEGIN_INTEGER.matcher(text);
        while (m.find()) {
            stringBuilder.append(m.group() + separate);
        }
        return stringBuilder.toString();
    }


    /**
     * 从text提取最后一个整数
     * @param text 字符串
     * @return 最后一个整数
     * 示例:String s = getLastInteger("QQ323.22EE5.6666"); //6666
     */
    public static String getLastInteger(String text) {
        Matcher m = LAST_INTEGER.matcher(text);
        while (m.find()) {
            return m.group();
        }
        return null;
    }


    /**
     * 从text提取手机号码
     * @param text 字符串
     * @return 手机号
     */
    public static String getPhone(String text) {
        Matcher matcher = PHONE.matcher(text);
        while (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 从text提取时分秒  格式 HH:mm:ss
     */
    public static String getHHmmss(String text) {
        Matcher matcher = HH_mm_ss.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 从text提取年月日 格式 yyyy-MM-dd | yyyy/MM/dd
     */
    public static String getYYYYMMdd(String text) {
        Matcher matcher = YYYY_MM_dd.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 从text提取身份证
     */
    public static String getIdentityCard(String text) {
        Matcher matcher = ID_ENTITY_CARD.matcher(text);
        while (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 提取中间text
     * @param text   ra0.8hm
     * @param prefix ra
     * @param suffix hm
     * @return 0.8
     * 如果是 (xxx) 123 $  提去中间的，prefix参数和suffix中的特殊符号需要用\\转义     \\(xxx\\) 123 \$
     */
    public static String getMiddle(String text, String prefix, String suffix) {
        Pattern phonePattern = Pattern.compile("(?<=" + prefix + ").*?(?=" + suffix + ")");
        Matcher matcher = phonePattern.matcher(text);
        while (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

}
