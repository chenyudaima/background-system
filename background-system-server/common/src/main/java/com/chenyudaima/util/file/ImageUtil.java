package com.chenyudaima.util.file;

import com.chenyudaima.constant.FileType;
import com.chenyudaima.constant.Property;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

/**
 * 图片操作工具
 */
public class ImageUtil {

    /**
     * 检测图片清晰度
     * @param file 图片
     * @return 17以上算清晰 超过30是小图片
     */
    public static double sharpness(File file) throws Exception {
        opencv_core.Mat srcImage = opencv_imgcodecs.imread(file.getCanonicalPath());

        opencv_core.Mat dstImage = new opencv_core.Mat();
        //转化为灰度图
        opencv_imgproc.cvtColor(srcImage, dstImage, opencv_imgproc.COLOR_BGR2GRAY);

        opencv_core.Mat laplacianDstImage = new opencv_core.Mat();

        //阈值太低会导致正常图片被误断为模糊图片，阈值太高会导致模糊图片被误判为正常图片
        opencv_imgproc.Laplacian(dstImage, laplacianDstImage, opencv_core.CV_64F);

        //矩阵标准差
        opencv_core.Mat stddev = new opencv_core.Mat();

        //求矩阵的均值与标准差
        opencv_core.meanStdDev(laplacianDstImage, new opencv_core.Mat(), stddev);

        return stddev.createIndexer().getDouble();
    }
    

    /**
     * 图片转base64字符串
     */
    public static String imageTranBase64(File file) {
        Base64.Encoder encoder = Base64.getEncoder();
        try {
            return encoder.encodeToString(Files.readAllBytes(file.toPath()));
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * base64字符串转图片
     */
    public static File base64TranImage(String base64) {
        String fileType = null;
        if(base64.contains("data:image/")) {
            fileType = base64.substring(base64.indexOf("data:image/") + 11, base64.indexOf(";"));

        }
        File file = new File(System.getProperty(Property.JAVA_IO_TMPDIR),UUID.randomUUID() + "." + (fileType == null ? FileType.PNG : fileType));
        return base64TranImage(base64, file);
    }

    /**
     * base64字符串转图片
     */
    public static File base64TranImage(String base64, File file) {
        Base64.Decoder decoder = Base64.getDecoder();
        if(base64.contains("data:")) {
            base64 = base64.substring(base64.indexOf(",") + 1);
        }
        byte[] decode = decoder.decode(base64);
        try(FileOutputStream os = new FileOutputStream(file)) {
            os.write(decode);
            os.flush();
            return file;
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 将图片颜色反转
     */
    public static void imageColorInverse(File img) {

        try {
            FileInputStream fileInputStream = new FileInputStream(img);
            BufferedImage image = ImageIO.read(fileInputStream);

            //生成字符图片
            int w = image.getWidth();
            int h = image.getHeight();
            BufferedImage imageBuffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            // 绘制字符
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int rgb = image.getRGB(x, y);
                    int R = (rgb & 0xff0000) >> 16;
                    int G = (rgb & 0x00ff00) >> 8;
                    int B = rgb & 0x0000ff;

                    //颜色转rgb值
                    int newPixel = 0;
                    newPixel += 255 - R;
                    newPixel = newPixel << 8;
                    newPixel += 255 - G;
                    newPixel = newPixel << 8;
                    newPixel += 255 - B;

                    imageBuffer.setRGB(x, y, newPixel);
                }
            }
            ImageIO.write(imageBuffer, img.getName().substring(img.getName().lastIndexOf(".") + 1), img); //输出图片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 图片转tif
     */
    public static void imageTranTif(File img) {
        try {
            String suffix = "tif";

            BufferedImage read = ImageIO.read(img);
            String canonicalPath = img.getCanonicalPath();
            canonicalPath = canonicalPath.substring(0, canonicalPath.lastIndexOf(".") + 1);

            ImageIO.write(read, suffix, new File(canonicalPath + suffix));

            //删除原图片
            while (!img.delete()) {
                System.gc();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
