package com.zzjz.zzjg.bean;

import java.util.Date;

public class AssetHistory {
    /**
     * 资产基本信息
     */
    private Asset asset;
    /**
     * 历史记录id
     */
    private String id;
    /**
     * 资产id
     */
    private String assetId;
    /**
     * ip
     */
    private String ip;
    /**
     * 物理地址
     */
    private String macAddress;

    private Date createTime;

    private Date updateTime;
    /**
     * 详情
     */
    private String detail;
    /**
     * 状态
     */
    private String status;
    /**
     * 省市县地名
     */
    private String address;
    /**
     * 履历信息创建人
     */
    private String createUser;
    /**
     * 履历信息更新人
     */
    private String updateUser;
    /**
     * 责任人
     */
    private String responsible;
    /**
     * 责任人电话
     */
    private String responsiblePhone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getResponsiblePhone() {
        return responsiblePhone;
    }

    public void setResponsiblePhone(String responsiblePhone) {
        this.responsiblePhone = responsiblePhone;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }
}
