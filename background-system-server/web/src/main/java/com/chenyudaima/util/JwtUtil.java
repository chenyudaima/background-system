package com.chenyudaima.util;


import io.jsonwebtoken.*;
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
    @Value("${jwt.config.signKey}")
    private String signWith;

    /**
     * 创建token
     */
    public String createToken(String id, String subject) {
        return createToken(id, subject, null);
    }

    /**
     * 创建token
     */
    public String createToken(String id, String subject, Map<String,Object> map) {
        JwtBuilder jwtBuilder = Jwts.builder()
                //id (用户id)
                .setId(id)

                //主体 (用户名)
                .setSubject(subject)

                //签名时间
                .setIssuedAt(new Date())

                //加密方式和key
                .signWith(SignatureAlgorithm.HS512,signWith);

        //过期时间在redis设置
        //if(expiration > 0) {
        //    //设置过期时间
        //    jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + expiration));
        //}

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
        return Jwts.parser().setSigningKey(signWith).parseClaimsJws(token).getBody();
    }
}
