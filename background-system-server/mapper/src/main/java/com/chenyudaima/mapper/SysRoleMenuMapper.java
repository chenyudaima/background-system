package com.chenyudaima.mapper;

import java.util.List;

/**
* @author cydm
* @description 针对表【sys_role_menu(角色菜单表)】的数据库操作Mapper
* @createDate 2023-02-15 11:11:18
* @Entity com.chenyudaima.model.SysRoleMenu
*/
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




