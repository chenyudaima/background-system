package com.chenyudaima.mapper;

import com.chenyudaima.model.SysDictData;

import java.util.List;

public interface SysDictDataMapper {

    int insert(SysDictData sysDictData);

    List<SysDictData> select(SysDictData sysDictData, int page, int pageSize);

    long selectCount(SysDictData sysDictData);

    int update(SysDictData sysDictData);

    int deleteById(String id);

    int deleteByIdBatch(String... ids);

    List<SysDictData> selectByDictTypeId(String dictTypeId);

}
