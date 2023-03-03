package com.chenyudaima.web.controller.home.system;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysRole;
import com.chenyudaima.model.SysTimedTask;
import com.chenyudaima.service.RoleService;
import com.chenyudaima.service.TimedTaskService;
import com.chenyudaima.vo.SysRoleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 沉鱼代码
 * @date 2023/2/28
 */
@RestController
@RequestMapping("/home/system/timedTask")
@RequiredArgsConstructor
public class TimedTaskController {
    private final TimedTaskService timedTaskService;

    @GetMapping
    public Result<?> query(SysTimedTask sysTimedTask, int page, int pageSize) {
        return timedTaskService.query(sysTimedTask, page, pageSize);
    }

    //@PostMapping
    //public Result<?> add(@RequestBody SysRoleVo sysRole) {
    //    return timedTaskService.add(sysRole);
    //}
    //
    //@PatchMapping
    //public Result<?> update(@RequestBody SysRoleVo sysRole) {
    //    return timedTaskService.update(sysRole);
    //}
    //
    //@DeleteMapping("/{id}")
    //public Result<?> deleteById(@PathVariable String id) {
    //    return timedTaskService.deleteById(id);
    //}
    //
    //@DeleteMapping
    //public Result<?> deleteByIdBatch(@RequestBody Map<String, Object> map) {
    //    List<String> ids = (ArrayList<String>) map.get("ids");
    //    return timedTaskService.deleteByIdBatch(ids.toArray(new String[ids.size()]));
    //}

}
