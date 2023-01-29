package com.chenyudaima.util.file;


import com.chenyudaima.constant.EnvConstant;
import com.chenyudaima.constant.ResourcesConstant;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 图片处理工具类
 * @author 沉鱼代码
 * @date 2023/1/14
 */
public class OpencvUtil {

    private static final Logger log = LoggerFactory.getLogger(OpencvUtil.class);

    static {
        File file = new File(System.getenv(EnvConstant.JAVA_HOME) + "/bin/" + ResourcesConstant.OPENCV_JAVA3413_DLL);

        if(!file.exists()) {
            try(InputStream is = OpencvUtil.class.getResourceAsStream("/opencv/" + ResourcesConstant.OPENCV_JAVA3413_DLL);
                OutputStream os = new FileOutputStream(file)
            ) {

                byte[] buffer = new byte[1024];

                while(is.read(buffer) != -1) {
                    os.write(buffer);
                }
                os.flush();

            }catch (Exception e) {

            }
        }


        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * 图片二值化
     * @param file 图片
     */
    public static void threshold(File file) throws IOException {
        Mat src = Imgcodecs.imread(file.getCanonicalPath(), 3);

        Mat mat = new Mat();

        //使用颜色空间转换函数    第三个参数为具体颜色转化操作
        //意思将RGB颜色的图像转化为灰度图
        Imgproc.cvtColor(src, mat, Imgproc.COLOR_BGR2GRAY);

    }
}
