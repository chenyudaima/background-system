package com.chenyudaima.task;

import com.alibaba.fastjson.JSON;
import com.chenyudaima.mapper.SysTimedTaskLogMapper;
import com.chenyudaima.util.RedisUtil;
import com.github.houbb.auto.log.annotation.AutoLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 定时删除系统日志任务
 * @author 沉鱼代码
 * @date 2023/3/1
 */

/**
 * 如果需要添加多个相同的任务（设置不同的时间执行），则要设置为原型模式(非单例模式)
 * 因为可能多个相同的定时任务，但是参数可能不同，所以不能使用单例模式
 */
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteSystemLogTimeTask extends TimeTask {

    String run(Map<String, String> paramMap) throws Exception {

        log.error("你好");
        return "成功";
    }
}
