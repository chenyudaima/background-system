package com.chenyudaima.service;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysUser;
import com.chenyudaima.vo.SysUserVo;

/**
 * @author 沉鱼代码
 * @date 2023/2/19
 */
public interface SysUserService {
    Result<?> query(SysUser sysUser, int page, int pageSize);

    Result<?> deleteByIdBatch(String... ids);

    Result<?> deleteById(String id);

    Result<?> add(SysUserVo sysUser);

    Result<?> update(SysUserVo sysUser);

}
