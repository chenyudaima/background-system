package com.chenyudaima.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色表vo
 * @author 沉鱼代码
 * @date 2023/2/28
 */
@Data
public class SysRoleVo implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    private String id;

    /**
     * 角色名称
     */

    private String name;

    /**
     * 角色描述
     */
    private String description;

    private List<String> menuIds;
}
