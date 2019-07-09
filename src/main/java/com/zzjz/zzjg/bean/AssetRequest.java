package com.zzjz.zzjg.bean;

import io.swagger.annotations.ApiModel;

/**
 * @author 房桂堂
 * @description AssetRequest
 * @date 2019/7/5 10:48
 */
@ApiModel("资产请求体")
public class AssetRequest extends BaseRequest {

    /**
     * 资产类型
     */
    private String type;

    /**
     * 查询参数 资产IP或资产名
     */
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
}
