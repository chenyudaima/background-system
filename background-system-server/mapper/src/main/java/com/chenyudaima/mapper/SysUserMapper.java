package com.chenyudaima.mapper;

import com.chenyudaima.model.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser selectUserByAccountAndPassword(String account, String password);

}




