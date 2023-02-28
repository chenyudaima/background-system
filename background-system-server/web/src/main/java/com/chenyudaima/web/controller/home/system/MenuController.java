package com.chenyudaima.web.controller.home.system;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysMenu;
import com.chenyudaima.service.MenuService;
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
@RequestMapping("/home/system/menu")
@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    public Result<?> query(SysMenu sysMenu, int page, int pageSize) {
        return menuService.query(sysMenu, page, pageSize);
    }

    @PostMapping
    public Result<?> add(@RequestBody SysMenu sysMenu) {
        return menuService.add(sysMenu);
    }

    @PatchMapping
    public Result<?> update(@RequestBody SysMenu sysMenu) {
        return menuService.update(sysMenu);
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteById(@PathVariable("id") String id) {
        return menuService.deleteById(id);
    }

    @DeleteMapping
    public Result<?> deleteByIdBatch(@RequestBody Map<String, Object> map) {
        List<String> ids = (ArrayList<String>) map.get("ids");
        return menuService.deleteByIdBatch(ids.toArray(new String[ids.size()]));
    }
}
