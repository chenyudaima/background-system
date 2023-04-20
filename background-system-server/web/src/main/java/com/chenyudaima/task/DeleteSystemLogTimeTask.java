package com.chenyudaima.task;

import com.chenyudaima.mapper.CertificateCheckLogMapper;
import com.chenyudaima.mapper.SysInterfaceRequestLogMapper;
import com.chenyudaima.mapper.SysTimedTaskLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
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
public class DeleteSystemLogTimeTask extends TimeTask {

    private final SysTimedTaskLogMapper sysTimedTaskLogMapper;

    private final SysInterfaceRequestLogMapper sysInterfaceRequestLogMapper;

    private final CertificateCheckLogMapper certificateCheckLogMapper;

    String run(Map<String, String> paramMap) throws Exception {
        //删除定时任务日志
        sysTimedTaskLogMapper.deleteByDay(
                Integer.parseInt(paramMap.get("sys_timed_task_log_the_other_day"))
        );

        //删除接口请求日志
        sysInterfaceRequestLogMapper.deleteByDay(
                Integer.parseInt(paramMap.get("sys_interface_request_log_the_other_day"))
        );

        //删除合格证核对日志
        certificateCheckLogMapper.deleteByDay(
                Integer.parseInt(paramMap.get("certificate_check_log_the_other_day"))
        );

        return null;
    }

}
