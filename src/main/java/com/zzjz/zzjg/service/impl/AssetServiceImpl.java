package com.zzjz.zzjg.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzjz.zzjg.bean.Asset;
import com.zzjz.zzjg.bean.AssetRequest;
import com.zzjz.zzjg.bean.PagingEntity;
import com.zzjz.zzjg.mapper.AssetMapper;
import com.zzjz.zzjg.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author 房桂堂
 * @description AssetServiceImpl
 * @date 2019/7/5 10:01
 */
@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    AssetMapper assetMapper;

    @Override
    public Asset selectAssetById(String id) {
        return assetMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Asset> getAssetList(AssetRequest request) {
        PagingEntity paging = request.getPaging();
        PageHelper.startPage(paging.getPageNo(), paging.getPageSize());
        return assetMapper.queryAssetList(request);
    }

    @Override
    public boolean deleteByIdArr(String[] idArr) {
        return assetMapper.deleteBatch(idArr) > 0;
    }

    @Override
    public boolean addAsset(Asset asset) {
        return assetMapper.insertSelective(asset) > 0;
    }

    @Override
    public boolean updateAsset(Asset asset) {
        return assetMapper.updateByPrimaryKeySelective(asset) > 0;
    }


}
