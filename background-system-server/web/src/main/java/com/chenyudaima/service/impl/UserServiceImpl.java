package com.chenyudaima.service.impl;

import com.chenyudaima.mapper.ExplainMapper;
import com.chenyudaima.mapper.SysUserMapper;
import com.chenyudaima.model.Result;
import com.chenyudaima.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author 沉鱼代码
 * @date 2023/2/19
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SysUserMapper sysUserMapper;

    private final ExplainMapper explainMapper;

    @Override
    public Result<?> query(int page, int pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", sysUserMapper.select(page, pageSize));
        map.put("total", explainMapper.select("sys_user").getRows());
        return Result.success(map);
    }
}
