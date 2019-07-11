package com.zzjz.zzjg.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @ClassName: AssetType
 * @Description:
 * @author 房桂堂
 * @date 2019/7/5 10:21
 */
@ApiModel("资产类型实体")
public class AssetType {

    @Excel(name = "ID", width=20)
    @NotBlank(message = "id不能为空")
    private String id;

    private String createUser;

    @Excel(name = "创建时间", orderNum = "3", exportFormat = "yyyy-MM-dd HH:mm:ss", width=20)
    private Date createTime;

    private String updateUser;

    private Date updateTime;

    @Excel(name = "名称", orderNum = "1", width=15)
    @NotBlank(message = "名称不能为空")
    private String nameCh;

    @Excel(name = "英文名称", orderNum = "2", width=15)
    private String nameEn;

    private String pid;

    @Excel(name = "路径", orderNum = "5", width=28)
    private String pic;

    @Excel(name = "父节点", orderNum = "4", width=15)
    private String pName;

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + id.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object var1) {
        if (var1 instanceof AssetType) {
            AssetType assetType = (AssetType) var1;
            return id.equals(assetType.getId());
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getNameCh() {
        return nameCh;
    }

    public void setNameCh(String nameCh) {
        this.nameCh = nameCh == null ? null : nameCh.trim();
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}