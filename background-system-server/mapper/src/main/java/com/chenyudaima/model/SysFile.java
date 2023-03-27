package com.chenyudaima.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件表
 * @author 沉鱼代码
 * @date 2023/3/24
 */
@Data
public class SysFile implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     */
    private String id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件md5
     */
    private String fileMd5;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小
     */
    private Double fileSize;
}
