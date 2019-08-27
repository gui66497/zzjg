package com.zzjz.zzjg.bean;

import io.swagger.annotations.ApiModel;

/**
 * @Description 采集代理入库采集数据实体类
 * @Author 房桂堂
 * @Date 2019/8/27 10:52
 */
@ApiModel("采集代理入库采集数据实体")
public class Datagather {

    private String id;

    /**
     * 指标数据详细.
     */
    private String kpiDetail;

    /**
     * 指标数据值.
     */
    private Integer countValue;

    /**
     * 采集时间.
     */
    private String createTime;

    /**
     * KPI Id.
     */
    private String assetKpi;

    /**
     * 当前状态.
     */
    private String currentState;

    /**
     * 监测对象名称.
     */
    private String name;

    /**
     * 监测对象类型.
     */
    private String type;

    /**
     * 监测对象Ip.
     */
    private String ip;

    /**
     * CPU使用率.
     */
    private String cpuUsage;

    /**
     * 内存使用率.
     */
    private String memoryUsage;

    /**
     * 数据类型.
     */
    private Integer valueType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKpiDetail() {
        return kpiDetail;
    }

    public void setKpiDetail(String kpiDetail) {
        this.kpiDetail = kpiDetail;
    }

    public Integer getCountValue() {
        return countValue;
    }

    public void setCountValue(Integer countValue) {
        this.countValue = countValue;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAssetKpi() {
        return assetKpi;
    }

    public void setAssetKpi(String assetKpi) {
        this.assetKpi = assetKpi;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public String getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(String memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public Integer getValueType() {
        return valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }
}
