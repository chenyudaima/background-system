package com.chenyudaima.service.impl;

import com.chenyudaima.constant.RedisKey;
import com.chenyudaima.constant.RequestAttribute;
import com.chenyudaima.mapper.SysMenuMapper;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    @Override
    public Result<?> userInfo() {
        Claims claims = (Claims) request.getAttribute(RequestAttribute.CLAIMS);
        return Result.success(claims.getSubject());
    }

    @Override
    public Result<?> logout(String token) {
        Claims claims = (Claims)request.getAttribute(RequestAttribute.CLAIMS);
        redisUtil.hash_delete(RedisKey.TOKEN_ALL, claims.getId());
        redisUtil.delete(RedisKey.TOKEN + token);
        return Result.success();
    }

    @Override
    public Result<?> navigation() {
        Claims claims = (Claims) request.getAttribute(RequestAttribute.CLAIMS);

        //用户id
        String id = claims.getId();

        //查询这个用户的所有菜单栏
        List<SysMenuVo> list = sysMenuMapper.selectByUserId(id);

        //对菜单栏进行解析
        list = setSubmenu(list);

        //过滤只保留没有父id的菜单
        list = list.stream().filter(x -> x.getParentId() == null).collect(Collectors.toList());

        return Result.success(list);
    }


    /**
     * 递归给菜单设置子菜单
     * @param sysMenus 菜单
     * @return 设置完之后的的菜单
     */
    public List<SysMenuVo> setSubmenu(List<SysMenuVo> sysMenus) {
        List<SysMenuVo> list = new ArrayList<>();

        if(sysMenus == null || sysMenus.size() == 0) {
            return list;
        }

        //所有菜单
        for (SysMenuVo sysMenuVo : sysMenus) {
            if(sysMenuVo.getParentId() == null) {

            }


            //当前菜单的所有子菜单
            List<SysMenuVo> collect = sysMenus.stream().filter(x -> x.getParentId() != null && x.getParentId().equals(sysMenuVo.getId())).collect(Collectors.toList());

            //递归当前子菜单是否有子菜单
            //collect = setSubmenu(collect);

            sysMenuVo.setChildren(collect);

            list.add(sysMenuVo);
        }
        return list;
    }

}
