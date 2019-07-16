package com.zzjz.zzjg.mapper;

import com.zzjz.zzjg.bean.Asset;
import com.zzjz.zzjg.bean.AssetRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 根据资产IP和资产类型查询资产.
     * @param ip ip
     * @param typeName 类型名称
     * @return 资产
     */
    List<Asset> findByIpAndTypeName(@Param("ip") String ip, @Param("typeName") String typeName);

    /**
     * 根据资产IP和组织机构查询资产.
     * @param ip ip
     * @param organizeName 组织机构名称
     * @return 资产
     */
    List<Asset> findByIpAndOrganizeName(@Param("ip") String ip, @Param("organizeName") String organizeName);

    /**
     * 批量新增.
     * @param assetList 资产信息
     * @return 结果
     */
    int batchInsert(List<Asset> assetList);

    /**
     * 清空资产表.
     */
    void emptyAsset();
}