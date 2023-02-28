package com.chenyudaima.mapper;


import com.chenyudaima.model.SysRole;
import com.chenyudaima.vo.SysRoleVo;

import java.util.List;

public interface SysRoleMapper {

    /**
     * 根据条件分页查询角色
     * @param sysRole
     * @param page
     * @param pageSize
     * @return
     */
    List<SysRole> select(SysRole sysRole, int page, int pageSize);

    long selectCount(SysRole sysRole);

    /**
     * 查询所有角色
     * @return
     */
    List<SysRole> selectAll();


    int update(SysRole role);

    int insert(SysRole role);

    int deleteById(String id);

    int deleteByIdBatch(String[] ids);

}




