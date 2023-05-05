package com.chenyudaima.service.impl;

import com.chenyudaima.constant.RedisKey;
import com.chenyudaima.constant.RequestAttribute;
import com.chenyudaima.mapper.SysMenuMapper;
import com.chenyudaima.mapper.SysUserMapper;
import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysMenu;
import com.chenyudaima.model.SysUser;
import com.chenyudaima.service.HomeService;
import com.chenyudaima.util.RedisUtil;
import com.chenyudaima.vo.SysMenuVo;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 沉鱼代码
 * @date 2023/1/3
 */
@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {
    private final HttpServletRequest request;
    private final RedisUtil redisUtil;
    private final SysMenuMapper sysMenuMapper;

    private final SysUserMapper sysUserMapper;


    @Override
    public Result<?> userInfo() {
        Claims claims = (Claims) request.getAttribute(RequestAttribute.CLAIMS);
        return Result.success(sysUserMapper.selectById(claims.getId()));
    }

    @Override
    public Result<?> logout(String token) {
        Claims claims = (Claims) request.getAttribute(RequestAttribute.CLAIMS);
        redisUtil.hash_delete(RedisKey.TOKEN_ALL, claims.getId());
        redisUtil.delete(RedisKey.TOKEN + token);
        return Result.success();
    }

    @Override
    public Result<?> menu() {
        Claims claims = (Claims) request.getAttribute(RequestAttribute.CLAIMS);

        //用户id
        String id = claims.getId();

        //根据id查询用户角色

        //查询这个用户的所有菜单栏
        List<SysMenuVo> sysMenus = sysMenuMapper.selectByUserId(id);

        int headIndex = 0;

        for (int i = sysMenus.size() - 1; i >= headIndex;) {

            SysMenuVo sysMenuVo = sysMenus.get(i);

            //如果是父菜单，则把元素放在最前面
            if(sysMenuVo.getParentId() == null) {
                sysMenus.add(0, sysMenus.remove(i));
                headIndex ++;
                continue;
            }

            //如果是子菜单，查找它的父菜单，然后放进去，再从list中删除这个元素
            for (int j = i - 1; j >= 0; j--) {
                SysMenuVo sysMenuVo1 = sysMenus.get(j);
                if(sysMenuVo1.getId().equals(sysMenuVo.getParentId())) {
                    if(sysMenuVo1.getSubMenu() == null) {
                        sysMenuVo1.setSubMenu(new ArrayList<>());
                    }
                    sysMenuVo1.getSubMenu().add(sysMenuVo);
                    break;
                }
            }

            sysMenus.remove(i);
            i--;
        }

        Collections.reverse(sysMenus);

        return Result.success(sysMenus);
    }


}
