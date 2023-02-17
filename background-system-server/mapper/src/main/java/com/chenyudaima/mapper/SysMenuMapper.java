package com.chenyudaima.mapper;

import com.chenyudaima.model.SysMenu;
import com.chenyudaima.vo.SysMenuVo;

import java.util.List;

/**
* @author cydm
* @description 针对表【sys_menu(菜单表)】的数据库操作Mapper
* @createDate 2023-02-15 11:11:18
* @Entity com.chenyudaima.model.SysMenu
*/
public interface SysMenuMapper {

    List<SysMenuVo> selectByUserId(String id);

}




