package com.chenyudaima.properties;


import com.chenyudaima.util.Snowflake;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * snowflake配置
 * @author 沉鱼代码
 * @date 2023/3/7
 */
@ConfigurationProperties(prefix = "snowflake.config")
@Validated
public class SnowflakeProperties {

    /**
     * 数据中心id
     */
    @Max(Snowflake.MAX_DATACENTER_NUM)
    @Min(0)
    private long datacenterId;

    /**
     * 机器标识id
     */
    @Max(Snowflake.MAX_MACHINE_NUM)
    @Min(0)
    private long machineId;


    public long getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(long datacenterId) {
        this.datacenterId = datacenterId;
    }

    public long getMachineId() {
        return machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }
}
