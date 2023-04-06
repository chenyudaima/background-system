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

}
