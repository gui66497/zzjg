package com.zzjz.zzjg.controller;

import com.google.common.io.Files;
import com.zzjz.zzjg.bean.AssetType;
import com.zzjz.zzjg.bean.BaseResponse;
import com.zzjz.zzjg.bean.ResultCode;
import com.zzjz.zzjg.service.AssetTypeService;
import com.zzjz.zzjg.util.FileUtil;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * @author 房桂堂
 * @description AssetTypeController
 * @date 2019/7/5 17:15
 */
@Api(tags = "资产类型相关接口", description = "提供资产类型相关的 Rest API")
@RestController
@RequestMapping("/assetType")
public class AssetTypeController {

    @Autowired
    AssetTypeService assetTypeService;

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
        BaseResponse<AssetType> response = new BaseResponse<>();
        if (assetType == null) {
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
        BaseResponse<AssetType> response = new BaseResponse<>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(new ArrayList<>(assetTypeSet));
        response.setMessage("查询资产类型成功");
        return response;
    }

    /**
     * 根据id删除资产类型.
     * @param id id
     * @return 结果
     */
    @ApiOperation("根据id删除资产类型")
    @DeleteMapping("/{id}")
    public BaseResponse deleteAssetTypeById(@PathVariable String id) {
        String patth = FileUtil.getJarPath("static/image/assetType");
        System.out.println("patth:" + patth);
        System.out.println(1);
        AssetType assetType = assetTypeService.getAssetTypeById(id);
        if (assetType == null) {
            return MessageUtil.error("删除失败！该资产不存在");
        }
        //删除前验证
        if (!assetTypeService.deleteValidationAssetType(id)) {
            return MessageUtil.error("删除失败！该分类下有关联资产分类");
        }
        if (!assetTypeService.deleteValidationRes(id)) {
            return MessageUtil.error("删除失败！该分类下有关联资产");
        }
        boolean res = assetTypeService.deleteAssetTypeById(id);
        if (res) {
            return MessageUtil.success("删除资产类型[" + assetType.getNameCh() + "]成功");
        } else {
            return MessageUtil.error("删除资产类型[" + assetType.getNameCh() + "]失败");
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
        try {
            if (StringUtils.isBlank(assetType.getId())) {
                assetType.setId(createId(assetType.getPid()));
            } else {
                AssetType oldAssetType = assetTypeService.getAssetTypeById(assetType.getId());
                if (oldAssetType != null) {
                    delPic = oldAssetType.getPic();
                    assetType.setPic(delPic);
                    //判断是否修改了上级资产类型
                    if (!assetType.getPid().equals(oldAssetType.getPid())) {
                        newId = createId(assetType.getPid());
                    }
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
     * 根据父级ID生成ID.
     * @param pId 父级id
     * @return id
     */
    private String createId(String pId) {
        AssetType assetType = assetTypeService.findLastByPId(pId);
        if (assetType != null) {
            String[] splitArr = assetType.getId().split("\\.");
            Integer item = Integer.parseInt(splitArr[splitArr.length - 1]) + 1;
            splitArr[splitArr.length - 1] = item.toString();
            String id = StringUtils.join(splitArr, ".");
            return id;
        } else {
            return pId + ".1";
        }
    }
}
