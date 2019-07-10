package com.zzjz.zzjg.service;

import com.zzjz.zzjg.bean.AssetType;
import java.util.List;
import java.util.Set;

/**
 * @author 房桂堂
 * @description AssetTypeService
 * @date 2019/7/8 9:15
 */
public interface AssetTypeService {

    /**
     * 根据id获取资产类型.
     * @param id id
     * @return 资产类型
     */
    AssetType getAssetTypeById(String id);

    /**
     * 查询所有资产类型.
     * @param isAll 是否全部显示，1是,0只显示有资产的类型
     * @return 资产类型列表
     */
    Set<AssetType> getAssetTypeList(String isAll);

    /**
     * 根据id删除资产类型
     * @param id id
     * @return 结果
     */
    boolean deleteAssetTypeById(String id);

    /**
     * 根据资产类型查看该类型下是否有资产分类.
     * @param typeId the type id
     * @return true 可以删除
     */
    boolean deleteValidationAssetType(String typeId);

    /**
     * 根据资产类型查看该类型下是否有资产.
     * @param typeId the type id
     * @return boolean
     */
    boolean deleteValidationRes(String typeId);

    /**
     * 根据父级ID获取最后一个子级资产类型.
     * @param pId 父级id
     * @return map
     */
    AssetType findLastByPId(String pId);

    /**
     * 新增或更新资产类型.
     * @param assetType 资产类型
     * @param newId   the new id
     */
    void saveResType(AssetType assetType, String newId);

    /**
     * 插入资产类型.
     * @param assetType 资产类型实体
     * @return 结果
     */
    boolean insertAssetType(AssetType assetType);

    boolean updateAssetTypeById(AssetType assetType);

    /**
     * 多条件查询资产类型.
     * @param assetType 资产类型实体
     * @return 结果
     */
    List<AssetType> find(AssetType assetType);

}
