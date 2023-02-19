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

}




