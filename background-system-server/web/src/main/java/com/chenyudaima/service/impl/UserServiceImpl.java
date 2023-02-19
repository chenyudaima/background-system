package com.chenyudaima.service.impl;

import com.chenyudaima.mapper.SysUserMapper;
import com.chenyudaima.model.Result;
import com.chenyudaima.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 沉鱼代码
 * @date 2023/2/19
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SysUserMapper sysUserMapper;

    @Override
    public Result<?> query() {
        return Result.success(sysUserMapper.selectAll());
    }
}
