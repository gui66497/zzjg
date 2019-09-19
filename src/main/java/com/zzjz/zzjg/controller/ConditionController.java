package com.zzjz.zzjg.controller;

import com.github.pagehelper.PageInfo;
import com.zzjz.zzjg.bean.*;
import com.zzjz.zzjg.service.AssetService;
import com.zzjz.zzjg.service.AssetTypeService;
import com.zzjz.zzjg.service.ConditionService;
import com.zzjz.zzjg.util.PageInfoTransformUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author 陈超
 * @date 2019/08/28
 */
@Api(tags = "状态监控相关接口",description = "提供资产状态监控相关的 Rest API")
@RestController
@RequestMapping("/condition")
public class ConditionController {

    @Autowired
    private ConditionService conditionService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private AssetTypeService assetTypeService;


    /**
     * 分页获取资产及监控信息
     * @param assetRequest
     * @return
     */
    @ApiOperation(value = "分页获取资产状态监控信息")
    @PostMapping("/list")
    public PageInfo<AssetCondition> getAssetConditionList(@RequestBody AssetRequest assetRequest){
        //获取资产信息
        List<Asset> assetList = assetService.getAssetList(assetRequest);
        List<AssetCondition> assetConditionList = new ArrayList<>();
        for (int i=0;i<assetList.size();i++){
            AssetCondition assetCondition = new AssetCondition();
            //资产类型名称
            AssetType assetType = assetTypeService.getAssetTypeById(assetList.get(i).getType());
            assetList.get(i).setTypeName(assetType.getNameCh());
            //根据资产id查询cpu使用率
            CPUConditon cpuConditon=conditionService.getCPUCondition(assetList.get(i).getId(),"cpuutilize");
            if (cpuConditon == null) cpuConditon = new CPUConditon();
            if (cpuConditon.getCpuUtilization()==null){
                assetCondition.setCpuUtilization("");
            }
            assetCondition.setCpuUtilization(""+cpuConditon.getCpuUtilization()+"%");

            //根据资产id查询内存占用率
            MemoryCondition memoryCondition = conditionService.getMemoryCondition(assetList.get(i).getId(),"memoryutilize");
            if (memoryCondition == null) memoryCondition = new MemoryCondition();
            if (memoryCondition.getMemoryUtilization()==null){
                assetCondition.setMemoryUtilization("");
            }
            assetCondition.setMemoryUtilization(""+memoryCondition.getMemoryUtilization()+"%");

            //资产信息
            assetCondition.setAsset(assetList.get(i));
            //结果集
            assetConditionList.add(assetCondition);
        }
        //分页信息保存
        PageInfo pageInfoAsset = new PageInfo<>(assetList);
        PageInfo pageInfoAssetCondition = new PageInfo<>(assetConditionList);
        PageInfoTransformUtil.pageInfoTransform(pageInfoAsset, pageInfoAssetCondition);
        return pageInfoAssetCondition;
    }

    /**
     * 根据资产id查看资产对应的详细监控信息
     * @param assetId
     * @return
     */
    @ApiOperation(value = "根据资产id获取资产状态监控数据详情")
    @GetMapping("/detail/{assetId}")
    public Map<String,Object> getDetailCondition(String assetId){
        Map<String,Object> detailInfoMap = new HashMap<>();
        //资产基本信息
        Asset asset = assetService.selectAssetById(assetId);
        AssetType assetType = assetTypeService.getAssetTypeById(asset.getType());
        asset.setTypeName(assetType.getNameCh());
        detailInfoMap.put("baseAssetInfo",asset);
        //安全基线情况

        detailInfoMap.put("securityBaseInfo",null);

        //cpu信息
        CPUConditon cpuConditon = conditionService.getCPUCondition(assetId,"cpuutilize");
        detailInfoMap.put("cpuInfo",cpuConditon);

        //内存信息


        detailInfoMap.put("memoryInfo",null);
        //硬盘信息


        detailInfoMap.put("diskInfo",null);
        //端口信息


        detailInfoMap.put("portInfo",null);

        return detailInfoMap;
    }

}
