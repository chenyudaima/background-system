package com.chenyudaima.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户表vo
 * @author 沉鱼代码
 * @date 2023/2/23
 */
@Data
public class SysUserVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 状态
     */
    private Integer status;
    private List<String> roleIds;
}
