package com.chenyudaima.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * jwt配置
 * @author 沉鱼代码
 * @date 2023/3/7
 */
@ConfigurationProperties(prefix = "jwt.config")
public class JwtProperties {
    private String signKey;

    private int expiration;

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }
}
