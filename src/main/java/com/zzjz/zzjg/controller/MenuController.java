package com.zzjz.zzjg.controller;

import com.zzjz.zzjg.bean.BaseResponse;
import com.zzjz.zzjg.bean.Menu;
import com.zzjz.zzjg.service.MenuService;
import com.zzjz.zzjg.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 陈超
 * @date 2019/08/30
 */
@Api(tags = "功能菜单相关接口",description = "提供功能菜单结构的Rest API")
@RequestMapping("/menu")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 根据菜单的父级id查询菜单列表
     * @param pid
     * @return
     */
    @ApiOperation(value = "根据菜单的父级id查询菜单列表")
    @ApiImplicitParam(name = "pid",value = "如果是顶级菜单，没有父级id，则参数pid设为0")
    @GetMapping("/list/{pid}")
    public List<Menu> getMenuByPid(@PathVariable Integer pid){
        if (pid == 0 || pid == null){
            pid = null;
        }
        return menuService.getMenuByPid(pid);
    }

    /**
     * 根据菜单的id删除该菜单，如果存在子菜单，无法删除
     * @param id
     * @return
     */
    @ApiOperation(value = "根据菜单id删除对应的菜单")
    @DeleteMapping("/delete/{id}")
    public BaseResponse deleteMenu(@PathVariable Integer id){
        int countMenu = menuService.countChildrenMenu(id);
        if (countMenu > 0) {
            return MessageUtil.error("该菜单有子菜单，无法删除");
        }
        boolean flag = menuService.deleteMenu(id);
        if (flag){
            return MessageUtil.success("菜单删除成功");
        }
        return MessageUtil.error("资产删除失败");
    }

    /**
     * 如果菜单已经存在，则修改菜单信息
     * 如果菜单不存在，新增菜单
     * @param menu
     * @return
     */
    @ApiOperation(value = "新增或者修改菜单",notes = "根据菜单id是否为空来区分新增和更新")
    @PostMapping("/addOrUpdate")
    public BaseResponse addOrUpdateMenu(@Valid @RequestBody Menu menu){
        boolean flag;
        String message;
        if (menu.getId() == null || menu.getId() == 0){
            //插入新的菜单
            menu.setId(null);
            flag = menuService.addMenu(menu);
            message = "菜单添加成功";
        }else {
            //更新菜单信息
            flag = menuService.updateMenu(menu);
            message = "菜单更新成功";
        }
        return flag ? MessageUtil.success(message) : MessageUtil.error("菜单信息保存失败");
    }

    /**
     * 根据菜单id查询该菜单的信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据菜单的id查询菜单的详情")
    @GetMapping("/info/{id}")
    public Menu getMenuById(@PathVariable Integer id){
        return menuService.getMenuById(id);
    }

    /**
     * 查询出所有的菜单列表
     * @return
     */
    @ApiOperation(value = "查询所有的菜单列表")
    @PostMapping("/allList")
    public List<Menu> getAllMenu(){
        return menuService.getAllMenu();
    }
}
