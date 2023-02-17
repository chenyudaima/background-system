package com.chenyudaima.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色表
 */
@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    private Integer id;

    /**
     * 角色名称
     */

    private String name;

    /**
     * 角色描述
     */
    private String description;

}