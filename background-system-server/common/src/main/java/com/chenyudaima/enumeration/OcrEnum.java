package com.chenyudaima.enumeration;

/**
 * OcrUtil语言枚举
 */
public enum OcrEnum {
    /**
     * 英文
     */
    ENG("eng"),

    /**
     * 中文
     */
    CHI_SIM("chi_sim");

    private final String language;

    OcrEnum(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

}
