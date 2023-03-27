package com.chenyudaima.web.controller.home.system;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysDictData;
import com.chenyudaima.model.SysDictType;
import com.chenyudaima.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 字典管理
 * @author 沉鱼代码
 * @date 2023/3/25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/home/system/sysDict")
public class SysDictController {

    private final SysDictService sysDictService;


    @GetMapping("/type")
    public Result<?> queryType(SysDictType sysDictType, int page, int pageSize) {
        return sysDictService.queryType(sysDictType, page, pageSize);
    }

    @PostMapping("/type")
    public Result<?> addType(@RequestBody SysDictType sysDictType) {
        return sysDictService.addType(sysDictType);
    }

    @PatchMapping("/type")
    public Result<?> updateType(@RequestBody SysDictType sysDictType) {
        return sysDictService.updateType(sysDictType);
    }

    @DeleteMapping("/type")
    public Result<?> deleteTypeByIdBatch(@RequestBody Map<String, Object> map) {
        List<String> ids = (ArrayList<String>) map.get("ids");
        return sysDictService.deleteTypeByIdBatch(ids.toArray(new String[ids.size()]));
    }

    @DeleteMapping("/type/{id}")
    public Result<?> deleteTypeById(@PathVariable("id") String id) {
        return sysDictService.deleteTypeById(id);
    }

    @GetMapping("/data")
    public Result<?> queryData(SysDictData sysDictData, int page, int pageSize) {
        return sysDictService.queryData(sysDictData, page, pageSize);
    }

    @GetMapping("/dataAll")
    public Result<?> queryDataByDictTypeId(String dictTypeId) {
        return sysDictService.queryDataByDictTypeId(dictTypeId);
    }

    @PostMapping("/data")
    public Result<?> addData(@RequestBody SysDictData sysDictData) {
        return sysDictService.addData(sysDictData);
    }

    @PatchMapping("/data")
    public Result<?> updateData(@RequestBody SysDictData sysDictData) {
        return sysDictService.updateData(sysDictData);
    }

    @DeleteMapping("/data/{id}")
    public Result<?> deleteDataById(@PathVariable("id") String id) {
        return sysDictService.deleteDataById(id);
    }

    @DeleteMapping("/data")
    public Result<?> deleteDataByIdBatch(@RequestBody Map<String, Object> map) {
        List<String> ids = (ArrayList<String>) map.get("ids");
        return sysDictService.deleteDataByIdBatch(ids.toArray(new String[ids.size()]));
    }

}
