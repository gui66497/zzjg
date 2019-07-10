package com.zzjz.zzjg.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 *
 * @author fgt
 */
@ApiModel("资产实体")
public class Asset {

    private String id;

    @Excel(name = "资产名称", width=20)
    @NotBlank(message = "资产名称不能为空")
    private String name;

    @NotBlank(message = "资产类型不能为空")
    private String type;

    @Excel(name = "资产类型", width=15)
    private String typeName;

    private String organizeId;

    @Excel(name = "组织机构", width=16)
    private String organizeName;

    private String createUser;

    @Excel(name = "创建时间", exportFormat = "yyyy-MM-dd HH:mm:ss", width=20)
    private Date createTime;

    private String updateUser;

    private Date updateTime;

    @Excel(name = "资产IP", width=15)
    private String manageIp;

    @Excel(name = "责任人", width=15)
    private String responsible;

    @Excel(name = "责任电话", width=15)
    private String responsiblePhone;

    @Excel(name = "地理位置", width=15)
    private String location;

    private Integer onlineStatusCode;

    @Excel(name = "备注", width=15)
    private String remarks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getManageIp() {
        return manageIp;
    }

    public void setManageIp(String manageIp) {
        this.manageIp = manageIp == null ? null : manageIp.trim();
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible == null ? null : responsible.trim();
    }

    public String getResponsiblePhone() {
        return responsiblePhone;
    }

    public void setResponsiblePhone(String responsiblePhone) {
        this.responsiblePhone = responsiblePhone == null ? null : responsiblePhone.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Integer getOnlineStatusCode() {
        return onlineStatusCode;
    }

    public void setOnlineStatusCode(Integer onlineStatusCode) {
        this.onlineStatusCode = onlineStatusCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(String organizeId) {
        this.organizeId = organizeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getOrganizeName() {
        return organizeName;
    }

    public void setOrganizeName(String organizeName) {
        this.organizeName = organizeName;
    }
}