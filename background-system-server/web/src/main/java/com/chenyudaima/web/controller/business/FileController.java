package com.chenyudaima.web.controller.business;

import com.chenyudaima.model.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 沉鱼代码
 * @date 2023/4/28
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/home/business/file")
public class FileController {
    @PostMapping
    public Result<?> upload(MultipartFile file) {
        return Result.success(file.getOriginalFilename() + "预检成功");
    }

}
