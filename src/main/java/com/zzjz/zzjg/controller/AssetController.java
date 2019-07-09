package com.zzjz.zzjg.controller;

import com.github.pagehelper.PageInfo;
import com.github.wujun234.uid.UidGenerator;
import com.zzjz.zzjg.bean.Asset;
import com.zzjz.zzjg.bean.AssetRequest;
import com.zzjz.zzjg.bean.BaseResponse;
import com.zzjz.zzjg.bean.ResultCode;
import com.zzjz.zzjg.service.AssetService;
import com.zzjz.zzjg.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author 房桂堂
 * @description AssetController
 * @date 2019/7/4 14:13
 */
@Api(tags = "资产相关接口", description = "提供资产相关的 Rest API")
@RestController
@RequestMapping("/asset")
public class AssetController {

    @Resource
    private UidGenerator defaultUidGenerator;

    @Autowired
    AssetService assetService;

    /**
     * 根据id获取资产信息.
     * @param id 资产id
     * @return 资产数据
     */
    @ApiOperation("根据id获取资产信息")
    @GetMapping("/{id}")
    public BaseResponse getAsset(@PathVariable String id) {
        Asset asset = assetService.selectAssetById(id);
        if (asset != null && asset.getId() != null) {
            return MessageUtil.success("资产获取成功", asset);
        }
        return MessageUtil.error("没有查询到该资产");
    }

    /**
     * 分页获取资产列表.
     * @param request request
     * @return 结果
     */
    @ApiOperation(value = "分页获取资产列表")
    @PostMapping("/list")
    public PageInfo<Asset> getAssetList(@RequestBody AssetRequest request) {
        List<Asset> assets = assetService.getAssetList(request);
        return new PageInfo<>(assets);
    }

    /**
     * 根据id删除资产
     * @param ids 单个id或多个id以逗号分隔
     * @return 结果
     */
    @ApiOperation(value = "根据id删除资产")
    @ApiImplicitParam(name = "ids", value = "单个id或多个id以逗号分隔")
    @DeleteMapping("/{ids}")
    public BaseResponse deleteAsset(@PathVariable String ids) {
        BaseResponse response = new BaseResponse();
        if (StringUtils.isBlank(ids)) {
            response.setMessage("参数错误");
            response.setResultCode(ResultCode.RESULT_BAD_REQUEST);
            return response;
        }
        String[] idList = StringUtils.split(ids, ",");
        boolean res = assetService.deleteByIdArr(idList);
        if (res) {
            return MessageUtil.success("资产删除成功");
        }
        return MessageUtil.error("资产删除失败");
    }

    /**
     * 新增或修改资产.
     * @param asset 资产信息
     * @return 结果
     */
    @ApiOperation(value = "新增或修改资产", notes = "根据id来区分新增与修改")
    @PostMapping("/addOrUpdate")
    public BaseResponse addAsset(@Valid @RequestBody Asset asset) {
        boolean result;
        String message;
        if (asset.getId() == null) {
            //新增
            asset.setId(String.valueOf(defaultUidGenerator.getUID()));
            asset.setCreateTime(new Date());
            asset.setUpdateTime(new Date());
            asset.setUpdateUser(asset.getCreateUser());
            result = assetService.addAsset(asset);
            message = "资产新增成功";
        } else {
            //修改
            asset.setUpdateTime(new Date());
            result = assetService.updateAsset(asset);
            message = "资产修改成功";
        }
        if (result) {
            return MessageUtil.success(message);
        }
        return MessageUtil.error(message);
    }


}
