package com.chenyudaima.config;

import com.chenyudaima.constant.PropertyConstant;
import com.chenyudaima.constant.ResourcesConstant;
import com.chenyudaima.util.file.OpencvUtil;
import org.opencv.core.Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * OpencvUtil配置
 * @author 沉鱼代码
 * @date 2023/1/29
 */
public class OpencvConfig {

    private static final Logger log = LoggerFactory.getLogger(OpencvConfig.class);

    /**
     * dll动态库位置
     */
    private static final String DLL_PATH = System.getProperty(PropertyConstant.JAVA_HOME) + "/bin/" + ResourcesConstant.OPENCV_JAVA3413_DLL;


    /**
     * 加载动态库 Linux环境需要更换Linux的动态库
     */
    public static void init() {
        File file = new File(OpencvConfig.DLL_PATH);

        if(!file.exists()) {
            try(InputStream is = OpencvUtil.class.getResourceAsStream("/" + ResourcesConstant.OPENCV + "/" + ResourcesConstant.OPENCV_JAVA3413_DLL);
                OutputStream os = Files.newOutputStream(file.toPath())
            ) {
                byte[] buffer = new byte[1024];
                while(is.read(buffer) != -1) {
                    os.write(buffer);
                }
                os.flush();
            }catch (Exception e) {
                log.error(ResourcesConstant.OPENCV_JAVA3413_DLL + "移动失败");
            }
        }

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}
