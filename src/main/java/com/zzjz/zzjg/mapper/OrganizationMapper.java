package com.zzjz.zzjg.mapper;

import com.zzjz.zzjg.bean.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @ClassName: OrganizationMapper
 * @Description: 组织机构mapper接口
 * @author 房桂堂
 * @date 2019/7/10 9:15
 */
@Repository
@Mapper
public interface OrganizationMapper {
    int deleteByPrimaryKey(String id);

    int insert(Organization record);

    int insertSelective(Organization record);

    Organization selectById(String id);

    int updateByPrimaryKeySelective(Organization record);

    int updateByPrimaryKey(Organization record);

    /**
     * 查询所有组织机构.
     * @param isAll 是否全部显示，1是,0只显示有资产的类型
     * @return 组织机构
     */
    List<Organization> queryOrganizeList(@Param("isAll") String isAll);

    /**
     * 多条件查询组织机构.
     * @param organization 组织机构实体
     * @return 结果
     */
    List<Organization> find(Organization organization);
}