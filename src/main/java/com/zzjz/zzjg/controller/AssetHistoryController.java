package com.zzjz.zzjg.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.github.pagehelper.PageInfo;
import com.github.wujun234.uid.UidGenerator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zzjz.zzjg.bean.*;
import com.zzjz.zzjg.service.AssetHistoryService;
import com.zzjz.zzjg.service.AssetService;
import com.zzjz.zzjg.service.AssetTypeService;
import com.zzjz.zzjg.util.ExcelUtils;
import com.zzjz.zzjg.util.FileUtil;
import com.zzjz.zzjg.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = "资产履历信息相关接口",description = "提供资产履历相关的 Rest API")
@RestController
@RequestMapping("/assetHistory")
public class AssetHistoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetHistoryController.class);

    @Autowired
    private AssetHistoryService assetHistoryService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private AssetTypeService assetTypeService;

    @Resource
    private UidGenerator defaultUidGenerator;

    /**
     * 分页获取资产的履历信息
     * @param assetRequest
     * @return
     */
    @ApiOperation(value = "资产履历分页信息")
    @PostMapping("/list")
    public PageInfo<AssetHistory> getAssetHistory(@RequestBody AssetRequest assetRequest){
        List<AssetHistory> assetHistoryList = assetHistoryService.getAssetHistory(assetRequest);
        for (int i=0; i<assetHistoryList.size(); i++){
            //根据资产id获取资产信息
            Asset asset = assetService.selectAssetById(assetHistoryList.get(i).getAssetId());
            //根据资产类型id获取资产类型名
            AssetType assetType = assetTypeService.getAssetTypeById(asset.getType());
            asset.setTypeName(assetType.getNameCh());
            //保存资产信息
            assetHistoryList.get(i).setAsset(asset);
        }
        return new PageInfo<>(assetHistoryList);
    }

    /**
     * 根据记录id删除或批量删除资产履历记录
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据履历的id删除或批量删除资产履历记录")
    @ApiImplicitParam(name = "ids",value = "多个id之间用逗号隔开")
    @DeleteMapping("/delete/{ids}")
    public BaseResponse deleteAssetHistory(@PathVariable String ids){
        BaseResponse response = new BaseResponse();
        if (StringUtils.isBlank(ids)) {
            response.setMessage("参数错误");
            response.setResultCode(ResultCode.RESULT_BAD_REQUEST);
            return response;
        }
        String[] idList = StringUtils.split(ids, ",");
        boolean flag = assetHistoryService.deleteAssetHistory(idList);
        if (flag) {
            return MessageUtil.success("资产履历记录删除成功");
        }
        return MessageUtil.error("资产履历记录删除失败");
    }

    /**
     * 如果资产履历记录已经存在，则修改履历信息
     * 如果资产履历信息不存在，则新增履历记录
     * @param historyRequest
     * @return
     */
    @ApiOperation(value = "新增或编辑资产履历信息",notes = "如果已存在则修改,否则添加")
    @PostMapping("/addOrUpdate")
    public BaseResponse addOrUpdateAssetHistory(@RequestBody HistoryRequest historyRequest){
        boolean result;
        String message;
        if (historyRequest.getId() == null){
            //新增资产履历信息
            historyRequest.setId(String.valueOf(defaultUidGenerator.getUID()));
            historyRequest.setCreateTime(new Date());
            historyRequest.setUpdateTime(new Date());
            result = assetHistoryService.addAssetHistory(historyRequest);
            message = "新增资产履历信息成功";
        }else {
            //修改资产履历信息
            historyRequest.setUpdateTime(new Date());
            HistoryRequest history = assetHistoryService.selectHistoryById(historyRequest.getId());
            if (history==null){
                MessageUtil.error("更新错误！"+historyRequest.getId()+"记录不存在！");
            }
            result = assetHistoryService.updateAssetHistory(historyRequest);
            message = "资产履历信息更新成功";
        }
        return result ? MessageUtil.success(message) : MessageUtil.error("资产履历信息保存失败");
    }

    /**
     * 查询出所有资产履历信息
     * @return
     */
    @ApiOperation(value = "查询出所有的资产履历信息")
    @GetMapping("/listAll")
    public List<HistoryRequest> getAllAssetHistory(){
        return assetHistoryService.getAllAssetHistory();
    }

    /**
     * 支持资产履历信息的批量导入
     * excle文件
     * @param file
     * @return
     */
    @ApiOperation(value = "导入excle中的资产履历记录",notes = "excle模版:http://localhost:8080/zzjg/static/testAssetHistory-import-output.xls")
    @PostMapping("/excle/import")
    public BaseResponse batchInsert(@RequestParam("file") MultipartFile file){
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
        List<HistoryRequest> historyRequestList = Lists.newArrayList();
        ImportParams importParams = new ImportParams();
        // 数据处理
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);
        // 需要验证
        importParams.setNeedVerify(false);
        try {
            ExcelImportResult<HistoryRequest> result = ExcelImportUtil.importExcelMore(file.getInputStream(), HistoryRequest.class,
                    importParams);
            List<HistoryRequest> historyList = result.getList();
            int i=0;
            for (HistoryRequest history:historyList){
                i += 1;
                String assetName = history.getAssetName();
                String ip = history.getIp();
                String macAddress = history.getMacAddress();
                String phone = history.getResponsiblePhone();

                StringBuffer errorMsg = new StringBuffer();
                if (StringUtils.isBlank(assetName) || StringUtils.isBlank(ip) || StringUtils.isBlank(macAddress)){
                    errorMsg.append("信息不完整（ip地址,资产名称,mac地址不能为空）;");
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

                //资产履历记录校验
                HistoryRequest historyRecord = assetHistoryService.selectHistoryByAssetNameAndIp(history.getAssetName(),history.getIp());
                if (historyRecord != null){
                    errorMsg.append("资产【"+history.getAssetName()+"】下的履历记录中ip为【"+history.getIp()+"】的记录已经存在;");
                }

                //资产校验
                Asset asset = new Asset();
                asset.setName(history.getAssetName());
                List<Asset> assetList = assetService.find(asset);
                if (assetList == null){
                    errorMsg.append("资产【"+history.getAssetName()+"】不存在;");
                }else {
                    history.setAssetId(assetList.get(0).getId());
                }

                if (StringUtils.isNotBlank(errorMsg)) {
                    Map<String, String> errorMap = Maps.newHashMap();
                    errorMap.put("line", String.valueOf(i));
                    errorMap.put("msg", errorMsg.toString());
                    errorInfo.add(errorMap);
                } else if (errorInfo.isEmpty()) {
                    history.setId(String.valueOf(defaultUidGenerator.getUID()));
                    history.setCreateTime(new Date());
                    history.setUpdateTime(new Date());
                    historyRequestList.add(history);
                }
            }
            if (errorInfo.isEmpty()) {
                //批量插入资产数据
                assetHistoryService.batchInsert(historyRequestList);
                return MessageUtil.success("资产导入成功");
            } else {
                BaseResponse response = MessageUtil.error("资产导入失败");
                response.setData(errorInfo);
                return response;
            }

        }catch (Exception e) {
            LOGGER.error("导入失败：{}", e.getMessage());
            e.printStackTrace();
            return MessageUtil.error("资产导入失败:" + e.getMessage());
        }
    }

    /**
     * 解析excel资产信息.
     * @param file excel文件
     */
    @ApiOperation(value = "解析excel中的资产履历信息",notes = "excle模版:http://localhost:8080/zzjg/static/testAssetHistory-import-output.xls")
    @PostMapping("/excel/analyze")
    public BaseResponse analyzeAsset(@RequestParam("file") MultipartFile file) {
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
            ExcelImportResult<HistoryRequest> result = ExcelImportUtil.importExcelMore(file.getInputStream(), HistoryRequest.class,
                    importParams);
            List<HistoryRequest> historyRequestList = result.getList();
            if (historyRequestList.isEmpty()) {
                return MessageUtil.error("资产解析结果为空");
            }
            BaseResponse response = MessageUtil.success("资产解析成功");
            response.setData(historyRequestList);
            return response;

        } catch (Exception e) {
            LOGGER.error("导入失败：{}", e.getMessage());
            e.printStackTrace();
            return MessageUtil.error("资产解析失败:" + e.getMessage());
        }
    }

    /**
     * 资产信息导出excel.
     * @param response response
     */
    @ApiOperation(value = "资产履历信息导出为excel表格")
    @GetMapping("/excel/export")
    public void exportAssetHistory(HttpServletResponse response) {
        List<HistoryRequest> historyRequestList = assetHistoryService.getAllAssetHistory();
        //获取资产信息
        for (HistoryRequest historyRequest:historyRequestList){
            Asset asset = assetService.selectAssetById(historyRequest.getAssetId());
            historyRequest.setAssetName(asset.getName());
        }
        ExcelUtils.exportExcel(historyRequestList, "资产履历导出", "导出sheet1", HistoryRequest.class, "资产履历导出.xls", response);
    }

}
