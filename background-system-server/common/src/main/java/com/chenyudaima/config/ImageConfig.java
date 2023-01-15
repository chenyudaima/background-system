package com.chenyudaima.config;

/**
 * @author 沉鱼代码
 * @date 2023/1/14
 */
public class ImageConfig {

    static {
        System.load(ClassLoader.getSystemResource("opencv/opencv_java470.dll").getPath());
    }
}
