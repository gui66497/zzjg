package com.zzjz.zzjg.service;

import com.zzjz.zzjg.bean.CPUConditon;
import com.zzjz.zzjg.bean.MemoryCondition;

public interface ConditionService {

    /**
     * 根据资产id获取对应的cpu监控数据
     * @param id
     * @return
     */
    CPUConditon getCPUCondition(String id,String cpuutilize);

    /**
     * 根据资产id获取内存占用率
     * @param id
     * @return
     */
    MemoryCondition getMemoryCondition(String id,String memoryutilize);
}
