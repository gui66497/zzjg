package com.zzjz.zzjg.service.impl;

import com.zzjz.zzjg.bean.Menu;
import com.zzjz.zzjg.mapper.MenuMapper;
import com.zzjz.zzjg.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getMenuByPid(Integer pid) {
        return menuMapper.getMenuByPid(pid);
    }

    @Override
    public int countChildrenMenu(Integer id) {
        return menuMapper.countChildrenMenu(id);
    }

    @Override
    public boolean deleteMenu(Integer id) {
        int row = menuMapper.deleteMenu(id);
        if (row > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean addMenu(Menu menu) {
        int row = menuMapper.addMenu(menu);
        if (row > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateMenu(Menu menu) {
        int row = menuMapper.updateMenu(menu);
        if (row > 0){
            return true;
        }
        return false;
    }

    @Override
    public Menu getMenuById(Integer id) {
        return menuMapper.getMenuById(id);
    }

    @Override
    public List<Menu> getAllMenu() {
        return menuMapper.getAllMenu();
    }
}
