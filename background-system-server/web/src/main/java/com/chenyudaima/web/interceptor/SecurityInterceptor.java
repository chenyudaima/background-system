package com.chenyudaima.web.interceptor;

import com.chenyudaima.config.WebMvcConfig;
import com.chenyudaima.constant.HttpHeader;
import com.chenyudaima.constant.RedisKey;
import com.chenyudaima.constant.RequestAttribute;
import com.chenyudaima.exception.SecurityException;
import com.chenyudaima.mapper.SysMenuMapper;
import com.chenyudaima.properties.JwtProperties;
import com.chenyudaima.util.JwtUtil;
import com.chenyudaima.util.RedisUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 安全检查拦截器
 */
@Component
@RequiredArgsConstructor
public class SecurityInterceptor extends Interceptor {
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final JwtProperties jwtProperties;

    private final SysMenuMapper sysMenuMapper;

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
        String token1 = redisUtil.hash_get(RedisKey.TOKEN_ALL, claims.getId());

        if(token1 == null) {
            throw new SecurityException("账号异常，请重新登录");
        }

        if(!token.equals(token1)) {
            throw new SecurityException("账号在另一方已登录");
        }

        //获取redis中token绑定的客户端
        Map<String, String> clientInfoMap = redisUtil.get(token);

        //对比当前请求的客户端是否相同
        Map<String, String> clientInfoMap1 = new HashMap<>();
        clientInfoMap1.put(HttpHeader.K_REQUEST_HEADER_USER_AGENT, request.getHeader(HttpHeader.K_REQUEST_HEADER_USER_AGENT));

        //参数不同说明当前的是非法用户，显示请重新登录
        clientInfoMap.forEach((k,v) -> {
            String value = clientInfoMap1.get(k);
            if(value == null || !value.equals(v)) {
                throw new SecurityException("非法身份");
            }
        });

        //重置redis中的token过期时间
        redisUtil.expire(token, jwtProperties.getExpiration(), TimeUnit.MINUTES);

        //请求的控制器路径
        String path = request.getServletPath().substring(WebMvcConfig.PATH.length());

        //先判断用户是否拥有访问这个页面的权限

        //用户能访问的路径
        List<String> list = new ArrayList<>();
        if(!list.contains(path)) {
            throw new SecurityException("没有权限访问");
        }

        //用户拥有的权限


        //把解析出来的数据放到请求中，内部有用户id，用户名
        request.setAttribute(RequestAttribute.CLAIMS, claims);

        return true;
    }

}
