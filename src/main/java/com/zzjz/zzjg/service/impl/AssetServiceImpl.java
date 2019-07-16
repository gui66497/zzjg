package com.zzjz.zzjg.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.wujun234.uid.UidGenerator;
import com.zzjz.zzjg.bean.Asset;
import com.zzjz.zzjg.bean.AssetInit;
import com.zzjz.zzjg.bean.AssetRequest;
import com.zzjz.zzjg.bean.AssetType;
import com.zzjz.zzjg.bean.Organization;
import com.zzjz.zzjg.bean.PagingEntity;
import com.zzjz.zzjg.mapper.AssetMapper;
import com.zzjz.zzjg.mapper.AssetTypeMapper;
import com.zzjz.zzjg.mapper.OrganizationMapper;
import com.zzjz.zzjg.service.AssetService;
import com.zzjz.zzjg.service.AssetTypeService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
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

    @Autowired
    AssetTypeMapper assetTypeMapper;

    @Autowired
    AssetTypeService assetTypeService;

    @Autowired
    OrganizationMapper organizationMapper;

    @Resource
    private UidGenerator defaultUidGenerator;

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
    public List<Asset> getAllAsset() {
        return assetMapper.queryAllAsset();
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

    @Override
    public List<Asset> findByIpAndTypeName(String ip, String typeName) {
        return assetMapper.findByIpAndTypeName(ip, typeName);
    }

    @Override
    public List<Asset> findByIpAndOrganizeName(String ip, String organizeName) {
        return assetMapper.findByIpAndOrganizeName(ip, organizeName);
    }

    @Override
    public boolean batchInsert(List<Asset> assetList) {
        return assetMapper.batchInsert(assetList) > 0;
    }

    //所有异常都回滚
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void init(AssetInit assetInit) throws Exception {
        List<AssetType> assetTypeList = assetInit.getAssetTypeList();
        // 先清空资产类型表
        assetTypeMapper.emptyAssetType();
        for (AssetType assetType : assetTypeList) {
            String id = assetType.getId();
            String pid = assetType.getPid();
            if (StringUtils.isBlank(id) || StringUtils.isBlank(pid) ) {
                throw new Exception("初始化时,资产类型需要指定ID和父级ID");
            }
            assetType.setCreateTime(new DateTime().withZone(DateTimeZone.forID("Asia/Shanghai")).toDate());
            assetType.setUpdateTime(new Date());
        }
        assetTypeMapper.batchInsert(assetTypeList);

        List<Asset> assetList = assetInit.getAssetList();
        for (Asset asset : assetList) {
            String typeName = asset.getTypeName();
            AssetType assetType = new AssetType(typeName, null, null);
            List<AssetType> findAssetTypes = assetTypeMapper.find(assetType);
            if (findAssetTypes.isEmpty()) {
                throw new Exception("资产类型[" + typeName + "]不存在");
            }
            asset.setType(findAssetTypes.get(0).getId());

            String organizeName = asset.getOrganizeName();
            if (StringUtils.isNotBlank(organizeName)) {
                Organization organization = new Organization(null, organizeName, null);
                List<Organization> findOrganizeList = organizationMapper.find(organization);
                if (findOrganizeList.isEmpty()) {
                    throw new Exception("组织机构[" + organizeName + "]不存在");
                }
                asset.setOrganizeId(findOrganizeList.get(0).getId());
            }

            asset.setId(String.valueOf(defaultUidGenerator.getUID()));
            asset.setCreateTime(new Date());
            asset.setUpdateTime(new Date());
        }
        // 先清空资产表
        assetMapper.emptyAsset();
        assetMapper.batchInsert(assetList);
    }

}
