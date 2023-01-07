package com.chenyudaima.util.file;

import com.chenyudaima.enumeration.OcrEnum;
import com.chenyudaima.config.OcrConfig;
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
            tesseract.setDatapath(OcrConfig.DATA_PATH);
            tesseract.setLanguage(value.getLanguage());
            map.put(value, tesseract);
        }
    }

    /**
     *
     * @param img 图片
     * @param language 语言库
     * @return
     */
    public static String doOCR(File img, OcrEnum language) {
        try {
            return map.get(language).doOCR(img);
        }catch (Exception e) {
            return null;
        }
    }
}
