package com.zzjz.zzjg.mapper;

import com.zzjz.zzjg.bean.Asset;
import com.zzjz.zzjg.bean.AssetRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 *
 * @date 2019/7/4 14:14
 * @author fgt
 */
@Repository
@Mapper
public interface AssetMapper {

    /**
     * 根据id删除资产.
     * @param id id
     * @return 删除行数
     */
    int deleteByPrimaryKey(String id);

    /**
     * 批量删除资产.
     * @param ids id数组
     * @return 删除行数
     */
    int deleteBatch(String[] ids);

    int insert(Asset record);

    int insertSelective(Asset record);

    Asset selectByPrimaryKey(String id);

    /**
     * 根据id修改资产信息.
     * @param record 资产信息
     * @return 改动行数
     */
    int updateByPrimaryKeySelective(Asset record);

    int updateByPrimaryKeyWithBLOBs(Asset record);

    int updateByPrimaryKey(Asset record);

    /**
     * 分页查询资产信息.
     * @param asset request
     * @return 资产列表
     */
    List<Asset> queryAssetList(AssetRequest asset);

    /**
     * 查询所有资产信息.
     * @return 资产列表
     */
    List<Asset> queryAllAsset();

}