package com.zzjz.zzjg.controller;

import com.zzjz.zzjg.bean.BaseResponse;
import com.zzjz.zzjg.bean.Organization;
import com.zzjz.zzjg.bean.ResultCode;
import com.zzjz.zzjg.service.OrganizeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Set;

/**
 * @author 房桂堂
 * @description OrganizeController
 * @date 2019/7/10 08:44
 */
@Api(tags = "组织机构相关接口", description = "提供组织机构相关的 Rest API")
@RestController
@RequestMapping("/organize")
public class OrganizeController {

    @Autowired
    OrganizeService organizeService;

    /**
     * 根据id获取指定组织机构.
     * @param id 组织机构id
     * @return 结果
     */
    @ApiOperation("根据id获取指定组织机构")
    @GetMapping("/{id}")
    public BaseResponse<Organization> getOrganizeById(@PathVariable("id") String id) throws Exception {
        Organization organization = organizeService.getOrganizeById(id);
        BaseResponse<Organization> response = new BaseResponse<>();
        if (organization == null) {
            throw new Exception("没有查询到指定组织机构");
        }
        response.setMessage("查询组织机构成功");
        response.setObj(organization);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        return response;
    }

    /**
     * 获取组织机构列表.
     * @param isAll 是否全部显示，1是,0只显示有资产的类型
     * @return 组织机构列表
     */
    @ApiOperation("获取组织机构列表")
    @ApiImplicitParam(name = "isAll", value = "是否全部显示，1是,0只显示有资产的类型")
    @GetMapping("/list/{isAll}")
    public BaseResponse<Organization> getOrganizeList(@PathVariable String isAll) {
        Set<Organization> assetTypeSet = organizeService.getOrganizeList(isAll);
        BaseResponse<Organization> response = new BaseResponse<>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(new ArrayList<>(assetTypeSet));
        response.setMessage("查询组织机构列表成功");
        return response;
    }

}
