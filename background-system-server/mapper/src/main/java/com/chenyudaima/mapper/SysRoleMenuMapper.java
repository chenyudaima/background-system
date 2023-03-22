package com.chenyudaima.mapper;

import java.util.List;


public interface SysRoleMenuMapper {

    int deleteByRoleId(String roleId);
    int deleteByRoleIdBatch(String[] roleIds);

    int insertBatch(String roleId, List<String> menuIds);


    int deleteByMenuId(String menuId);

    int deleteByMenuIdBatch(String[] menuIds);

    /**
     * 根据角色id查询所有菜单id
     * @return
     */
    List<String> selectMenuIdByRoleId(String roleId);




}




