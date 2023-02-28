package com.chenyudaima.mapper;

import java.util.List;

/**
* @author cydm
* @description 针对表【sys_user_role(用户角色表)】的数据库操作Mapper
* @createDate 2023-02-15 11:11:18
* @Entity com.chenyudaima.model.SysUserRole
*/
public interface SysUserRoleMapper {

    /**
     * 根据用户id和多个角色id插入数据
     * @param userId
     * @param roleIds
     * @return
     */

    int insertBatch(String userId, List<String> roleIds);

    /**
     * 根据多个用户id删除
     * @param userIds
     */

    void deleteByUserIdBatch(String[] userIds);

    /**
     * 根据用户id删除
     * @param userId
     */
    void deleteByUserId(String userId);

}




