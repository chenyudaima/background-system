package com.chenyudaima.web.interceptor;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.chenyudaima.config.WebMvcConfig;
import com.chenyudaima.constant.HttpHeader;
import com.chenyudaima.constant.RedisKey;
import com.chenyudaima.constant.RequestAttribute;
import com.chenyudaima.exception.security.SecurityException;
import com.chenyudaima.exception.security.SecurityPathException;
import com.chenyudaima.mapper.SysMenuMapper;
import com.chenyudaima.properties.JwtProperties;
import com.chenyudaima.util.JwtUtil;
import com.chenyudaima.util.RedisUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        return new String[] {
                "/**"
        };
    }

    @Override
    public String[] getExcludePathPatterns() {
        return new String[] {
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
        String authorization = request.getHeader(HttpHeader.K_REQUEST_AUTHORIZATION);

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

        //查询token是否在这，如果不在说明没登录，如果改变就显示被挤下线
        String token1 = null;
        try {
            token1 = redisUtil.hash_get(RedisKey.TOKEN_ALL, claims.getId());
        }catch (Exception e) {
            throw new SecurityException("账号异常，请重新登录");
        }

        if (token1 == null) {
            throw new SecurityException("账号异常，请重新登录");
        }

        if (!token.equals(token1)) {
            throw new SecurityException("账号在另一方已登录");
        }

        //对比当前请求的客户端是否相同
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader(HttpHeader.K_REQUEST_USER_AGENT));

        //获取redis中token绑定的客户端
        UserAgent userAgent1 = redisUtil.get(token);

        //参数不同说明当前的是非法用户，显示请重新登录

        //判断window还是Android或者其他客户端
        if(userAgent1.getPlatform() != null && !userAgent1.getPlatform().getName().equals(userAgent.getPlatform().getName())) {
            throw new SecurityException("非法身份，请重新登录");
        }

        //判断浏览器名称
        if(userAgent1.getBrowser() != null && !userAgent1.getBrowser().getName().equals(userAgent.getBrowser().getName())) {
            throw new SecurityException("非法身份，请重新登录");
        }

        //请求的控制器路径
        String path = request.getServletPath().substring(WebMvcConfig.PATH.length());

        List<String> securityPaths = redisUtil.get(RedisKey.SECURITY_PATH);

        //判断该路径是否需要权限
        if(securityPaths.contains(path)) {
            //判断用户是否拥有访问该路径的权限
            List<String> strings = sysMenuMapper.selectRouterPathByUserId(claims.getId());
            if(!strings.contains(path)) {
                throw new SecurityPathException("没有权限");
            }
        }

        //把解析出来的数据放到请求中，内部有用户id，用户名
        request.setAttribute(RequestAttribute.CLAIMS, claims);

        //重置redis中的token过期时间
        if(jwtProperties.getExpiration() > 0) {
            redisUtil.expire(token, jwtProperties.getExpiration(), TimeUnit.MINUTES);
        }

        return true;
    }

}
