package com.chenyudaima.service.impl;

import com.chenyudaima.mapper.SysDictDataMapper;
import com.chenyudaima.mapper.SysDictTypeMapper;
import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysDictData;
import com.chenyudaima.model.SysDictType;
import com.chenyudaima.service.SysDictService;
import com.chenyudaima.util.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * @author 沉鱼代码
 * @date 2023/3/25
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl implements SysDictService {

    private final SysDictDataMapper sysDictDataMapper;

    private final SysDictTypeMapper sysDictTypeMapper;

    private final Snowflake snowflake;
    @Override
    public Result<?> queryType(SysDictType sysDictType, int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("sysDictTypeList", sysDictTypeMapper.select(sysDictType, page, pageSize));
        map.put("total", sysDictTypeMapper.selectCount(sysDictType));
        return Result.success(map);
    }

    @Override
    public Result<?> addType(SysDictType sysDictType) {
        sysDictType.setId(String.valueOf(snowflake.nextId()));
        return Result.success(sysDictTypeMapper.insert(sysDictType));
    }

    @Override
    public Result<?> updateType(SysDictType sysDictType) {
        return Result.success(sysDictTypeMapper.update(sysDictType));
    }

    @Override
    public Result<?> deleteTypeByIdBatch(String[] ids) {
        return Result.success(sysDictTypeMapper.deleteByIdBatch(ids));
    }

    @Override
    public Result<?> deleteTypeById(String id) {
        return Result.success(sysDictTypeMapper.deleteById(id));
    }

    @Override
    public Result<?> queryData(SysDictData sysDictData, int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("sysDictDataList", sysDictDataMapper.select(sysDictData, page, pageSize));
        map.put("total", sysDictDataMapper.selectCount(sysDictData));
        return Result.success(map);
    }

    @Override
    public Result<?> addData(SysDictData sysDictData) {
        sysDictData.setId(String.valueOf(snowflake.nextId()));
        return Result.success(sysDictDataMapper.insert(sysDictData));
    }

    @Override
    public Result<?> updateData(SysDictData sysDictData) {
        return Result.success(sysDictDataMapper.update(sysDictData));
    }

    @Override
    public Result<?> deleteDataById(String id) {
        return Result.success(sysDictDataMapper.deleteById(id));
    }

    @Override
    public Result<?> deleteDataByIdBatch(String[] ids) {
        return Result.success(sysDictDataMapper.deleteByIdBatch(ids));
    }

    @Override
    public Result<?> queryDataByDictTypeId(String dictTypeId) {
        return Result.success(sysDictDataMapper.selectByDictTypeId(dictTypeId));
    }
}
