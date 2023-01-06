package com.chenyudaima.web.controller;

import com.chenyudaima.enumeration.OcrEnum;
import com.chenyudaima.model.Result;
import com.chenyudaima.util.file.FileUtil;
import com.chenyudaima.util.file.OcrUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author 沉鱼代码
 * @date 2023/1/4
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @PostMapping("/ocr")
    public Result<?> ocr(MultipartFile file) {
        if(file.isEmpty()) {
            return Result.error("文件为空");
        }

        File fileTemp = new File(System.getProperty("java.io.tmpdir") + file.getOriginalFilename());


        try (FileOutputStream os = new FileOutputStream(fileTemp)) {
            os.write(file.getBytes());
            return new Result<>(200, "11", OcrUtil.doOCR(fileTemp, OcrEnum.CHI_SIM));
        } catch (Exception e) {
            return Result.error("失败," + e.getMessage());
        }finally {
            FileUtil.deleteFile(fileTemp);
        }
    }
}
