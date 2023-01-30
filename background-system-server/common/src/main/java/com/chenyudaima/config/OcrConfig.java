package com.chenyudaima.config;

import com.chenyudaima.constant.EnvConstant;

/**
 * OcrUtil配置
 * @author 沉鱼代码
 * @date 2023/1/14
 */
public class OcrConfig {

    /**
     * tessdata语言库位置
     */
    public static final String DATA_PATH = System.getenv(EnvConstant.TESSDATA_PREFIX);

}
