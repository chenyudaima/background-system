package com.chenyudaima.web.interceptor;

import com.chenyudaima.constant.HttpHeaderConstant;
import com.chenyudaima.exception.SecurityException;
import com.chenyudaima.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 权限拦截器
 */
@Component
public class SecurityInterceptor extends Interceptor {
    @Autowired
    private JwtUtil jwtUtil;

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

        //如果用户没有token或者验证token失败则抛异常
        String authorization = request.getHeader(HttpHeaderConstant.K_REQUEST_HEADER_AUTHORIZATION);
        if(authorization == null) {
            throw new SecurityException("未登录");
        }

        Claims claims;

        try {
            claims = jwtUtil.parseToken(authorization);

            if(claims == null) throw new SecurityException();
        }catch (Exception e) {
            throw new SecurityException("该token不可用");
        }

        request.setAttribute("claims", claims);

        return true;
    }

}
