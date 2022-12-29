package com.chenyudaima.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import com.github.jaiimageio.plugins.tiff.TIFFImageWriteParam;
import org.jdesktop.swingx.util.OS;
import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

/**
 * 图片文字识别工具类
 */
public class OcrUtil {


    private static final String EOL = System.getProperty("line.separator");

    /**
     * OCR安装路径
     */
    private static final String OCR_PATH = "D:/dev/Tesseract-OCR";

    /**
     * 默认chi_sim语言包，识别中文
     */
    private static final String LANGUAGE = "chi_sim";

    /**
     * 选择语言命令参数
     */
    private static final String LANG_OPTION = "-l";

    /**
     * 从图片中识别文字
     * @param imageFile 图片文件
     * @return 返回图片的文字
     */
    public static String recognizeText(File imageFile) throws Exception {
        return ocrImages(createImage(imageFile), imageFile);
    }

    /**
     * 识别图片中的文字
     */
    private static String ocrImages(File tempImage, File imageFile) throws IOException, InterruptedException {
        File outputFile = new File(imageFile.getParentFile(), "output");
        // 设置文件隐藏
        Runtime.getRuntime().exec("attrib " + "\"" + outputFile.getAbsolutePath() + "\"" + " +H");
        StringBuffer strB = new StringBuffer();
        List<String> cmd = new ArrayList<>();
        if (OS.isWindowsXP()) {
            cmd.add(OCR_PATH + "/tesseract");
        } else if (OS.isLinux()) {
            cmd.add("tesseract");
        } else {
            cmd.add(OCR_PATH + "/tesseract");
        }
        cmd.add("");
        cmd.add(outputFile.getName());
        cmd.add(LANG_OPTION);
        cmd.add(LANGUAGE);

        ProcessBuilder pb = new ProcessBuilder();
        Map<String, String> env = pb.environment();
        env.put("TESSDATA_PREFIX", OCR_PATH + "/tessdata");
        pb.directory(imageFile.getParentFile());
        cmd.set(1, tempImage.getName());
        pb.command(cmd);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        int w = process.waitFor();

        tempImage.delete();// 删除临时正在工作文件
        if (w == 0) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath() + ".txt"), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                strB.append(str).append(EOL);
            }
            in.close();
        } else {
            String msg;
            switch (w) {
                case 1:
                    msg = "Errors accessing files.There may be spaces in your image's filename.";
                    break;
                case 29:
                    msg = "Cannot recongnize the image or its selected region.";
                    break;
                case 31:
                    msg = "Unsupported image format.";
                    break;
                default:
                    msg = "Errors occurred.";
            }
            tempImage.delete();
            throw new RuntimeException(msg);
        }
        new File(outputFile.getAbsolutePath() + ".txt").delete();
        return strB.toString();
    }

    private static File createImage(File imageFile) throws IOException {
        Iterator<ImageReader> readers = ImageIO.getImageReaders(new FileImageInputStream(imageFile));
        ImageReader reader = readers.next();
        ImageInputStream iis = ImageIO.createImageInputStream(imageFile);
        reader.setInput(iis);

        IIOMetadata streamMetadata = reader.getStreamMetadata();
        TIFFImageWriteParam tiffWriteParam = new TIFFImageWriteParam(Locale.CHINESE);
        tiffWriteParam.setCompressionMode(ImageWriteParam.MODE_DISABLED);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("tiff");
        ImageWriter writer = writers.next();
        BufferedImage bi = reader.read(0);

        IIOImage image = new IIOImage(bi, null, reader.getImageMetadata(0));
        File tempFile = tempImageFile(imageFile);
        ImageOutputStream ios = ImageIO.createImageOutputStream(tempFile);
        writer.setOutput(ios);
        writer.write(streamMetadata, image, tiffWriteParam);

        ios.close();
        iis.close();
        writer.dispose();
        reader.dispose();
        return tempFile;
    }
    private static File tempImageFile(File imageFile) throws IOException {
        String path = imageFile.getPath();
        StringBuffer strB = new StringBuffer(path);
        strB.insert(path.lastIndexOf('.'), "_text_recognize_temp");
        String s = strB.toString().replaceFirst("(?<=//.)(//w+)$", "tif");
        Runtime.getRuntime().exec("attrib " + "\"" + s + "\"" + " +H"); // 设置文件隐藏
        return new File(strB.toString());
    }
}
