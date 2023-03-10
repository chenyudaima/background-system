package com.chenyudaima.model;

import lombok.Data;

import java.io.Serializable;

/**
 * ่ๅ่กจ
 */
@Data
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;

    private String name;

    private String parentId;

    private String routerPath;

    private String routerComponent;
    
    private String icon;

    private String description;

    private Integer order;
}