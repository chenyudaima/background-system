package com.chenyudaima.web.listener;

import com.chenyudaima.constant.RedisKey;
import com.chenyudaima.mapper.SysMenuMapper;
import com.chenyudaima.mapper.SysTimedTaskMapper;
import com.chenyudaima.model.SysTimedTask;
import com.chenyudaima.task.TaskService;
import com.chenyudaima.util.RedisUtil;
import com.chenyudaima.util.SpringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * Spring容器初始化前,初始化后,销毁前的执行
 * @author 沉鱼代码
 * @date 2023/3/1
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SpringListener implements ApplicationRunner {

    private final SysMenuMapper sysMenuMapper;

    private final RedisUtil redisUtil;


    @PostConstruct
    public void init() {
        log.info("Spring容器正在初始化...");
    }

    @PreDestroy
    public void destroy() {
        log.info("Spring容器销毁...");
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Spring容器初始化完成...");

        //加载定时任务
        List<SysTimedTask> sysTimedTasks = SpringUtil.getBean(SysTimedTaskMapper.class)
                .selectAll();
        for (SysTimedTask sysTimedTask : sysTimedTasks) {
            if(sysTimedTask.getStatus() != 0) {
                TaskService.startTimeTask(sysTimedTask);
            }
        }

        //加载需要权限的路径
        redisUtil.set(RedisKey.SECURITY_PATH, sysMenuMapper.selectSecurityAll());
    }
}
