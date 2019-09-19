package com.zzjz.zzjg.bean;

import java.util.Date;

public class MemoryCondition {
    private String memoryid;
    /**
     * cpu使用率
     */
    private Integer memoryUtilization;
    /**
     * cpu监控详情
     */
    private String memoryDetail;
    /**
     * cpu值类型
     */
    private Integer memoryValueType;
    /**
     * cpu监控数据收集时间
     */
    private Date memoryCreateTime;

    public String getMemoryid() {
        return memoryid;
    }

    public void setMemoryid(String memoryid) {
        this.memoryid = memoryid;
    }

    public Integer getMemoryUtilization() {
        return memoryUtilization;
    }

    public void setMemoryUtilization(Integer memoryUtilization) {
        this.memoryUtilization = memoryUtilization;
    }

    public String getMemoryDetail() {
        return memoryDetail;
    }

    public void setMemoryDetail(String memoryDetail) {
        this.memoryDetail = memoryDetail;
    }

    public Integer getMemoryValueType() {
        return memoryValueType;
    }

    public void setMemoryValueType(Integer memoryValueType) {
        this.memoryValueType = memoryValueType;
    }

    public Date getMemoryCreateTime() {
        return memoryCreateTime;
    }

    public void setMemoryCreateTime(Date memoryCreateTime) {
        this.memoryCreateTime = memoryCreateTime;
    }
}
