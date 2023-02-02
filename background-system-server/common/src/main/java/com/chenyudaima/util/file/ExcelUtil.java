package com.chenyudaima.util.file;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel文件操作工具类
 */
public class ExcelUtil {

    public static List<List<List<String>>> parse(File file) throws Exception {
        Workbook workbook = WorkbookFactory.create(file);

        //整个文件数据
        List<List<List<String>>> listList = new ArrayList<>();

        for(int i = 0; i < workbook.getNumberOfSheets(); i++) {
            //获取工作簿第i张表格
            Sheet sheet = workbook.getSheetAt(i);

            //第i张表格数据
            List<List<String>> lists = new ArrayList<>();

            for(int j = 0; j <= sheet.getLastRowNum(); j++) {
                //获取表格第j行数据
                Row row = sheet.getRow(j);

                List<String> list = new ArrayList<>();

                for(Cell cell : row) {
                    list.add(cell.toString());
                }

                lists.add(list);
            }

            listList.add(lists);
        }

        return listList;
    }

}
