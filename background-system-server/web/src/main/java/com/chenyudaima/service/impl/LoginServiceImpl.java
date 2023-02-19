package com.chenyudaima.service.impl;

import com.chenyudaima.constant.RedisKey;
import com.chenyudaima.enumeration.TokenClientEnum;
import com.chenyudaima.mapper.SysUserMapper;
import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysUser;
import com.chenyudaima.service.LoginService;
import com.chenyudaima.util.JwtUtil;
import com.chenyudaima.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

    @Value("${jwt.config.expiration}")
    private int expiration;

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
        String token =  jwtUtil.createToken(sysUser.getId(), sysUser.getName());

        //查询上次登录的token，如果存在则删除 不删除，加强提示功能
        //Optional.ofNullable((String)redisUtil.hash_get(RedisKey.TOKEN_ALL, sysUser.getAccount())).ifPresent(x -> redisUtil.delete(x));

        //如果在TOKEN_ALL内部key存在则直接覆盖（挤下线）
        redisUtil.hash_put(RedisKey.TOKEN_ALL, sysUser.getId(), RedisKey.TOKEN + token);

        //给token绑定客户端信息
        Map<TokenClientEnum, String> tokenClientMap = new HashMap<>();
        tokenClientMap.put(TokenClientEnum.IP, request.getRemoteAddr());
        tokenClientMap.put(TokenClientEnum.HOST, request.getRemoteHost());

        //设置1小时过期时间
        redisUtil.set(RedisKey.TOKEN + token, tokenClientMap, expiration, TimeUnit.MINUTES);

        return Result.success(token);
    }

}
