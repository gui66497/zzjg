package com.zzjz.zzjg.bean;

import java.util.Date;

public class CPUConditon {
    private String cpuid;
    /**
     * cpu使用率
     */
    private Integer cpuUtilization;
    /**
     * cpu监控详情
     */
    private String cpuDetail;
    /**
     * cpu值类型
     */
    private Integer cpuValueType;
    /**
     * cpu监控数据收集时间
     */
    private Date cpuCreateTime;

    public String getCpuid() {
        return cpuid;
    }

    public void setCpuid(String cpuid) {
        this.cpuid = cpuid;
    }

    public Integer getCpuUtilization() {
        return cpuUtilization;
    }

    public void setCpuUtilization(Integer cpuUtilization) {
        this.cpuUtilization = cpuUtilization;
    }

    public String getCpuDetail() {
        return cpuDetail;
    }

    public void setCpuDetail(String cpuDetail) {
        this.cpuDetail = cpuDetail;
    }

    public Integer getCpuValueType() {
        return cpuValueType;
    }

    public void setCpuValueType(Integer cpuValueType) {
        this.cpuValueType = cpuValueType;
    }

    public Date getCpuCreateTime() {
        return cpuCreateTime;
    }

    public void setCpuCreateTime(Date cpuCreateTime) {
        this.cpuCreateTime = cpuCreateTime;
    }
}
