package com.chenyudaima.web.controller;

import com.chenyudaima.enumeration.OcrEnum;
import com.chenyudaima.model.Result;
import com.chenyudaima.util.file.FileUtil;
import com.chenyudaima.util.file.ImageUtil;
import com.chenyudaima.util.file.OcrUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;


@RestController
@RequestMapping("/index")
public class IndexController {

    @PostMapping("/ocr")
    public Result<?> ocr(String base64) {
        File file = ImageUtil.base64TranImage(base64);

        try {
            return Result.success(OcrUtil.doOCR(file, OcrEnum.CHI_SIM));
        }finally {
            FileUtil.deleteFile(file);
        }

    }
}
