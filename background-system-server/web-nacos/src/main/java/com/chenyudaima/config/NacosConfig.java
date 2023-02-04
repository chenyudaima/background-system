package com.chenyudaima.config;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;


/**
 * dataId 对应后台的配置参数
 * groupId 对应后台的配置参数
 * type 配置文件类型
 * autoRefreshed 是否自动刷新
 */

@NacosPropertySource(dataId = NacosConfig.DATA_ID,groupId = NacosConfig.GROUP, type = ConfigType.PROPERTIES,autoRefreshed = true)
@Configuration
public class NacosConfig {

    public static final String DATA_ID = "web-nacos";

    /**
     * 选择Nacos配置管理和服务管理的分组
     */
    public static final String GROUP = "TEST";

    @NacosInjected
    private NamingService namingService;

    @NacosValue("${spring.application.name}")
    private String serviceName;

    @NacosValue("${server.port}")
    private int port;

    /**
     * 当前服务器暴露的ip （用于注册nacos服务）
     */
    public static final String IP = "127.0.0.1";


    /**
     * 服务启动 向nacos注册服务
     * @throws NacosException
     */
    @PostConstruct
    public void init() throws Exception {
        Instance instance = new Instance();
        instance.setIp(IP);
        instance.setPort(port);

        //设置实例的元数据
        instance.setMetadata(new HashMap<>());
        //注册实例（添加在服务列表） registerInstance(服务名，组名，实例)
        namingService.registerInstance(serviceName, GROUP, instance);
    }

}
