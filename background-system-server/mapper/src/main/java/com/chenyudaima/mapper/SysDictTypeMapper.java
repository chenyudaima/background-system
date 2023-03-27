package com.chenyudaima.mapper;

import com.chenyudaima.model.SysDictType;

import java.util.List;

public interface SysDictTypeMapper {

    int insert(SysDictType sysDictType);

    List<SysDictType> select(SysDictType sysDictType, int page, int pageSize);

    long selectCount(SysDictType sysDictType);

    int update(SysDictType sysDictType);

    int deleteByIdBatch(String... ids);

    int deleteById(String id);


}
