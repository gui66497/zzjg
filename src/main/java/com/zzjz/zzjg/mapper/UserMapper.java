package com.zzjz.zzjg.mapper;

import com.zzjz.zzjg.bean.User;
import java.util.List;

/**
 * @Description: 用户Mapper
 * @author 房桂堂
 * @date 2019/8/21 8:49
 */
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据用户名获取用户信息.
     * @param userName 用户名
     * @return 用户实体
     */
    User getByUserName(String userName);

    /**
     * 获取所有用户.
     * @return 用户列表
     */
    List<User> queryAllUser();
}