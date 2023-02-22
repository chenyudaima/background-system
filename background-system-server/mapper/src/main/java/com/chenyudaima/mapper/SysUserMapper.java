package com.chenyudaima.mapper;

import com.chenyudaima.model.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {

    SysUser selectUserByAccountAndPassword(String account, String password);

    List<SysUser> select(@Param("page") int page, @Param("pageSize")int pageSize);

    SysUser selectById(String id);

}




