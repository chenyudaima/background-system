package com.chenyudaima.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.chenyudaima.mapper.SysMenuMapper;
import com.chenyudaima.mapper.SysRoleMapper;
import com.chenyudaima.mapper.SysRoleMenuMapper;
import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysRole;
import com.chenyudaima.model.SysUser;
import com.chenyudaima.service.RoleService;
import com.chenyudaima.vo.SysMenuVo;
import com.chenyudaima.vo.SysRoleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 沉鱼代码
 * @date 2023/2/23
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final SysRoleMapper sysRoleMapper;

    private final SysMenuMapper sysMenuMapper;

    private final SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public Result<?> query(SysRole sysRole, int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();

        List<SysRoleVo> roleList = new ArrayList<>();
        for (SysRole role : sysRoleMapper.select(sysRole, page, pageSize)) {
            List<String> list = sysRoleMenuMapper.selectMenuIdByRoleId(role.getId());
            SysRoleVo sysRoleVo = new SysRoleVo();
            BeanUtil.copyProperties(role, sysRoleVo);
            sysRoleVo.setMenuIds(list);
            roleList.add(sysRoleVo);
        }

        map.put("roleList", roleList);


        //查询所有菜单栏
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
                    sysMenuVo1.getSubMenu().add(sysMenuVo);
                    break;
                }
            }

            sysMenus.remove(i);
            i--;
        }

        Collections.reverse(sysMenus);

        map.put("menuList", sysMenus);
        map.put("total", sysRoleMapper.selectCount(sysRole));
        return Result.success(map);
    }

    @Transactional(timeout = 60)
    public Result<?> update(SysRoleVo sysRole) {
        SysRole role = new SysRole();

        BeanUtil.copyProperties(sysRole, role);

        sysRoleMapper.update(role);

        //删除角色的所有角色菜单关联
        sysRoleMenuMapper.deleteByRoleId(sysRole.getId());

        //再进行添加
        if(sysRole.getMenuIds() != null && sysRole.getMenuIds().size() > 0) {
            sysRoleMenuMapper.insertBatch(sysRole.getId(), sysRole.getMenuIds());
        }

        return Result.success();
    }

    @Transactional(timeout = 60)
    public Result<?> add(SysRoleVo sysRole) {

        SysRole role = new SysRole();

        BeanUtil.copyProperties(sysRole, role);

        sysRoleMapper.insert(role);

        if(sysRole.getMenuIds() != null && sysRole.getMenuIds().size() > 0) {
            sysRoleMenuMapper.insertBatch(role.getId(), sysRole.getMenuIds());
        }

        return Result.success();
    }

    @Transactional(timeout = 60)
    public Result<?> deleteById(String id) {
        sysRoleMapper.deleteById(id);

        sysRoleMenuMapper.deleteByRoleId(id);

        return Result.success();
    }

    @Transactional(timeout = 60)
    public Result<?> deleteByIdBatch(String[] ids) {
        sysRoleMapper.deleteByIdBatch(ids);
        sysRoleMenuMapper.deleteByRoleIdBatch(ids);
        return Result.success();
    }
}
