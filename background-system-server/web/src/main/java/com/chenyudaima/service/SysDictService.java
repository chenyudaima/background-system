package com.chenyudaima.service;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysDictData;
import com.chenyudaima.model.SysDictType;

/**
 * @author 沉鱼代码
 * @date 2023/3/25
 */
public interface SysDictService {
    Result<?> queryType(SysDictType sysDictType, int page, int pageSize);

    Result<?> addType(SysDictType sysDictType);

    Result<?> updateType(SysDictType sysDictType);

    Result<?> deleteTypeByIdBatch(String[] ids);

    Result<?> deleteTypeById(String id);

    Result<?> queryData(SysDictData sysDictData, int page, int pageSize);

    Result<?> addData(SysDictData sysDictData);

    Result<?> updateData(SysDictData sysDictData);

    Result<?> deleteDataById(String id);

    Result<?> deleteDataByIdBatch(String[] ids);

    Result<?> queryDataByDictTypeId(String dictTypeId);

}
