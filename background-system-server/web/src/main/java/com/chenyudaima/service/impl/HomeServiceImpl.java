package com.chenyudaima.service.impl;

import com.chenyudaima.constant.RedisKey;
import com.chenyudaima.constant.RequestAttribute;
import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysUser;
import com.chenyudaima.service.HomeService;
import com.chenyudaima.util.RedisUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 沉鱼代码
 * @date 2023/1/3
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Result<Map<String, Object>> userInfo(int id) {
        //查询用户信息
        Map<String,Object> map = new HashMap<>();
        SysUser sysUser = new SysUser();
        sysUser.setName("王五");

        map.put("sysUser",sysUser);

        return Result.success(map);
    }

    @Override
    public Result<?> logout(String token) {
        Claims claims = (Claims)request.getAttribute(RequestAttribute.CLAIMS);

        redisUtil.hash_delete(RedisKey.TOKEN_ALL, claims.getSubject());

        redisUtil.delete(RedisKey.TOKEN + token);
        return Result.success();
    }
}
