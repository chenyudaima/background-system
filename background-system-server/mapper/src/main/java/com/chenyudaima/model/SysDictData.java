package com.chenyudaima.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 字典数据表
 */
@Data
public class SysDictData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;

    private String dictTypeId;

    private String name;

    private String value;

    private String description;

    private Integer order;
}