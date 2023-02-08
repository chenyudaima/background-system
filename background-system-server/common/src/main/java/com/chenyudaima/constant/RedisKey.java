package com.chenyudaima.constant;

/**
 * Redis常量
 * @author 沉鱼代码
 * @date 2023/2/8
 */
public class RedisKey {

    /**
     * 储存每个唯一的请求，防止重放 放在sign目录下的nonce目录 使用字符串数据类型   NONCE + xxxx
     */
    public static final String NONCE = "sign:nonce:";
}
