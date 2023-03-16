package com.chenyudaima.mapper;

import com.chenyudaima.model.SysMenu;
import com.chenyudaima.vo.SysMenuVo;

import java.util.List;


public interface SysMenuMapper {

    /**
     * 根据用户id查询所有菜单
     * @param id
     * @return
     */

    List<SysMenuVo> selectByUserId(String id);

    /**
     * 根据用户id查询所有路由路径
     * @param id
     * @return
     */
    List<String> selectRouterPathByUserId(String id);

    int insert(SysMenu sysMenu);

    int update(SysMenu sysMenu);

    int deleteById(String id);

    int deleteByIdBatch(String[] ids);

    List<SysMenuVo> selectAll();

    List<String> selectSecurityAll();

}




