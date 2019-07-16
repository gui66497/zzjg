package com.zzjz.zzjg.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * @author 房桂堂
 * @description AssetInit
 * @date 2019/7/11 17:30
 */
@ApiModel("资产相关初始化请求体")
public class AssetInit {

    /**
     * 资产列表
     */
    @ApiModelProperty("资产列表")
    private List<Asset> assetList;

    /**
     * 资产类型列表
     */
    @ApiModelProperty("资产类型列表")
    private List<AssetType> assetTypeList;

    public List<Asset> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<Asset> assetList) {
        this.assetList = assetList;
    }

    public List<AssetType> getAssetTypeList() {
        return assetTypeList;
    }

    public void setAssetTypeList(List<AssetType> assetTypeList) {
        this.assetTypeList = assetTypeList;
    }
}
