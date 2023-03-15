package com.chenyudaima.web.controller.home.system;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysUser;
import com.chenyudaima.service.UserService;
import com.chenyudaima.vo.SysUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author 沉鱼代码
 * @date 2023/2/19
 */
@RequestMapping("/home/system/sysUser")
@RestController
@RequiredArgsConstructor
public class SysUserController {

    private final UserService userService;

    @GetMapping
    public Result<?> query(SysUser sysUser, int page, int pageSize) {
        return userService.query(sysUser, page, pageSize);
    }

    @PostMapping
    public Result<?> add(@RequestBody SysUserVo sysUser) {
        return userService.add(sysUser);
    }


    @PatchMapping
    public Result<?> update(@RequestBody SysUserVo sysUser) {
        return userService.update(sysUser);
    }

    @DeleteMapping
    public Result<?> deleteByIdBatch(@RequestBody Map<String, Object> map) {
        List<String> ids = (ArrayList<String>) map.get("ids");
        return userService.deleteByIdBatch(ids.toArray(new String[ids.size()]));
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteById(@PathVariable("id") String id) {
        return userService.deleteById(id);
    }

    @GetMapping("/exportExcel")
    public void export(HttpServletResponse resp) throws Exception {
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type", "application/octet-stream");
        resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("filename", "UTF-8"));



        try (FileInputStream inputStream = new FileInputStream("D:\\screenshot\\Snipaste_2023-02-26_16-49-09.png")) {
            byte[] data = new byte[1024];
            int length;
            while ((length = inputStream.read(data)) > 0) {
                resp.getOutputStream().write(data, 0, length);
            }
            resp.getOutputStream().flush();
        }
    }

}
