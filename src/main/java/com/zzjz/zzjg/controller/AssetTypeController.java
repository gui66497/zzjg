package com.zzjz.zzjg.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.zzjz.zzjg.bean.AssetType;
import com.zzjz.zzjg.bean.BaseResponse;
import com.zzjz.zzjg.bean.ResultCode;
import com.zzjz.zzjg.service.AssetTypeService;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author 房桂堂
 * @description AssetTypeController
 * @date 2019/7/5 17:15
 */
@Api(tags = "资产类型相关接口", description = "提供资产类型相关的 Rest API")
@RestController
@RequestMapping("/assetType")
public class AssetTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetTypeController.class);

    @Autowired
    AssetTypeService assetTypeService;

    @Value("${server.port}")
    String serverPort;

    @Value("${ip}")
    String ip;

    /**
     * 资产类型图片相对存放路径
     */
    private static final String ASSET_TYPE_PATH = "static/image/assetType/";

    /**
     * 根据id获取指定资产类型.
     * @param id 资产类型id
     * @return 结果
     */
    @ApiOperation("根据id获取指定资产类型")
    @GetMapping("/{id}")
    public BaseResponse<AssetType> getAssetTypeById(@PathVariable("id") String id) {
        AssetType assetType = assetTypeService.getAssetTypeById(id);
        String pic = assetType.getPic();
        if (StringUtils.isNotBlank(pic)) {
            assetType.setPic("http://" + ip + ":" + serverPort + "/zzjg/" + pic);
        }
        BaseResponse<AssetType> response = new BaseResponse<>();
        if (assetType.getId() == null) {
            response.setMessage("没有查询到指定资产类型");
            response.setResultCode(ResultCode.RESULT_ERROR);
            return response;
        }
        response.setMessage("查询资产类型成功");
        response.setObj(assetType);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        return response;
    }

    /**
     * 获取资产类型列表.
     * @param isAll 是否全部显示，1是,0只显示有资产的类型
     * @return 资产类型列表
     */
    @ApiOperation("获取资产类型列表")
    @ApiImplicitParam(name = "isAll", value = "是否全部显示，1是,0只显示有资产的类型")
    @GetMapping("/list/{isAll}")
    public BaseResponse<AssetType> getAssetTypeList(@PathVariable String isAll) {
        Set<AssetType> assetTypeSet = assetTypeService.getAssetTypeList(isAll);
        // 图片路径替换
        for (AssetType assetType : assetTypeSet) {
            String pic = assetType.getPic();
            if (StringUtils.isNotBlank(pic)) {
                assetType.setPic("http://" + ip + ":" + serverPort + "/zzjg/" + pic);
            }
        }
        BaseResponse<AssetType> response = new BaseResponse<>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(new ArrayList<>(assetTypeSet));
        response.setMessage("查询资产类型成功");
        return response;
    }

    /**
     * 根据id删除资产类型.
     * @param ids id
     * @return 结果
     */
    @ApiOperation("根据id删除资产类型")
    @ApiImplicitParam(name = "ids", value = "单个id或多个id以逗号分隔")
    @DeleteMapping("/{ids}")
    public BaseResponse deleteAssetTypeById(@PathVariable String ids) {
        if (StringUtils.isBlank(ids)) {
            return MessageUtil.error("参数不合法");
        }
        String[] idArr = ids.split(",");
        List<String> errorList = Lists.newArrayList();
        for (String id : idArr) {
            StringBuilder stringBuilder = new StringBuilder();
            AssetType assetType = assetTypeService.getAssetTypeById(id);
            if (assetType == null) {
                stringBuilder.append("无法删除,资产[").append(id).append("]不存在");
            }
            //删除前验证
            if (!assetTypeService.deleteValidationAssetType(id)) {
                stringBuilder.append("无法删除,资产[").append(id).append("]下有关联资产类型");
            }
            if (!assetTypeService.deleteValidationRes(id)) {
                stringBuilder.append("无法删除,资产[").append(id).append("]下有关联资产");
            }
            if (StringUtils.isNotBlank(stringBuilder.toString())) {
                errorList.add(stringBuilder.toString());
            }
        }
        if (errorList.isEmpty()) {
            boolean res = assetTypeService.deleteByIdArr(idArr);
            if (res) {
                return MessageUtil.success("删除资产类型[" + ids + "]成功");
            } else {
                return MessageUtil.error("删除资产类型[" + ids + "]失败");
            }
        } else {
            BaseResponse response = MessageUtil.error(String.join("|", errorList));
            response.setData(errorList);
            return response;
        }

    }

    /**
     * 新增或修改资产类型信息.
     * @param assetType 资产类型实体
     * @param file 图片文件
     * @param request HttpServletRequest
     * @return 结果
     */
    @ApiOperation("新增或修改资产类型信息")
    @PostMapping("save")
    public BaseResponse saveAssetType(AssetType assetType,
                                      @RequestParam(required = false) MultipartFile file,
                                      HttpServletRequest request) {
        String delPic = "";
        String newId = null;
        if (StringUtils.isBlank(assetType.getNameCh()) || StringUtils.isBlank(assetType.getNameEn()) ||
                StringUtils.isBlank(assetType.getPid())) {
            return MessageUtil.error("中文名、英文名、父级ID为必填项");
        }
        try {
            if (StringUtils.isBlank(assetType.getId())) {
                //新增

                if (assetTypeService.getAssetTypeByEnName(assetType.getNameEn()) != null) {
                    return MessageUtil.error("已存在英文名为[" + assetType.getNameEn() + "]的资产类型");
                }
                if (assetTypeService.getAssetTypeByChName(assetType.getNameCh()) != null) {
                    return MessageUtil.error("已存在中文名为[" + assetType.getNameCh() + "]的资产类型");
                }
                assetType.setId(assetTypeService.createId(assetType.getPid()));

            } else {
                //修改
                AssetType oldAssetType = assetTypeService.getAssetTypeById(assetType.getId());
                if (oldAssetType != null) {
                    if (!oldAssetType.getNameEn().equals(assetType.getNameEn())) {
                        if (assetTypeService.getAssetTypeByEnName(assetType.getNameEn()) != null) {
                            return MessageUtil.error("已存在英文名为[" + assetType.getNameEn() + "]的资产类型");
                        }
                    }
                    if (!oldAssetType.getNameCh().equals(assetType.getNameCh())) {
                        if (assetTypeService.getAssetTypeByChName(assetType.getNameCh()) != null) {
                            return MessageUtil.error("已存在中文名为[" + assetType.getNameCh() + "]的资产类型");
                        }
                    }
                    delPic = oldAssetType.getPic();
                    assetType.setPic(delPic);
                    //判断是否修改了上级资产类型
                    if (!assetType.getPid().equals(oldAssetType.getPid())) {
                        newId = assetTypeService.createId(assetType.getPid());
                    }
                } else {
                    return MessageUtil.error("ID为[" + assetType.getId() + "]的资产类型不存在");
                }
            }

            //处理图片上传
            if (file != null && !file.isEmpty()) {
                BufferedImage image = ImageIO.read(file.getInputStream());
                if (image.getWidth() > 80 || image.getHeight() > 80) {
                    return MessageUtil.error("上传图片像素应小于80*80px");
                }
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

                String filePath = FileUtil.getJarPath(ASSET_TYPE_PATH);
                File fileDir = new File(filePath);
                if (!fileDir.exists() && !fileDir.isDirectory()) {
                    if (!fileDir.mkdirs()) {
                        return MessageUtil.error("图片路径创建失败!");
                    }
                }

                String fileName = UUID.randomUUID() + suffix;
                Files.write(file.getBytes(), new File(filePath, fileName));
                //tomcat配置虚拟路径image
                assetType.setPic(ASSET_TYPE_PATH + fileName);
            }

            assetType.setCreateTime(new Date());
            assetType.setUpdateTime(new Date());
            assetType.setUpdateUser(assetType.getCreateUser());
            assetTypeService.saveResType(assetType, newId);

            if (file != null && !file.isEmpty() && StringUtils.isNotBlank(delPic)) {
                //删除旧图片
                File oldFile = new File(FileUtil.getJarPath("") + delPic);
                if (oldFile.isFile() && oldFile.exists()) {
                    if (!oldFile.delete()) {
                        return MessageUtil.error("原资产类型图片删除失败!");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return MessageUtil.error("资产类型[" + assetType.getNameCh() + "]保存失败");
        }
        return MessageUtil.success("保存资产类型[" + assetType.getNameCh() + "]成功");
    }

    /**
     * 资产类型信息导出excel.
     * @param response response
     */
    @ApiOperation(value = "资产类型信息导出excel")
    @GetMapping("/excel/export")
    public void exportAssetType(HttpServletResponse response) {
        Set<AssetType> assetTypeList = assetTypeService.getAssetTypeList("1");
        ExcelUtils.exportExcel(new ArrayList<>(assetTypeList), "资产类型导出", "导出sheet1", AssetType.class, "资产类型导出.xls", response);
    }

    /**
     * 导入excel资产类型信息.
     * @param file excel文件
     */
    @ApiOperation(value = "导入excel资产类型信息", notes = "模板文件下载：http://192.168.1.129:8080/zzjg/static/%E8%B5%84%E4%BA%A7%E7%B1%BB%E5%9E%8B%E5%AF%BC%E5%85%A5.xls")
    @PostMapping("/excel/import")
    public BaseResponse importAssetType(@RequestParam("file") MultipartFile file) {
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
        List<AssetType> finalAssetTypeList = Lists.newArrayList();
        //key为父节点 value为出现次数
        Map<String, Integer> map = new HashMap<>();
        ImportParams importParams = new ImportParams();
        // 数据处理
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);
        // 需要验证
        importParams.setNeedVerify(false);
        try {
            ExcelImportResult<AssetType> result = ExcelImportUtil.importExcelMore(file.getInputStream(), AssetType.class,
                    importParams);
            List<AssetType> assetTypeList = result.getList();
            int i = 0;
            for (AssetType assetType : assetTypeList) {
                i += 1;
                String name = assetType.getNameCh();
                String pid = assetType.getPid();
                String nameEn = assetType.getNameEn();
                StringBuffer errorMsg = new StringBuffer();
                if (StringUtils.isBlank(nameEn) || StringUtils.isBlank(name) || StringUtils.isBlank(pid)) {
                    errorMsg.append("信息不完整(资产类型名称、父节点为必填项);");
                }
                //名称校验
                List<AssetType> cnNameOne = assetTypeService.find(new AssetType(name, null, null));
                List<AssetType> enNameOne = assetTypeService.find(new AssetType(null, nameEn, null));
                if (CollectionUtils.isNotEmpty(cnNameOne) || CollectionUtils.isNotEmpty(enNameOne)) {
                    errorMsg.append("资产类型名称验证失败(中文名或英文名重复);");
                }

                if (StringUtils.isNotBlank(errorMsg)) {
                    Map<String, String> errorMap = Maps.newHashMap();
                    errorMap.put("line", String.valueOf(i));
                    errorMap.put("msg", errorMsg.toString());
                    errorInfo.add(errorMap);
                } else if (errorInfo.isEmpty()) {
                    // 这里要考虑多个同级 id需要递增
                    if (map.get(pid) == null) {
                        assetType.setId(assetTypeService.createId(pid));
                        map.put(pid, 1);
                    } else {
                        assetType.setId(assetTypeService.plusId(assetTypeService.createId(pid), map.get(pid)));
                        map.put(pid, map.get(pid) + 1);
                    }
                    assetType.setCreateTime(new Date());
                    assetType.setUpdateTime(new Date());
                    finalAssetTypeList.add(assetType);
                }
            }
            if (finalAssetTypeList.stream().map(AssetType::getNameEn).collect(Collectors.toSet()).size() < finalAssetTypeList.size()) {
                return MessageUtil.error("资产类型英文名重复");
            }
            if (finalAssetTypeList.stream().map(AssetType::getNameCh).collect(Collectors.toSet()).size() < finalAssetTypeList.size()) {
                return MessageUtil.error("资产类型中文名重复");
            }
            if (errorInfo.isEmpty()) {
                //批量插入资产类型数据
                assetTypeService.batchInsert(finalAssetTypeList);
                return MessageUtil.success("资产类型导入成功");
            } else {
                BaseResponse response = MessageUtil.error("资产类型导入失败");
                response.setData(errorInfo);
                return response;
            }

        } catch (Exception e) {
            LOGGER.error("导入失败：{}", e.getMessage());
            e.printStackTrace();
            return MessageUtil.error("资产类型导入失败:" + e.getMessage());
        }

    }

    /**
     * 解析excel资产类型信息.
     * @param file excel文件
     */
    @ApiOperation(value = "解析excel资产类型信息", notes = "模板文件下载：http://192.168.1.129:8080/zzjg/static/%E8%B5%84%E4%BA%A7%E7%B1%BB%E5%9E%8B%E5%AF%BC%E5%85%A5.xls")
    @PostMapping("/excel/analyze")
    public BaseResponse analyzeAssetType(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return MessageUtil.error("选择文件为空!");
        }
        String fileName = file.getOriginalFilename();
        // 判断文件格式
        String exp = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!"xls".equalsIgnoreCase(exp)) {
            return MessageUtil.error("Excel文件类型错误,请上传xls文件!");
        }
        ImportParams importParams = new ImportParams();
        // 数据处理
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);
        // 需要验证
        importParams.setNeedVerify(false);
        try {
            ExcelImportResult<AssetType> result = ExcelImportUtil.importExcelMore(file.getInputStream(), AssetType.class,
                    importParams);
            List<AssetType> assetTypeList = result.getList();
            if (assetTypeList.isEmpty()) {
                return MessageUtil.error("资产类型解析结果为空");
            }
            BaseResponse response = MessageUtil.success("资产类型解析成功");
            response.setData(assetTypeList);
            return response;
        } catch (Exception e) {
            LOGGER.error("导入失败：{}", e.getMessage());
            e.printStackTrace();
            return MessageUtil.error("资产类型导入失败:" + e.getMessage());
        }
    }

}
