package com.chenyudaima.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * 定时任务基类
 *
 * @author 沉鱼代码
 * @date 2023/2/28
 */
public abstract class TimeTask implements Runnable {

    private Map<String, String> paramMap;

    @Override
    public void run() {
        Object result = null;
        try {
            result = run(paramMap);
        } catch (Exception e) {
            //System.out.println(o.toString());//任务的返回
            //e.toString() 异常信息
            //e.getStackTrace()[0].toString() 异常定位
        }
    }

    public void setParam(String param) {
        if (param == null) {
            return;
        }

        paramMap = JSON.parseObject(param, new TypeReference<Map<String, String>>() {});
    }


    abstract Object run(Map<String, String> paramMap) throws Exception;
}
