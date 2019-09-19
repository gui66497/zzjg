package com.zzjz.zzjg.service.impl;

import com.zzjz.zzjg.bean.CPUConditon;
import com.zzjz.zzjg.bean.MemoryCondition;
import com.zzjz.zzjg.mapper.ConditionMapper;
import com.zzjz.zzjg.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConditionServiceImpl implements ConditionService {

    @Autowired
    private ConditionMapper conditionMapper;

    @Override
    public CPUConditon getCPUCondition(String id,String cpuutilize) {
        return conditionMapper.getCPUCondition(id,cpuutilize);
    }

    @Override
    public MemoryCondition getMemoryCondition(String id,String memoryutilize) {
        return conditionMapper.getMemoryCondition(id,memoryutilize);
    }
}
