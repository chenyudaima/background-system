package com.chenyudaima.web.controller.home.system;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysMenu;
import com.chenyudaima.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 * @author 沉鱼代码
 * @date 2023/2/19
 */
@RequestMapping("/home/system/sysMenu")
@RestController
@RequiredArgsConstructor
public class SysMenuController {
    private final SysMenuService sysMenuService;

    @GetMapping
    public Result<?> query() {
        return sysMenuService.query();
    }

    @PostMapping
    public Result<?> add(@RequestBody SysMenu sysMenu) {
        return sysMenuService.add(sysMenu);
    }

    @PatchMapping
    public Result<?> update(@RequestBody SysMenu sysMenu) {
        return sysMenuService.update(sysMenu);
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteById(@PathVariable("id") String id) {
        return sysMenuService.deleteById(id);
    }

    @DeleteMapping
    public Result<?> deleteByIdBatch(@RequestBody Map<String, Object> map) {
        List<String> ids = (ArrayList<String>) map.get("ids");
        return sysMenuService.deleteByIdBatch(ids.toArray(new String[ids.size()]));
    }
}
