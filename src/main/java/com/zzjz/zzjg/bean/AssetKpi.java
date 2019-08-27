package com.zzjz.zzjg.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description 指标类型与资产或端口关联关系.
 * @Author 房桂堂
 * @Date 2019/8/27 10:14
 */
@ApiModel("指标类型与资产或端口关联关系")
public class AssetKpi {

    private String id;

    /**
     * 资产ID.
     */
    @ApiModelProperty("资产ID")
    private String asset;

    /**
     * 端口ID.
     */
    @ApiModelProperty("端口ID")
    private String part;

    /**
     * 指标类型ID.
     */
    @ApiModelProperty("指标类型ID")
    private String kpiType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getKpiType() {
        return kpiType;
    }

    public void setKpiType(String kpiType) {
        this.kpiType = kpiType;
    }
}
