package com.chenyudaima.service.impl;

import com.chenyudaima.mapper.SysMenuMapper;
import com.chenyudaima.mapper.SysRoleMenuMapper;
import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysMenu;
import com.chenyudaima.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 沉鱼代码
 * @date 2023/2/27
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final SysMenuMapper sysMenuMapper;

    private final SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public Result<?> query(SysMenu sysMenu, int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("sysMenuList", sysMenuMapper.select(sysMenu, page, pageSize));
        map.put("total", sysMenuMapper.selectCount(sysMenu));
        return Result.success(map);
    }

    @Override
    public Result<?> add(SysMenu sysMenu) {
        sysMenuMapper.insert(sysMenu);
        return Result.success();
    }

    @Override
    public Result<?> update(SysMenu sysMenu) {
        sysMenuMapper.update(sysMenu);
        return Result.success();
    }

    @Transactional(timeout = 60)
    public Result<?> deleteByIdBatch(String[] ids) {
        sysMenuMapper.deleteByIdBatch(ids);
        sysRoleMenuMapper.deleteByMenuIdBatch(ids);
        return Result.success();
    }

    @Transactional(timeout = 60)
    public Result<?> deleteById(String id) {
        sysMenuMapper.deleteById(id);
        sysRoleMenuMapper.deleteByMenuId(id);
        return Result.success();
    }
}
