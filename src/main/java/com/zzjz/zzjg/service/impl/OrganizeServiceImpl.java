package com.zzjz.zzjg.service.impl;

import com.zzjz.zzjg.bean.Organization;
import com.zzjz.zzjg.mapper.OrganizationMapper;
import com.zzjz.zzjg.service.OrganizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 房桂堂
 * @description OrganizeServiceImpl
 * @date 2019/7/10 8:59
 */
@Service
public class OrganizeServiceImpl implements OrganizeService {

    @Autowired
    OrganizationMapper organizationMapper;

    @Override
    public Organization getOrganizeById(String id) {
        return organizationMapper.selectById(id);
    }

    @Override
    public Set<Organization> getOrganizeList(String isAll) {
        List<Organization> organizationList = organizationMapper.queryOrganizeList(isAll);
        Set<Organization> organizationSet = new HashSet<>(organizationList);
        //是否全部显示，1是,0只显示有资产的类型
        if ("0".equals(isAll)) {
            for (Organization organization : organizationList) {
                if (!"1".equals(organization.getPid())) {
                    findParent(organizationSet, organization.getPid());
                }
            }
        }
        return organizationSet;
    }

    @Override
    public List<Organization> find(Organization organization) {
        return organizationMapper.find(organization);
    }

    /**
     * 根据资产类型找到父级资产类型.
     * @param organizeSet 资产类型列表
     * @param typeId 资产类型id
     */
    private void findParent(Set<Organization> organizeSet, String typeId) {
        Organization organization = getOrganizeById(typeId);
        organizeSet.add(organization);
        if (!"1".equals(organization.getPid())) {
            findParent(organizeSet, organization.getPid());
        }
    }
}
