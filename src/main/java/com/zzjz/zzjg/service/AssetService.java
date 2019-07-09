package com.zzjz.zzjg.service;

import com.zzjz.zzjg.bean.Asset;
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
     * 根据名字查找资产.
     */
    Asset selectAssetById(String id);

    /**
     * 分页获取资产列表.
     * @param request request
     * @return 资产列表
     */
    List<Asset> getAssetList(AssetRequest request);

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
}
