package com.zzjz.zzjg.mapper;

import com.zzjz.zzjg.bean.CPUConditon;
import com.zzjz.zzjg.bean.MemoryCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ConditionMapper {

    /**
     * 根据资产id查询对应的cpu利用率
     * @param id
     * @return
     */
    CPUConditon getCPUCondition(@Param("id") String id,@Param("cpuutilize") String cpuutilize);

    /**
     * 根据资产id查询内存占用率
     * @param id
     * @return
     */
    MemoryCondition getMemoryCondition(@Param("id") String id,@Param("memoryutilize") String memoryutilize);
}
