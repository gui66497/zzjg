package com.zzjz.zzjg.service;

import com.zzjz.zzjg.bean.Asset;
import com.zzjz.zzjg.bean.AssetInit;
import com.zzjz.zzjg.bean.AssetRequest;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author 房桂堂
 * @description AssetService
 * @date 2019/7/4 14:23
 */
@Service
public interface AssetService {

    /**
     * 根据id查找资产.
     */
    Asset selectAssetById(String id);

    /**
     * 多条件查询资产.
     * @param asset 资产
     * @return 结果
     */
    List<Asset> find(Asset asset);

    /**
     * 分页获取资产列表.
     * @param request request
     * @return 资产列表
     */
    List<Asset> getAssetList(AssetRequest request);

    /**
     * 获取所有资产信息.
     * @return 资产列表
     */
    List<Asset> getAllAsset();

    /**
     * 根据id删除资产.
     * @param idArr id数组
     * @return flag
     */
    boolean deleteByIdArr(String[] idArr);

    /**
     * 新增资产.
     * @param asset 资产实体
     * @return 结果
     */
    boolean addAsset(Asset asset);

    /**
     * 修改资产.
     * @param asset 资产实体
     * @return 结果
     */
    boolean updateAsset(Asset asset);

    /**
     * 根据资产IP和资产类型查询资产.
     * @param ip ip
     * @param typeName 类型名称
     * @return 资产
     */
    List<Asset> findByIpAndTypeName(String ip, String typeName);

    /**
     * 根据资产IP和组织机构查询资产.
     * @param ip ip
     * @param organizeName 组织机构名称
     * @return 资产
     */
    List<Asset> findByIpAndOrganizeName(String ip, String organizeName);

    /**
     * 批量新增.
     * @param assetList 资产信息
     * @return 结果
     */
    boolean batchInsert(List<Asset> assetList);

    /**
     * 初始化资产类型和资产.
     * @param assetInit assetInit
     */
    void init(AssetInit assetInit) throws Exception;
}
