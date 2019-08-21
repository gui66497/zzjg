package com.zzjz.zzjg.service;

import com.zzjz.zzjg.bean.User;

/**
 * @author 房桂堂
 * @description AssetService
 * @date 2019/7/4 14:23
 */
public interface UserService {

    /**
     * 根据用户名获取用户信息.
     * @param userName 用户名
     * @return 用户
     */
    User getByUserName(String userName);

    /**
     * 根据用户id获取用户信息.
     * @param id 用户id
     * @return 用户
     */
    User getById(long id);

}
