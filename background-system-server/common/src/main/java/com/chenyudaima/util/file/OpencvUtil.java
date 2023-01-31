package com.chenyudaima.util.file;

import com.chenyudaima.config.OpencvConfig;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * 图片处理工具类
 * @author 沉鱼代码
 * @date 2023/1/14
 */
public class OpencvUtil {

    private static final Logger log = LoggerFactory.getLogger(OpencvUtil.class);

    private final Mat mat;

    private final File file;

    static {
        OpencvConfig.init();
    }

    /**
     * 初始化，将图片载入内存
     * @param file 图片文件
     * @param flags 0灰度图像 3彩色图像
     * @throws IOException
     */
    public OpencvUtil(File file, int flags) throws IOException {
        this.file = file;
        mat = Imgcodecs.imread(file.getCanonicalPath(), flags);
    }


    /**
     * 初始化，将图片载入内存
     * @param file 图片文件
     * @throws IOException
     */
    public OpencvUtil(File file) throws IOException {
        this.file = file;
        mat = Imgcodecs.imread(file.getCanonicalPath());
    }

    /**
     * 转换图像颜色
     * @param imgproc Imgproc.COLOR_XXX
     * @return this
     */
    public OpencvUtil cvtColor(int imgproc) {
        Imgproc.cvtColor(mat, mat, imgproc);
        return this;
    }

    /**
     * 二值化
     * @param thresh
     * @param maxval
     * @param type
     * @return this
     */
    public OpencvUtil threshold(double thresh, double maxval, int type) {
        Imgproc.threshold(mat, mat, thresh, maxval, type);
        return this;
    }


    /**
     * 轮廓查找
     * @param contours
     * @param mode
     * @param method
     * @return this
     */
    public OpencvUtil findContours(List<MatOfPoint> contours, int mode, int method) {
        Imgproc.findContours(mat, contours, mat, mode, method);
        return this;
    }


    /**
     * 查找边缘
     * @param threshold1
     * @param threshold2
     * @param apertureSize
     * @return this
     */
    public OpencvUtil canny(Mat mat, double threshold1, double threshold2, int apertureSize) {
        Imgproc.Canny(this.mat, mat, threshold1, threshold2, apertureSize);
        return this;
    }

    /**
     * 查找直线
     * @param rho
     * @param theta
     * @param threshold
     * @param srn
     * @param stn
     * @param min_theta
     * @param max_theta
     * @return this
     */
    public OpencvUtil houghLines(double rho, double theta, int threshold, double srn, double stn, double min_theta, double max_theta) {
        Imgproc.HoughLines(mat, mat, rho, theta, threshold, srn, stn, min_theta, max_theta);
        return this;
    }


    /**
     * 绘制连接两点的线段
     * @param pt1
     * @param pt2
     * @param color
     * @param thickness
     * @param lineType
     * @param shift
     * @return
     */
    public OpencvUtil line(Point pt1, Point pt2, Scalar color, int thickness, int lineType, int shift) {
        Imgproc.line(mat, pt1, pt2, color, thickness, lineType, shift);
        return this;
    }

    /**
     * 保存处理的图片
     * @param path 保存的路径
     */
    public void save(String path) {
        File save = new File(new File(path).getPath() + "/" + file.getName());
        try {
            Imgcodecs.imwrite(save.getCanonicalPath(), mat);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 保存处理的图片 (覆盖原来的文件)
     */
    public void save() {
        try {
            Imgcodecs.imwrite(file.getCanonicalPath(), mat);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Mat getMat() {
        return mat;
    }
}
