package com.chenyudaima.web.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.chenyudaima.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试控制器 不需要权限
 * @author 沉鱼代码
 * @date 2023/2/3
 */
@RestController
@RequestMapping("/test")

public class TestController {

    @NacosInjected
    private NamingService namingService;


    @GetMapping("/1")
    public Result<Instance> w() throws NacosException {

        //获取一个健康的实例（添加订阅者列表，并且如果当前服务宕机，在订阅者列表会自动移除） selectOneHealthyInstance(服务名，组名)
        Instance instance = namingService.selectOneHealthyInstance("web-nacos","TEST");

        //获取实例的元数据
        Map<String, String> metadata = instance.getMetadata();

        return Result.success(instance);
    }
}
