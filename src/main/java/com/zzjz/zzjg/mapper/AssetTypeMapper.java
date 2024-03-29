package com.zzjz.zzjg.mapper;

import com.zzjz.zzjg.bean.AssetType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @ClassName: AssetTypeMapper
 * @Description:
 * @author 房桂堂
 * @date 2019/7/8 8:38
 */
@Repository
@Mapper
public interface AssetTypeMapper {

    /**
     * 根据id删除资产类型.
     * @param id id
     * @return 删除条数
     */
    int deleteById(String id);

    /**
     * 批量删除资产类型.
     * @param ids id数组
     * @return 删除行数
     */
    int deleteBatch(String[] ids);

    int insert(AssetType record);

    int insertSelective(AssetType record);

    /**
     * 根据id获取资产类型.
     * @param id id
     * @return 资产类型
     */
    AssetType selectById(String id);

    int updateByPrimaryKeySelective(AssetType record);

    int updateByPrimaryKey(AssetType record);

    /**
     * 根据条件获取资产类型数据.
     * @param assetType 资产类型实体
     * @return 资产类型数据
     */
    List<AssetType> querySelective(AssetType assetType);

    /**
     * 查询所有资产类型.
     * @param isAll 是否全部显示，1是,0只显示有资产的类型
     * @return 资产类型
     */
    List<AssetType> queryAssetTypeList(@Param("isAll") String isAll);

    /**
     * 根据父级ID获取最后一个子级资产类型.
     * @param pid 父级id
     * @return 资产类型
     */
    AssetType queryLastByPId(String pid);

    /**
     * 修改id.
     * @param id 原id
     * @param newId 新id
     * @return 改动行数
     */
    int updateId(@Param("id") String id, @Param("newId") String newId);

    /**
     * 多条件查询资产类型.
     * @param assetType 资产类型实体
     * @return 结果
     */
    List<AssetType> find(AssetType assetType);

    /**
     * 批量新增.
     * @param assetTypeList 资产类型列表
     * @return 结果
     */
    int batchInsert(List<AssetType> assetTypeList);

    /**
     * 清空资产类型表.
     */
    void emptyAssetType();

    /**
     * 根据中文名获取资产类型.
     * @param chName 中文名
     * @return 资产类型
     */
    AssetType getAssetTypeByChName(String chName);

    /**
     * 根据英文名获取资产类型.
     * @param enName 英文名
     * @return 资产类型
     */
    AssetType getAssetTypeByEnName(String enName);
}