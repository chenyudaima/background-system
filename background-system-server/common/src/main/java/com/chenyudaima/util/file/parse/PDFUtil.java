package com.chenyudaima.util.file.parse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;

/**
 * pdf文件操作工具类
 */
public class PDFUtil {
    /**
     * 读取pdf文件总页数
     */
    public static Integer readPageSize(File file) {
        //创建文档对象
        try (PDDocument doc = PDDocument.load(file)) {
            //获取文字内容
            return doc.getNumberOfPages();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 读取pdf文件全部文字内容
     */
    public static String readTest(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        //创建文档对象
        try (PDDocument doc = PDDocument.load(file)) {

            //获取PDF文字剥离对象
            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            for (int i = 0; i < doc.getNumberOfPages(); i++) {

                pdfTextStripper.setStartPage(i + 1);
                pdfTextStripper.setEndPage(i + 1);

                //一次输出多个页时，按顺序输出
                pdfTextStripper.setSortByPosition(true);

                //获取第i页文字内容
                stringBuilder.append(pdfTextStripper.getText(doc));
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 分页读取pdf文字内容 下标0开始
     */
    public static String[] readTextPageArray(File file) {
        String[] content = null;

        //创建文档对象
        try (PDDocument doc = PDDocument.load(file)) {

            //拿到pdf页数给数组分配空间
            content = new String[doc.getNumberOfPages()];

            //获取PDF文字剥离对象
            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            for (int i = 0; i < doc.getNumberOfPages(); i++) {

                pdfTextStripper.setStartPage(i + 1);
                pdfTextStripper.setEndPage(i + 1);

                //一次输出多个页时，按顺序输出
                pdfTextStripper.setSortByPosition(true);

                //获取第i页文字内容
                content[i] = pdfTextStripper.getText(doc);
            }
            return content;
        } catch (Exception e) {
            return content;
        }
    }

}
