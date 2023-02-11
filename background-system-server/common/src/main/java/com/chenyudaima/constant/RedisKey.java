package com.chenyudaima.constant;

/**
 * Redis常量
 * @author 沉鱼代码
 * @date 2023/2/8
 */
public class RedisKey {

    /**
     * 储存每个唯一的请求，防止重放 放在sign目录下的nonce目录 使用字符串数据类型 没有value
     */
    public static final String NONCE = "sign:nonce:";

    /**
     * 储存每一个能使用的token 使用字符串数据类型  value是HashMap类型
     */
    public static final String TOKEN = "user:token:";

    /**
     * 储存所有用户的token，使用hash数据类型 内部key是用户名，value是token
     * 不用设置过期，待用户下次上线直接覆盖就行了
     */
    public static final String TOKEN_ALL = "user:token—all";


}
