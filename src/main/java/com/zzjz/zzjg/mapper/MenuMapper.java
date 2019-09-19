package com.zzjz.zzjg.mapper;

import com.zzjz.zzjg.bean.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MenuMapper {
    /**
     * 根据父级id查询菜单列表
     * @param pid
     * @return
     */
    List<Menu> getMenuByPid(@Param("pid") Integer pid);

    /**
     * 根据菜单id查询该菜单下的子菜单数
     * @param id
     * @return
     */
    int countChildrenMenu(@Param("id") Integer id);

    /**
     * 根据菜单id删除菜单，如果该菜单有子菜单，无法删除
     * @param id
     * @return
     */
    int deleteMenu(@Param("id") Integer id);

    /**
     * 新增菜单信息
     * @param menu
     * @return
     */
    int addMenu(Menu menu);

    /**
     * 更新菜单信息
     * @param menu
     * @return
     */
    int updateMenu(Menu menu);

    /**
     * 根据菜单的id查询该菜单的信息
     * @param id
     * @return
     */
    Menu getMenuById(Integer id);

    /**
     * 查询所有的菜单列表
     * @return
     */
    List<Menu> getAllMenu();
}
