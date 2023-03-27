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
import java.util.*;
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
    private String logPath = System.getProperty(Property.USER_DIR) + "/logs";

    @GetMapping
    public Result<?> query(String type, String filePath) throws Exception {
        Map<String, Object> map = new HashMap<>();

        if(filePath == null || filePath.length() == 0) {
            File file = new File(logPath + "/" + type);
            map.put("fileList", Arrays.stream(file.listFiles()).map(x ->  x.getName()).collect(Collectors.toList()));
            map.put("type", 1);
            return Result.success(map);
        }

        File file = new File(logPath + "/" + type + "/" + filePath);

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<String> list = new ArrayList<>();
            String line;

            while((line = br.readLine()) != null) {
                list.add(line);
            }

            map.put("logList", list);
            map.put("type", 2);

            return Result.success(map);
        }
    }

}
