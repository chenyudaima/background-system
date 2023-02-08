package com.chenyudaima.util.file;




import com.chenyudaima.constant.FileType;
import com.chenyudaima.constant.Property;

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
        return base64TranImage(base64, new File(System.getProperty(Property.JAVA_IO_TMPDIR) + UUID.randomUUID() + "." + (fileType == null ? FileType.PNG : fileType)));
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
