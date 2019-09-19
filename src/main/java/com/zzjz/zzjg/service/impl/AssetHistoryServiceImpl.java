package com.zzjz.zzjg.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzjz.zzjg.bean.AssetHistory;
import com.zzjz.zzjg.bean.AssetRequest;
import com.zzjz.zzjg.bean.HistoryRequest;
import com.zzjz.zzjg.bean.PagingEntity;
import com.zzjz.zzjg.mapper.AssetHistoryMapper;
import com.zzjz.zzjg.service.AssetHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetHistoryServiceImpl implements AssetHistoryService {

    @Autowired
    private AssetHistoryMapper assetHistoryMapper;

    @Override
    public List<AssetHistory> getAssetHistory(AssetRequest assetRequest) {
        PagingEntity pagingEntity = assetRequest.getPaging();
        PageHelper.startPage(pagingEntity.getPageNo(),pagingEntity.getPageSize());
        return assetHistoryMapper.getAssetHistory(assetRequest);
    }

    @Override
    public boolean deleteAssetHistory(String[] id) {
        return assetHistoryMapper.deleteAssetHistoryByIds(id)>0;
    }

    @Override
    public boolean addAssetHistory(HistoryRequest historyRequest) {
        return assetHistoryMapper.addAssetHistory(historyRequest)>0;
    }

    @Override
    public boolean updateAssetHistory(HistoryRequest historyRequest) {
        return assetHistoryMapper.updateAssetHistory(historyRequest)>0;
    }

    @Override
    public HistoryRequest selectHistoryById(String id) {
        return assetHistoryMapper.selectHistoryById(id);
    }

    @Override
    public List<HistoryRequest> getAllAssetHistory() {
        return assetHistoryMapper.getAllAssetHistory();
    }

    @Override
    public HistoryRequest selectHistoryByAssetNameAndIp(String assetName, String ip) {
        return assetHistoryMapper.selectHistoryByAssetNameAndIp(assetName,ip);
    }

    @Override
    public boolean batchInsert(List<HistoryRequest> historyRequestList) {
        return assetHistoryMapper.batchInsert(historyRequestList)>0;
    }

}
