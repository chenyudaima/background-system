package com.chenyudaima.service;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysMenu;

/**
 * @author 沉鱼代码
 * @date 2023/2/27
 */
public interface MenuService {
    Result<?> query(SysMenu sysMenu, int page, int pageSize);

    Result<?> add(SysMenu sysMenu);

    Result<?> update(SysMenu sysMenu);

    Result<?> deleteByIdBatch(String[] ids);

    Result<?> deleteById(String id);
}
