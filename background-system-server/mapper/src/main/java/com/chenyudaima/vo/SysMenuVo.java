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
    private Integer id;

    private String name;

    private Integer parentId;

    private String routerPath;

    private String routerComponent;

    private String description;

    private List<SysMenuVo> children;
}
