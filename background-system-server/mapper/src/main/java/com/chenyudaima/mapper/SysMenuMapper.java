package com.chenyudaima.mapper;

import com.chenyudaima.model.SysMenu;
import com.chenyudaima.vo.SysMenuVo;

import java.util.List;


public interface SysMenuMapper {
    List<SysMenu> select(SysMenu sysMenu, int page, int pageSize);

    long selectCount(SysMenu sysMenu);

    /**
     * 根据用户id查询所有菜单
     * @param id
     * @return
     */

    List<SysMenuVo> selectByUserId(String id);


    int insert(SysMenu sysMenu);

    int update(SysMenu sysMenu);

    int deleteById(String id);

    int deleteByIdBatch(String[] ids);

    List<SysMenuVo> selectAll();


}




