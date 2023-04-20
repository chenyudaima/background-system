package com.chenyudaima.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 合格证质检日志表
 */
@Data
public class CertificateCheckLog implements Serializable {
    private String id;

    /**
     * vin号
     */
    private String vin;

    /**
     * 合格证数据
     */
    private String certificateData;

    /**
     * 铭牌识别数据
     */
    private String nameplateOcrData;

    /**
     * 拓印号识别数据
     */
    private String frontierMarkingOcrData;

    /**
     * 铭牌对比数据
     */
    private String nameplateCheckData;

    /**
     * 拓印号对比数据
     */
    private String frontierMarkingCheckData;

    /**
     * 铭牌图片
     */
    private String nameplateBase64;

    /**
     * 拓印号图片
     */
    private String frontierMarkingBase64;

    /**
     * 0 NG    1 OK
     */
    private Integer status;

    /**
     * 0上线   1下线
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}