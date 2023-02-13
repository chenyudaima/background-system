package com.chenyudaima.web.interceptor;

import com.chenyudaima.constant.HttpHeader;
import com.chenyudaima.constant.HttpMethod;
import com.chenyudaima.constant.RedisKey;
import com.chenyudaima.constant.RequestAttribute;
import com.chenyudaima.enumeration.TokenClientEnum;
import com.chenyudaima.exception.SecurityException;
import com.chenyudaima.util.JwtUtil;
import com.chenyudaima.util.RedisUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 权限拦截器
 */
@Component
public class SecurityInterceptor extends Interceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${jwt.config.expiration}")
    private int expiration;

    @Override
    public String[] getAddPathPatterns() {
        return new String[]{
                "/**"
        };
    }

    @Override
    public String[] getExcludePathPatterns() {
        return new String[]{
                "/login/**",
                "/test/**"
        };
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //请求头没有token说明没有登录
        String authorization = request.getHeader(HttpHeader.K_REQUEST_HEADER_AUTHORIZATION);

        if(authorization == null) {
            throw new SecurityException("未登录");
        }


        //redis中的token
        String token = RedisKey.TOKEN + authorization;

        //查询该token是否过期
        if(!redisUtil.hasKey(token)) {
            throw new SecurityException("账号过期，请重新登录");
        }

        //解析token出来的数据
        Claims claims = jwtUtil.parseToken(authorization);

        //查询token是否在这，如果不在就显示请重新登录，如果改变就显示被挤下线
        String token1 = redisUtil.hash_get(RedisKey.TOKEN_ALL, claims.getSubject());

        if(token1 == null) {
            throw new SecurityException("账号异常，请重新登录");
        }

        if(!token.equals(token1)) {
            throw new SecurityException("账号在另一方已登录");
        }

        //获取redis中token绑定的客户端
        Map<TokenClientEnum, String> tokenClientMap = redisUtil.get(token);

        //对比当前请求的客户端是否相同
        Map<TokenClientEnum, String> map = new HashMap<>();
        map.put(TokenClientEnum.IP, request.getRemoteAddr());
        map.put(TokenClientEnum.HOST, request.getRemoteHost());

        //参数不同说明是被别人登录，或者当前的是非法用户，显示请重新登录
        tokenClientMap.forEach((k,v) -> {
            String value = map.get(k);
            if(value != null && !value.equals(v)) {
                throw new SecurityException("非法身份");
            }
        });

        //重置redis中的token过期时间
        redisUtil.expire(token, expiration, TimeUnit.MINUTES);

        //把解析出来的数据放到请求中，内部有用户id，用户名
        request.setAttribute(RequestAttribute.CLAIMS, claims);


        //从User-Agent请求头获取客户端参数
        return true;
    }

}
