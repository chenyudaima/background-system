package com.chenyudaima.service.impl;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.chenyudaima.constant.HttpHeader;
import com.chenyudaima.constant.RedisKey;
import com.chenyudaima.mapper.SysUserMapper;
import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysUser;
import com.chenyudaima.properties.JwtProperties;
import com.chenyudaima.service.LoginService;
import com.chenyudaima.util.JwtUtil;
import com.chenyudaima.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 沉鱼代码
 * @date 2022/12/30
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final JwtUtil jwtUtil;
    private final SysUserMapper sysUserMapper;
    private final RedisUtil redisUtil;
    private final HttpServletRequest request;

    private final JwtProperties jwtProperties;

    @Override
    public Result<?> login(String account, String password) {
        //查询用户
        SysUser sysUser = sysUserMapper.selectUserByAccountAndPassword(account, password);

        if(sysUser == null) {
            throw new RuntimeException("账号或密码错误");
        }

        if(sysUser.getStatus() == 0) {
            throw new RuntimeException("账号被冻结");
        }

        //使用用户名和id创建token
        String jwtUtilToken = jwtUtil.createToken(sysUser.getId(), sysUser.getName());
        String token = RedisKey.TOKEN + jwtUtilToken;

        //如果在TOKEN_ALL内部key存在则直接覆盖（挤下线）
        redisUtil.hash_put(RedisKey.TOKEN_ALL, sysUser.getId(), token);

        //获取客户端信息，给token绑定
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader(HttpHeader.K_REQUEST_USER_AGENT));

        if(jwtProperties.getExpiration() <= 0) {
            //不设置过期时间 除非被挤下，不然不下线
            redisUtil.set(token, userAgent);
        }else {
            //设置过期时间
            redisUtil.set(token, userAgent, jwtProperties.getExpiration(), TimeUnit.MINUTES);
        }

        return Result.success(jwtUtilToken);
    }

}
