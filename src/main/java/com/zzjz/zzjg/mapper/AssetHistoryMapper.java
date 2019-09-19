package com.zzjz.zzjg.mapper;

import com.google.common.collect.Lists;
import com.zzjz.zzjg.bean.AssetHistory;
import com.zzjz.zzjg.bean.AssetRequest;
import com.zzjz.zzjg.bean.HistoryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AssetHistoryMapper {
    /**
     * 分页查询资产履历信息列表
     * @param assetRequest
     * @return
     */
    List<AssetHistory> getAssetHistory(AssetRequest assetRequest);

    /**
     * 根据记录id删除或批量删除记录
     * @param id
     * @return
     */
    int deleteAssetHistoryByIds(String[] id);

    /**
     * 新增资产履历信息
     * @param historyRequest
     * @return
     */
    int addAssetHistory(HistoryRequest historyRequest);

    /**
     * 跟新资产履历信息
     * @param historyRequest
     * @return
     */
    int updateAssetHistory(HistoryRequest historyRequest);

    /**
     * 根据资产履历记录id查询记录信息
     * @param id
     * @return
     */
    HistoryRequest selectHistoryById(String id);

    /**
     * 查询出所有的资产履历信息
     * @return
     */
    List<HistoryRequest> getAllAssetHistory();

    /**
     * 根据资产id和记录ip查询履历记录
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
    int batchInsert(List<HistoryRequest> historyRequestList);
}
