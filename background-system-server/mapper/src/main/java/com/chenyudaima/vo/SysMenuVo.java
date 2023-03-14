package com.chenyudaima.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单表vo
 */
@Data
public class SysMenuVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;

    private String name;

    private String parentId;

    private String routerPath;

    private String routerComponent;

    private String icon;

    private String description;

    private Integer order;

    /**
     * 子菜单
     */
    private List<SysMenuVo> subMenu;
}
