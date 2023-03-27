package com.chenyudaima.web.controller.home.system;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysRole;
import com.chenyudaima.service.SysRoleService;
import com.chenyudaima.vo.SysRoleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * @author 沉鱼代码
 * @date 2023/2/19
 */
@RestController
@RequestMapping("/home/system/sysRole")
@RequiredArgsConstructor
public class SysRoleController {
    private final SysRoleService sysRoleService;

    @GetMapping
    public Result<?> query(SysRole sysRole, int page, int pageSize) {
        return sysRoleService.query(sysRole, page, pageSize);
    }

    @PostMapping
    public Result<?> add(@RequestBody SysRoleVo sysRole) {
        return sysRoleService.add(sysRole);
    }

    @PatchMapping
    public Result<?> update(@RequestBody SysRoleVo sysRole) {
        return sysRoleService.update(sysRole);
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteById(@PathVariable String id) {
        return sysRoleService.deleteById(id);
    }

    @DeleteMapping
    public Result<?> deleteByIdBatch(@RequestBody Map<String, Object> map) {
        List<String> ids = (ArrayList<String>) map.get("ids");
        return sysRoleService.deleteByIdBatch(ids.toArray(new String[ids.size()]));
    }

}
