package com.zzjz.zzjg.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @Description 指标类型实体类
 * @Author 房桂堂
 * @Date 2019/8/27 9:55
 */
@ApiModel("指标类型实体")
public class KpiType {

    private String id;

    /**
     * 指标类型中文名称.
     */
    @ApiModelProperty("指标类型中文名称")
    private String nameCh;

    /**
     * 指标类型英文名称.
     */
    @ApiModelProperty("指标类型英文名称")
    private String nameEn;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameCh() {
        return nameCh;
    }

    public void setNameCh(String nameCh) {
        this.nameCh = nameCh;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
