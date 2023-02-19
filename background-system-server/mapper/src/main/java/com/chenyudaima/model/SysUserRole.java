package com.chenyudaima.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色表
 */
@Data
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private Integer userId;

    private Integer roleId;

}