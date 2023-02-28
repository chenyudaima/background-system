package com.chenyudaima.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.chenyudaima.mapper.SysRoleMapper;
import com.chenyudaima.mapper.SysUserMapper;
import com.chenyudaima.mapper.SysUserRoleMapper;
import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysUser;
import com.chenyudaima.service.UserService;
import com.chenyudaima.vo.SysUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * @author 沉鱼代码
 * @date 2023/2/19
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SysUserMapper sysUserMapper;

    private final SysUserRoleMapper sysUserRoleMapper;

    private final SysRoleMapper sysRoleMapper;

    @Override
    public Result<?> query(SysUser sysUser, int page, int pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userList", sysUserMapper.select(sysUser, page, pageSize));
        map.put("total", sysUserMapper.selectCount(sysUser));
        map.put("roleList", sysRoleMapper.selectAll());
        return Result.success(map);
    }

    @Transactional(timeout = 60)
    public Result<?> add(SysUserVo sysUser) {
        SysUser user = new SysUser();

        BeanUtil.copyProperties(sysUser, user);

        sysUserMapper.insert(user);

        if(sysUser.getRoleIds() != null && sysUser.getRoleIds().size() > 0) {
            sysUserRoleMapper.insertBatch(user.getId(), sysUser.getRoleIds());
        }

        return Result.success();
    }

    @Transactional(timeout = 60)
    public Result<?> update(SysUserVo sysUser) {
        SysUser user = new SysUser();

        BeanUtil.copyProperties(sysUser, user);

        sysUserMapper.update(user);

        sysUserRoleMapper.deleteByUserId(sysUser.getId());

        if(sysUser.getRoleIds() != null && sysUser.getRoleIds().size() > 0) {
            sysUserRoleMapper.insertBatch(sysUser.getId(), sysUser.getRoleIds());
        }

        return Result.success();
    }

    @Transactional(timeout = 60)
    public Result<?> deleteByIdBatch(String[] ids) {
        sysUserMapper.deleteByIdBatch(ids);
        sysUserRoleMapper.deleteByUserIdBatch(ids);
        return Result.success();
    }

    @Transactional(timeout = 60)
    public Result<?> deleteById(String id) {
        sysUserMapper.deleteById(id);
        sysUserRoleMapper.deleteByUserId(id);
        return Result.success();
    }

}
