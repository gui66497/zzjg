package com.zzjz.zzjg.bean;

import java.util.Date;

public class AssetCondition {
    /**
     * 监控的资源信息
     */
    private Asset asset;
    /**
     * 监控资源的cpu使用率
     */
    private String cpuUtilization;
    /**
     * 监控资源的内存使用率
     */
    private String memoryUtilization;
    /**
     * 监控数据最新的采集时间
     */
    private Date collectTime;

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public String getCpuUtilization() {
        return cpuUtilization;
    }

    public void setCpuUtilization(String cpuUtilization) {
        this.cpuUtilization = cpuUtilization;
    }

    public String getMemoryUtilization() {
        return memoryUtilization;
    }

    public void setMemoryUtilization(String memoryUtilization) {
        this.memoryUtilization = memoryUtilization;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }
}
