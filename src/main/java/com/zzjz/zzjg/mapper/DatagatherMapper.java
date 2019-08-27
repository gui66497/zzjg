package com.zzjz.zzjg.mapper;

import com.zzjz.zzjg.bean.Datagather;

/**
 * @Description: 采集数据查询mapper
 * @author 房桂堂
 * @date 2019/8/27 11:29
 */
public interface DatagatherMapper {

    int deleteByPrimaryKey(String id);

    int insert(Datagather record);

    int insertSelective(Datagather record);

    Datagather selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Datagather record);

    int updateByPrimaryKey(Datagather record);
}