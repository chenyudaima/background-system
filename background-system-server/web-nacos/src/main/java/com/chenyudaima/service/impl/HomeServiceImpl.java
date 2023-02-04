package com.chenyudaima.service.impl;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysUser;
import com.chenyudaima.service.HomeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 沉鱼代码
 * @date 2023/1/3
 */
@Service
public class HomeServiceImpl implements HomeService {



    @Override
    public Result<Map<String, Object>> userInfo(int id) {
        //查询用户信息
        Map<String,Object> map = new HashMap<>();
        SysUser sysUser = new SysUser();
        sysUser.setName("王五");

        map.put("sysUser",sysUser);

        return Result.success(map);
    }
}
