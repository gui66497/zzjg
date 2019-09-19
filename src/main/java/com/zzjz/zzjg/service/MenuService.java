package com.zzjz.zzjg.service;

import com.zzjz.zzjg.bean.Menu;

import java.util.List;

public interface MenuService {
    /**
     * 根据菜单父级id查询菜单列表
     * @param pid
     * @return
     */
    List<Menu> getMenuByPid(Integer pid);

    /**
     * 根据菜单id查询其子菜单数量
     * @param id
     * @return
     */
    int countChildrenMenu(Integer id);

    /**
     * 根据菜单id删除对应的菜单，如果有子菜单，不能删除
     * @param id
     * @return
     */
    boolean deleteMenu(Integer id);

    /**
     * 新增菜单信息
     * @param menu
     * @return
     */
    boolean addMenu(Menu menu);

    /**
     * 更新菜单信息
     * @param menu
     * @return
     */
    boolean updateMenu(Menu menu);

    /**
     * 根据菜单的id查询菜单的信息
     * @param id
     * @return
     */
    Menu getMenuById(Integer id);

    /**
     * 查询所有的菜单
     * @return
     */
    List<Menu> getAllMenu();
}
