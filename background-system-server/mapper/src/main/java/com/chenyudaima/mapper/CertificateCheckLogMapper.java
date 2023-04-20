package com.chenyudaima.mapper;

import com.chenyudaima.model.CertificateCheckLog;

import java.util.List;

/**
* @author cydm
* @description 针对表【certificate_check_log(合格证核对日志表)】的数据库操作Mapper
* @createDate 2023-04-15 11:04:25
* @Entity com.chenyudaima.model.CertificateCheckLog
*/
public interface CertificateCheckLogMapper {


    int insert(CertificateCheckLog record);


    List<CertificateCheckLog> select(CertificateCheckLog certificateCheckLog, int page, int pageSize);

    long selectCount(CertificateCheckLog certificateCheckLog);

    CertificateCheckLog selectById(String id);

    int deleteByDay(int certificateCheckLogTheOtherDay);


}
