package com.chenyudaima.web.controller.home.system;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysTimedTask;
import com.chenyudaima.model.SysTimedTaskLog;
import com.chenyudaima.service.TimedTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 定时任务
 * @author 沉鱼代码
 * @date 2023/2/28
 */
@RestController
@RequestMapping("/home/system/sysTimedTask")
@RequiredArgsConstructor
public class SysTimedTaskController {
    private final TimedTaskService timedTaskService;

    @GetMapping
    public Result<?> query(SysTimedTask sysTimedTask, int page, int pageSize) {
        return timedTaskService.query(sysTimedTask, page, pageSize);
    }

    @GetMapping("/log")
    public Result<?> querySysTimedTaskLog(SysTimedTaskLog sysTimedTaskLog, int page, int pageSize) {
        return timedTaskService.querySysTimedTaskLog(sysTimedTaskLog, page, pageSize);
    }

    @PostMapping
    public Result<?> add(@RequestBody SysTimedTask sysTimedTask) {
        return timedTaskService.add(sysTimedTask);
    }

    @PatchMapping
    public Result<?> update(@RequestBody SysTimedTask sysTimedTask) {
        return timedTaskService.update(sysTimedTask);
    }


    @DeleteMapping("/{id}")
    public Result<?> deleteById(@PathVariable String id) {
        return timedTaskService.deleteById(id);
    }


    @DeleteMapping
    public Result<?> deleteByIdBatch(@RequestBody Map<String, Object> map) {
        List<String> ids = (ArrayList<String>) map.get("ids");
        return timedTaskService.deleteByIdBatch(ids.toArray(new String[ids.size()]));
    }

}
