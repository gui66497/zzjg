package com.zzjz.zzjg.service;

import com.zzjz.zzjg.bean.Organization;
import java.util.Set;

/**
 * @author 房桂堂
 * @description OrganizeService
 * @date 2019/7/8 9:15
 */
public interface OrganizeService {

    /**
     * 根据id获取组织机构.
     * @param id id
     * @return 资产类型
     */
    Organization getOrganizeById(String id);

    /**
     * 查询所有资产类型.
     * @param isAll 是否全部显示，1是,0只显示有资产的类型
     * @return 资产类型列表
     */
    Set<Organization> getOrganizeList(String isAll);

}
