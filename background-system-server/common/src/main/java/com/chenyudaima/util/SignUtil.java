package com.chenyudaima.util;

import org.springframework.util.DigestUtils;

import java.util.Map;

/**
 * 参数签名和验证工具类
 * @author 沉鱼代码
 * @date 2023/2/7
 */
public class SignUtil {
    /**
     * 验证参数是否被篡改
     * @param signature 签名
     * @param paramMap 参数Map
     * @return true 验证成功 ，false 验证失败
     */
    public static boolean jointVerify(String signature, Map<String, String> paramMap) {
        StringBuilder stringBuilder = new StringBuilder();

        paramMap.forEach((k, v) -> {
            stringBuilder.append(k);
            stringBuilder.append("=");
            stringBuilder.append(v);
            stringBuilder.append("&");
        });

        String params = stringBuilder.toString();
        params = params.substring(0, params.length() -1);
        return verify(signature, params);
    }

    /**
     * 验证参数是否被篡改
     * @param signature 签名
     * @param params 参数字符串
     * @return true 验证成功 ，false 验证失败
     */
    public static boolean verify(String signature, String params) {
        if(DigestUtils.md5DigestAsHex(params.getBytes()).toUpperCase().equals(signature)) {
            return true;
        }

        return false;
    }


}
