package com.zzjz.zzjg.service.impl;

import com.zzjz.zzjg.bean.AssetRequest;
import com.zzjz.zzjg.bean.AssetType;
import com.zzjz.zzjg.bean.PagingEntity;
import com.zzjz.zzjg.mapper.AssetTypeMapper;
import com.zzjz.zzjg.service.AssetService;
import com.zzjz.zzjg.service.AssetTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 房桂堂
 * @description AssetTypeServiceImpl
 * @date 2019/7/8 9:15
 */
@Service
public class AssetTypeServiceImpl implements AssetTypeService {

    @Autowired
    AssetTypeMapper assetTypeMapper;

    @Autowired
    AssetService assetService;

    @Override
    public AssetType getAssetTypeById(String id) {
        return assetTypeMapper.selectById(id);
    }

    @Override
    public Set<AssetType> getAssetTypeList(String isAll) {
        List<AssetType> assetTypeList = assetTypeMapper.queryAssetTypeList(isAll);
        Set<AssetType> assetTypeSet = new HashSet<>(assetTypeList);
        //是否全部显示，1是,0只显示有资产的类型
        if ("0".equals(isAll)) {
            for (AssetType assetType : assetTypeList) {
                if (!"1".equals(assetType.getPid())) {
                    findParent(assetTypeSet, assetType.getPid());
                }
            }
        }
        return assetTypeSet;
    }

    @Override
    public boolean deleteAssetTypeById(String id) {
        return assetTypeMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deleteValidationAssetType(String typeId) {
        AssetType assetType = new AssetType();
        assetType.setPid(typeId);
        return assetTypeMapper.querySelective(assetType).isEmpty();
    }

    @Override
    public boolean deleteValidationRes(String typeId) {
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setType(typeId);
        assetRequest.setPaging(new PagingEntity());
        return assetService.getAssetList(assetRequest).isEmpty();
    }

    @Override
    public AssetType findLastByPId(String pId) {
        return assetTypeMapper.queryLastByPId(pId);
    }

    @Transactional
    @Override
    public void saveResType(AssetType assetType, String newId) {
        if (StringUtils.isNotBlank(newId)) {
            assetTypeMapper.updateId(assetType.getId(), newId);
            assetType.setId(newId);
        }
        //saveorupdate
        if (getAssetTypeById(assetType.getId()) == null) {
            //新增
            insertAssetType(assetType);
        } else {
            //修改
            updateAssetTypeById(assetType);
        }
    }

    @Override
    public boolean insertAssetType(AssetType assetType) {
        return assetTypeMapper.insertSelective(assetType) > 0;
    }

    @Override
    public boolean updateAssetTypeById(AssetType assetType) {
        return assetTypeMapper.updateByPrimaryKeySelective(assetType) > 0;
    }

    @Override
    public List<AssetType> find(AssetType assetType) {
        return assetTypeMapper.find(assetType);
    }

    @Override
    public boolean batchInsert(List<AssetType> assetTypeList) {
        return assetTypeMapper.batchInsert(assetTypeList) > 0;
    }

    /**
     * 根据资产类型找到父级资产类型.
     * @param assetTypeSet 资产类型列表
     * @param typeId 资产类型id
     */
    private void findParent(Set<AssetType> assetTypeSet, String typeId) {
        AssetType assetType = getAssetTypeById(typeId);
        assetTypeSet.add(assetType);
        if (!"1".equals(assetType.getPid())) {
            findParent(assetTypeSet, assetType.getPid());
        }
    }


}
