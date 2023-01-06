package com.chenyudaima.util;

import com.chenyudaima.config.OcrConfig;
import com.chenyudaima.enumeration.OcrEnum;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 图片文字识别工具类
 */
public class OcrUtil {
    private static final Map<OcrEnum, ITesseract> map = new ConcurrentHashMap<>();

    static {
        for (OcrEnum value : OcrEnum.values()) {
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(OcrConfig.DATAPATH);
            tesseract.setLanguage(value.getLanguage());
            map.put(value, tesseract);
        }
    }

    public static String doOCR(File img, OcrEnum language) {
        try {
            return map.get(language).doOCR(img);
        }catch (Exception e) {
            return null;
        }
    }
}
