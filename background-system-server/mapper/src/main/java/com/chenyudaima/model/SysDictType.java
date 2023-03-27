package com.chenyudaima.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 字典类型表
 */
@Data
public class SysDictType implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;

    private String name;
    private String description;
}