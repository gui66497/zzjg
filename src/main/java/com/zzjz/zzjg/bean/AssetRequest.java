package com.zzjz.zzjg.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 房桂堂
 * @description AssetRequest
 * @date 2019/7/5 10:48
 */
@ApiModel("资产请求体")
public class AssetRequest extends BaseRequest {

    /**
     * 资产类型id
     */
    @ApiModelProperty("资产类型id")
    private String type;

    /**
     * 组织机构id
     */
    @ApiModelProperty("组织机构id")
    private String organizeId;

    /**
     * 查询参数 资产IP或资产名
     */
    @ApiModelProperty("查询参数 资产IP或资产名")
    private String searchParam;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

    public String getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(String organizeId) {
        this.organizeId = organizeId;
    }
}
