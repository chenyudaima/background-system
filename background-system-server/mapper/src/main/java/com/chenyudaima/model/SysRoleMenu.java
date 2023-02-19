package com.chenyudaima.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色菜单表
 */
@Data
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private Integer roleId;

    private Integer menuId;
}