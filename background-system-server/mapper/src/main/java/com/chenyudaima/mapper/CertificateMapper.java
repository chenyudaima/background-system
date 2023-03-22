package com.chenyudaima.mapper;

import com.chenyudaima.model.Certificate;


public interface CertificateMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Certificate record);

    int insertSelective(Certificate record);

    Certificate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Certificate record);

    int updateByPrimaryKey(Certificate record);

}
