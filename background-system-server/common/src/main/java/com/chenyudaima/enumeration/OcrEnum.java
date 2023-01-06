package com.chenyudaima.enumeration;

/**
 * Ocr语言枚举
 */
public enum OcrEnum {
    CHI_SIM("chi_sim");

    private final String language;

    OcrEnum(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

}
