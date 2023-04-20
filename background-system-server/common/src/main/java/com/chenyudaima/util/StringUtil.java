package com.chenyudaima.util;

import java.nio.charset.StandardCharsets;

/**
 * 字符串工具类
 *
 * @author 沉鱼代码
 * @date 2023/4/3
 */
public class StringUtil {

    /**
     * 字符串转16进制字符串
     *
     * @param str 字符串
     * @return 16进制字符串
     */
    public static String stringToHex(String str) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char c : str.toCharArray()) {
            stringBuilder.append(Integer.toHexString(c));
        }

        return stringBuilder.toString();
    }

    /**
     * 16进制字符串转字符串
     * @param str 16进制字符串
     * @return 字符串
     */
    public static String hexToString(String str) {
        byte[] baKeyword = new byte[str.length() / 2];

        for (int i = 0; i < baKeyword.length; i++) {
            baKeyword[i] = (byte) (0xff & Integer.parseInt(str.substring(
                        i * 2, i * 2 + 2), 16));
        }

        return new String(baKeyword, StandardCharsets.UTF_8);
    }

    /**
     * @param scanCodeResult 真实数据
     * @param cardOcrResult 识别数据
     * @return 对比数据 （真实数据，对比不同则变红）
     */
    public static String contrast(String scanCodeResult, String cardOcrResult) {
        StringBuilder stringBuilder = new StringBuilder();

        char[] scr = scanCodeResult.toCharArray();

        char[] cor = cardOcrResult.toCharArray();

        //如果字符串长度相等
        if(scr.length == cor.length) {
            for (int i = 0; i < scr.length; i++) {
                if(scr[i] == cor[i]) {
                    stringBuilder.append(scr[i]);
                }else {
                    stringBuilder
                            .append("<font color='red'>")
                            .append(scr[i])
                            .append("</font>");
                }
            }
            return stringBuilder.toString();
        }


        //如果长度不相等，则从不相等的char开始后面所有变红
        int length;
        if(scr.length < cor.length) {
            length = scr.length;
        }else {
            length = cor.length;
        }

        int index = -1;
        for (int i = 0; i < length; i++) {
            if(scr[i] != cor[i]) {
                index = i;
            }
        }

        if(index != -1) {
            stringBuilder.append(scanCodeResult.substring(0, index))
                    .append("<font color='red'>")
                    .append(scanCodeResult.substring(index))
                    .append("</font>");
        }

        return stringBuilder.toString();

    }

}
