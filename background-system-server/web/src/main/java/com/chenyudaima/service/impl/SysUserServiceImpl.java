package com.chenyudaima.service.impl;

import com.chenyudaima.exception.PermissionException;
import com.chenyudaima.mapper.SysUserMapper;
import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysUser;
import com.chenyudaima.service.SysUserService;
import com.chenyudaima.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 沉鱼代码
 * @date 2022/12/30
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public Result<?> login(String account, String password) {
        SysUser sysUser = sysUserMapper.selectUserByAccountAndPassword(account,password);
        if(sysUser == null) {
            throw new RuntimeException("账号或密码错误");
        }

        if(sysUser.getStatus() == 0) {
            throw new RuntimeException("账号被冻结");
        }

        String token = jwtUtil.createToken(sysUser.getAccount(), sysUser.getAccount(), null);

        return Result.success(token);
    }
}
