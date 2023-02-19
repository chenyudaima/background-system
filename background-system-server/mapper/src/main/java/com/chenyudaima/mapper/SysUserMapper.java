package com.chenyudaima.mapper;

import com.chenyudaima.model.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface SysUserMapper {

    SysUser selectUserByAccountAndPassword(String account, String password);

    List<SysUser> selectAll();

    SysUser selectById(String id);
}




