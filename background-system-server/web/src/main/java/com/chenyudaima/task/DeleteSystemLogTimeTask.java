package com.chenyudaima.task;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 删除系统日志任务
 * @author 沉鱼代码
 * @date 2023/3/1
 */
@Component
public class DeleteSystemLogTimeTask extends TimeTask {
    @Override
    Object run(Map<String, String> paramMap) throws Exception {

        System.out.println(JSON.toJSONString(paramMap));

        return null;
    }
}
