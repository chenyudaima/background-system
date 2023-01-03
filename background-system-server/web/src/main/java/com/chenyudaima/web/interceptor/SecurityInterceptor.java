package com.chenyudaima.web.interceptor;

import com.chenyudaima.exception.SecurityException;
import com.chenyudaima.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 沉鱼代码
 * @date 2022/12/21
 */

@Component
public class SecurityInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

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
