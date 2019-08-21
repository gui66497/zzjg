package com.zzjz.zzjg;

import com.zzjz.zzjg.bean.User;
import com.zzjz.zzjg.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description 用户测试类
 * @Author 房桂堂
 * @Date 2019/8/21 9:20
 */
public class UserTest extends ZzjgApplicationTests {

    @Autowired
    UserService userService;

    @Test
    public void testGetByUserName() {
        String userName = "zhangsan";
        User user = userService.getByUserName(userName);
        assert user != null;
    }
}
