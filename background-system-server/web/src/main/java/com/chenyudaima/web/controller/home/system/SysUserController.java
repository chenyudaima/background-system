package com.chenyudaima.web.controller.home.system;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysUser;
import com.chenyudaima.service.SysUserService;
import com.chenyudaima.vo.SysUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author 沉鱼代码
 * @date 2023/2/19
 */
@RequestMapping("/home/system/sysUser")
@RestController
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    @GetMapping
    public Result<?> query(SysUser sysUser, int page, int pageSize) {
        return sysUserService.query(sysUser, page, pageSize);
    }

    @PostMapping
    public Result<?> add(@RequestBody SysUserVo sysUser) {
        return sysUserService.add(sysUser);
    }


    @PatchMapping
    public Result<?> update(@RequestBody SysUserVo sysUser) {
        return sysUserService.update(sysUser);
    }

    @DeleteMapping
    public Result<?> deleteByIdBatch(@RequestBody Map<String, Object> map) {
        List<String> ids = (ArrayList<String>) map.get("ids");
        return sysUserService.deleteByIdBatch(ids.toArray(new String[ids.size()]));
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteById(@PathVariable("id") String id) {
        return sysUserService.deleteById(id);
    }

}
