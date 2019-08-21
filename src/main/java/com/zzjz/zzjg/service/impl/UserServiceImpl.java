package com.zzjz.zzjg.service.impl;

import com.zzjz.zzjg.bean.User;
import com.zzjz.zzjg.mapper.UserMapper;
import com.zzjz.zzjg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 用户service实现类
 * @Author 房桂堂
 * @Date 2019/8/21 8:54
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getByUserName(String userName) {
        return userMapper.getByUserName(userName);
    }

    @Override
    public User getById(long id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
