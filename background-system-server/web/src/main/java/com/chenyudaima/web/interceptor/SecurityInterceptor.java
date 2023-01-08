package com.chenyudaima.web.interceptor;

import com.chenyudaima.exception.SecurityException;
import com.chenyudaima.util.JwtUtil;
import com.chenyudaima.web.interceptor.Interceptor;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class SecurityInterceptor implements Interceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String[] getAddPathPatterns() {
        return new String[]{"/**"};
    }

    @Override
    public String[] getExcludePathPatterns() {
        return new String[]{"/login"};
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        //如果用户没有token或者验证token失败则抛异常
        String token = request.getHeader("Authorization");
        if(token == null) {
            throw new SecurityException("未登录");
        }
        Claims claims = jwtUtil.parseToken(token);

        if(claims == null) {
            throw new SecurityException("该token不可用");
        }

        request.setAttribute("claims",claims);

        return true;
    }

}
