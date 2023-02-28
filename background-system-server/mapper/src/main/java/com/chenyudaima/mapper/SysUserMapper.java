package com.chenyudaima.mapper;

import com.chenyudaima.model.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenyudaima.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {

    /**
     * 根据用户名和密码查询用户
     * @param account 用户名
     * @param password 密码
     * @return
     */
    SysUser selectUserByAccountAndPassword(String account, String password);

    /**
     * 查询总数据量
     * @return
     */
    long selectCount(SysUser sysUser);

    /**
     * 根据条件查询用户
     * @param page 当前页
     * @param pageSize 每页大小
     * @return
     */
    List<SysUserVo> select(SysUser sysUser, int page, int pageSize);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    SysUser selectById(String id);

    /**
     * 插入用户
     * @param sysUser
     * @return 插入成功的数量
     */
    int insert(SysUser sysUser);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(String id);


    /**
     * 根据多个id删除
     * @param ids
     * @return
     */

    int deleteByIdBatch(String[] ids);


    /**
     * 根据id修改数据
     * @param sysUser
     * @return
     */
    int update(SysUser sysUser);

}




