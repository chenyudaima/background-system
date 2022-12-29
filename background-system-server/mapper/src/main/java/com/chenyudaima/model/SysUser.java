package com.chenyudaima.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 用户表
 */
@Data
public class SysUser implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

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

    private static final long serialVersionUID = 1L;
}