package com.chenyudaima.service.impl;

import com.chenyudaima.mapper.SysMenuMapper;
import com.chenyudaima.mapper.SysRoleMenuMapper;
import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysMenu;
import com.chenyudaima.service.MenuService;
import com.chenyudaima.vo.SysMenuVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public Result<?> query() {
        List<SysMenuVo> sysMenus = sysMenuMapper.selectAll();
        int headIndex = 0;


        for (int i = sysMenus.size() - 1; i >= headIndex;) {

            SysMenuVo sysMenuVo = sysMenus.get(i);

            //如果是父菜单，则把元素放在最前面
            if(sysMenuVo.getParentId() == null) {
                sysMenus.add(0, sysMenus.remove(i));
                headIndex ++;
                continue;
            }

            //如果是子菜单，查找它的父菜单，然后放进去，再从list中删除这个元素
            for (int j = i - 1; j >= 0; j--) {
                SysMenuVo sysMenuVo1 = sysMenus.get(j);
                if(sysMenuVo1.getId().equals(sysMenuVo.getParentId())) {

                    if(sysMenuVo1.getSubMenu() == null) {
                        sysMenuVo1.setSubMenu(new ArrayList<>());
                    }

                    //把子菜单设置给父菜单
                    sysMenuVo1.getSubMenu().add(sysMenuVo);
                    break;
                }
            }

            sysMenus.remove(i);
            i--;
        }

        Collections.reverse(sysMenus);

        return Result.success(sysMenus);
    }

    @Override
    public Result<?> add(SysMenu sysMenu) {
        sysMenuMapper.insert(sysMenu);
        return Result.success();
    }

    @Override
    public Result<?> update(SysMenu sysMenu) {
        return Result.success(sysMenuMapper.update(sysMenu));
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
