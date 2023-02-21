package com.chenyudaima.mapper;

import com.chenyudaima.model.Certificate;

/**
* @author cydm
* @description 针对表【certificate(合格证表)】的数据库操作Mapper
* @createDate 2023-02-20 14:10:08
* @Entity com.chenyudaima.model.Certificate
*/
public interface CertificateMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Certificate record);

    int insertSelective(Certificate record);

    Certificate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Certificate record);

    int updateByPrimaryKey(Certificate record);

}
