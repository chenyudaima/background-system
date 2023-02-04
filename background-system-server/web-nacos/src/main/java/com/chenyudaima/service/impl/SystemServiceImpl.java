package com.chenyudaima.service.impl;

import com.chenyudaima.model.Result;
import com.chenyudaima.service.SystemService;
import com.chenyudaima.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 沉鱼代码
 * @date 2023/1/3
 */
@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Result<?> updateToken(String token) {
        Claims claims = jwtUtil.parseToken(token);
        return Result.success(jwtUtil.createToken(claims.getId(), claims.getSubject(), null));
    }
}
