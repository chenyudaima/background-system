package com.chenyudaima.service;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysRole;
import com.chenyudaima.vo.SysRoleVo;

/**
 * @author 沉鱼代码
 * @date 2023/2/23
 */
public interface SysRoleService {
    Result<?> query(SysRole sysRole, int page, int pageSize);

    Result<?> update(SysRoleVo sysRole);

    Result<?> add(SysRoleVo sysRole);

    Result<?> deleteById(String id);

    Result<?> deleteByIdBatch(String[] ids);

}
