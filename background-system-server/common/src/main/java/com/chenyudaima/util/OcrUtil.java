//package com.chenyudaima.util;
//
//import com.chenyudaima.config.OcrConfig;
//import com.chenyudaima.enumeration.OcrEnum;
//import net.sourceforge.tess4j.ITesseract;
//import net.sourceforge.tess4j.Tesseract;
//import java.io.File;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * 图片文字识别工具类
// */
//public class OcrUtil {
//    private static final Map<OcrEnum, ITesseract> TESSERACT_MAP = new ConcurrentHashMap<>();
//
//    static {
//        for (OcrEnum value : OcrEnum.values()) {
//            Tesseract tesseract = new Tesseract();
//            tesseract.setDatapath(OcrConfig.DATA_PATH);
//            tesseract.setLanguage(value.getLanguage());
//            TESSERACT_MAP.put(value, tesseract);
//        }
//    }
//
//    /**
//     * @param image 图片
//     * @param language 语言库
//     * @return 识别的内容
//     */
//    public static String doOCR(File image, OcrEnum language) {
//        try {
//            return TESSERACT_MAP.get(language).doOCR(image);
//        }catch(Exception e) {
//            return null;
//        }
//    }
//
//}
