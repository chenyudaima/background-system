package com.chenyudaima.util;


import com.alibaba.nacos.api.config.annotation.NacosValue;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * Jwt工具类
 */
@Component
@Slf4j
public class JwtUtil {
    @NacosValue(value = "${jwt.config.signKey}", autoRefreshed = true)
    private String signWith;

    @NacosValue(value = "${jwt.config.expiration}", autoRefreshed = true)
    private int expiration;

    /**
     * 创建token
     */
    public String createToken(String id, String subject, Map<String,Object> map){
        JwtBuilder jwtBuilder = Jwts.builder()
                //id (用户id)
                .setId(id)

                //主体 (用户名)
                .setSubject(subject)

                //签名时间
                .setIssuedAt(new Date())

                //加密方式和key
                .signWith(SignatureAlgorithm.HS512,signWith);

        if(expiration > 0) {
            //设置过期时间
            jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + expiration));
        }

        if(map != null && map.size() > 0) {
            //自定义claim信息
            map.forEach(jwtBuilder::claim);
        }

        return jwtBuilder.compact();
    }

    /**
     * 解析token
     */
    public Claims parseToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(signWith).parseClaimsJws(token).getBody();
        }catch (Exception e) {
            log.error("token不可用!");
        }
        return claims;
    }
}
