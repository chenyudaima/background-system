package com.chenyudaima.web.controller.home.log;

import com.chenyudaima.constant.Property;
import com.chenyudaima.model.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统运行日志
 * @author 沉鱼代码
 * @date 2023/2/28
 */
@RestController
@RequestMapping("/home/log/sysSystemRunLog")
@RequiredArgsConstructor
public class SysSystemRunLogController {

    /**
     * 日志目录
     */
    private String logPath;

    @GetMapping
    public Result<?> query(String filePath) throws Exception {
        if(logPath == null) {
            logPath = System.getProperty(Property.USER_DIR) + "/logs";
        }

        String path;
        if(filePath == null) {
            path = logPath;
        }else {
            path = logPath + "/" + filePath;
        }

        File file = new File(path);

        if(file.isDirectory()) {
            return Result.success(file.listFiles());
        }else {
            BufferedReader br = new BufferedReader(new FileReader(path));
            List<String> list = new ArrayList<>();
            String line;
            while((line = br.readLine()) != null) {
                list.add(line);
            }
            br.close();
            return Result.success(list);
        }
    }

}
