package com.zzjz.zzjg.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.github.pagehelper.PageInfo;
import com.github.wujun234.uid.UidGenerator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zzjz.zzjg.bean.Asset;
import com.zzjz.zzjg.bean.AssetRequest;
import com.zzjz.zzjg.bean.AssetType;
import com.zzjz.zzjg.bean.BaseResponse;
import com.zzjz.zzjg.bean.Organization;
import com.zzjz.zzjg.bean.ResultCode;
import com.zzjz.zzjg.service.AssetService;
import com.zzjz.zzjg.service.AssetTypeService;
import com.zzjz.zzjg.service.OrganizeService;
import com.zzjz.zzjg.util.ExcelUtils;
import com.zzjz.zzjg.util.FileUtil;
import com.zzjz.zzjg.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 房桂堂
 * @description AssetController
 * @date 2019/7/4 14:13
 */
@Api(tags = "资产相关接口", description = "提供资产相关的 Rest API")
@RestController
@RequestMapping("/asset")
public class AssetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetController.class);

    @Resource
    private UidGenerator defaultUidGenerator;

    @Autowired
    AssetService assetService;

    @Autowired
    AssetTypeService assetTypeService;

    @Autowired
    OrganizeService organizeService;

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
    @ApiOperation(value = "新增或修改资产", notes = "根据id是否存在来区分新增与修改")
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
            Asset nowAsset = assetService.selectAssetById(asset.getId());
            if (nowAsset == null) {
                return MessageUtil.error("资产修改失败，不存在id为 " + asset.getId() + " 的资产");
            }
            result = assetService.updateAsset(asset);
            message = "资产修改成功";
        }
        return result ? MessageUtil.success(message) : MessageUtil.error("资产保存失败");
    }

    /**
     * 资产信息导出excel.
     * @param response response
     */
    @ApiOperation(value = "资产信息导出excel")
    @GetMapping("/excel/export")
    public void exportAsset(HttpServletResponse response) {
        List<Asset> assetList = assetService.getAllAsset();
        ExcelUtils.exportExcel(assetList, "资产导出", "导出sheet1", Asset.class, "资产导出.xls", response);
    }

    /**
     * 导入excel资产信息.
     * @param file excel文件
     */
    @ApiOperation(value = "导入excel资产信息")
    @PostMapping("/excel/import")
    public BaseResponse importAsset(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return MessageUtil.error("上传文件为空!");
        }
        String fileName = file.getOriginalFilename();
        // 判断文件格式
        String exp = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!"xls".equalsIgnoreCase(exp)) {
            return MessageUtil.error("Excel文件类型错误,请上传xls文件!");
        }

        List<Map<String, String>> errorInfo = Lists.newArrayList();
        List<Asset> finalAssetList = Lists.newArrayList();
        ImportParams importParams = new ImportParams();
        // 数据处理
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);
        // 需要验证
        importParams.setNeedVerify(false);
        try {
            ExcelImportResult<Asset> result = ExcelImportUtil.importExcelMore(file.getInputStream(), Asset.class,
                    importParams);
            List<Asset> assetList = result.getList();
            int i = 0;
            for (Asset asset : assetList) {
                i += 1;
                String ip = asset.getManageIp();
                String typeName = asset.getTypeName();
                String organizeName = asset.getOrganizeName();
                String phone = asset.getResponsiblePhone();

                StringBuffer errorMsg = new StringBuffer();
                if (StringUtils.isBlank(ip) || StringUtils.isBlank(asset.getName()) || StringUtils.isBlank(typeName)) {
                    errorMsg.append("信息不完整(资产名称、IP地址、资产类型为必填项);");
                }
                if (!FileUtil.isValidIp(ip)) {
                    errorMsg.append("IP地址不合法;");
                }
                if (StringUtils.isNotBlank(phone)) {
                    StringBuffer reg = new StringBuffer("(\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-");
                    reg.append("(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$");
                    String res = phone.replaceAll(reg.toString(), "");
                    if (StringUtils.isNotBlank(res)) {
                        errorMsg.append("责任人电话不符合规则;");
                    }
                }

                //资产类型校验
                if (CollectionUtils.isNotEmpty(assetService.findByIpAndTypeName(ip, typeName))) {
                    errorMsg.append("在[" + typeName + "]资产类型下，ip " + ip + "已存在;");
                }
                AssetType queryAssetType = new AssetType();
                queryAssetType.setNameCh(typeName);
                List<AssetType> assetTypeList = assetTypeService.find(queryAssetType);
                if (CollectionUtils.isEmpty(assetTypeList)) {
                    errorMsg.append("资产类型不存在;");
                } else {
                    asset.setType(assetTypeList.get(0).getId());
                }

                //组织机构校验
                if (CollectionUtils.isNotEmpty(assetService.findByIpAndOrganizeName(ip, organizeName))) {
                    errorMsg.append("在[" + organizeName + "组织机构下，ip " + ip + "已存在;");
                }
                Organization queryOrganization = new Organization();
                queryOrganization.setName(organizeName);
                List<Organization> organizationList = organizeService.find(queryOrganization);
                if (CollectionUtils.isEmpty(organizationList)) {
                    errorMsg.append("组织机构不存在;");
                } else {
                    asset.setOrganizeId(organizationList.get(0).getId());
                }

                if (StringUtils.isNotBlank(errorMsg)) {
                    Map<String, String> errorMap = Maps.newHashMap();
                    errorMap.put("line", String.valueOf(i));
                    errorMap.put("msg", errorMsg.toString());
                    errorInfo.add(errorMap);
                } else if (errorInfo.isEmpty()) {
                    asset.setId(String.valueOf(defaultUidGenerator.getUID()));
                    asset.setCreateTime(new Date());
                    asset.setUpdateTime(new Date());
                    finalAssetList.add(asset);
                }
            }
            if (errorInfo.isEmpty()) {
                //批量插入资产数据
                assetService.batchInsert(finalAssetList);
                return MessageUtil.success("资产导入成功");
            } else {
                BaseResponse response = MessageUtil.error("资产导入失败");
                response.setData(errorInfo);
                return response;
            }

        } catch (Exception e) {
            LOGGER.error("导入失败：{}", e.getMessage());
            e.printStackTrace();
            return MessageUtil.error("资产导入失败:" + e.getMessage());
        }
    }

}
