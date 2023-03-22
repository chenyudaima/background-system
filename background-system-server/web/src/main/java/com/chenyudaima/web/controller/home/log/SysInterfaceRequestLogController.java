package com.chenyudaima.web.controller.home.log;

import com.chenyudaima.model.Result;
import com.chenyudaima.model.SysInterfaceRequestLog;
import com.chenyudaima.model.SysRole;
import com.chenyudaima.service.RoleService;
import com.chenyudaima.service.SysInterfaceRequestLogService;
import com.chenyudaima.vo.SysRoleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author 沉鱼代码
 * @date 2023/3/22
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/home/log/sysInterfaceRequestLog")
public class SysInterfaceRequestLogController {

    private final SysInterfaceRequestLogService sysInterfaceRequestLogService;

    @GetMapping
    public Result<?> query(SysInterfaceRequestLog sysInterfaceRequestLog, int page, int pageSize) {
        return sysInterfaceRequestLogService.query(sysInterfaceRequestLog, page, pageSize);
    }

}
