package com.zzjz.zzjg.service;

import com.zzjz.zzjg.bean.AssetHistory;
import com.zzjz.zzjg.bean.AssetRequest;
import com.zzjz.zzjg.bean.HistoryRequest;

import java.util.List;

public interface AssetHistoryService {
    /**
     * 分页查询资产履历信息
     * @param assetRequest
     * @return
     */
    List<AssetHistory> getAssetHistory(AssetRequest assetRequest);

    /**
     * 根据记录id删除或批量删除记录
     * @param id
     * @return
     */
    boolean deleteAssetHistory(String[] id);

    /**
     * 新增资产履历信息
     * @param historyRequest
     * @return
     */
    boolean addAssetHistory(HistoryRequest historyRequest);

    /**
     * 更新资产履历信息
     * @param historyRequest
     * @return
     */
    boolean updateAssetHistory(HistoryRequest historyRequest);

    /**
     * 根据资产履历id查询记录信息
     * @param id
     * @return
     */
    HistoryRequest selectHistoryById(String id);

    /**
     * 查询所有的资产履历信息
     * @return
     */
    List<HistoryRequest> getAllAssetHistory();

    /**
     * 根据资产id和记录中的ip字段查询记录
     * @param assetName
     * @param ip
     * @return
     */
    HistoryRequest selectHistoryByAssetNameAndIp(String assetName, String ip);

    /**
     * 批量插入资产履历记录
     * @param historyRequestList
     * @return
     */
    boolean batchInsert(List<HistoryRequest> historyRequestList);
}
