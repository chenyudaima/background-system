package com.chenyudaima.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

/**
 * 图片操作工具
 */
public class ImageUtil {
    /**
     * 将图片颜色反转
     */
    public static void inverse(File img) {
        try {
            FileInputStream fileInputStream = new FileInputStream(img);
            BufferedImage image = ImageIO.read(fileInputStream);

            //生成字符图片
            int w = image.getWidth();
            int h = image.getHeight();
            BufferedImage imageBuffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            // 绘制字符
            for (int y = 0; y < h; y++) {
                for (int x = 0; x< w; x++) {
                    int rgb = image.getRGB(x, y);
                    int R = (rgb & 0xff0000) >> 16;
                    int G = (rgb & 0x00ff00) >> 8;
                    int B = rgb & 0x0000ff;

                    //颜色转rgb值
                    int newPixel=0;
                    newPixel=newPixel << 8;
                    newPixel+=255-R;
                    newPixel=newPixel << 8;
                    newPixel+=255-G;
                    newPixel=newPixel << 8;
                    newPixel+=255-B;

                    imageBuffer.setRGB(x,y,newPixel);
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
    public static void turnTif(File img) {
        try {
            String suffix = "tif";

            BufferedImage read = ImageIO.read(img);
            String canonicalPath = img.getCanonicalPath();
            canonicalPath = canonicalPath.substring(0, canonicalPath.lastIndexOf(".") + 1);

            ImageIO.write(read, suffix, new File(canonicalPath + suffix));

            //删除原图片
            while(!img.delete()) {
                System.gc();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
