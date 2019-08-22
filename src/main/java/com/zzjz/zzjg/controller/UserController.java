package com.zzjz.zzjg.controller;

import com.zzjz.zzjg.bean.BaseResponse;
import com.zzjz.zzjg.bean.ResultCode;
import com.zzjz.zzjg.bean.User;
import com.zzjz.zzjg.service.UserService;
import com.zzjz.zzjg.shiro.JwtToken;
import com.zzjz.zzjg.util.JwtUtil;
import com.zzjz.zzjg.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description 用户Controller
 * @Author 房桂堂
 * @Date 2019/8/21 9:52
 */
@Api(tags = "用户相关接口", description = "提供用户相关的 Rest API")
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @ApiOperation(value="登录功能", notes="用户密码登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "User")})
    @PostMapping("/login")
    public BaseResponse login(@RequestBody User user, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        String token = JwtUtil.sign(user.getUserName(), user.getPassword());
        JwtToken jwtToken = new JwtToken(token);
        subject.login(jwtToken);
        response.setHeader("token", token);
        return MessageUtil.success("登录成功", token);
    }

    @ApiOperation(value="获取用户列表")
    @GetMapping("/list")
    public BaseResponse<User> list() {
        BaseResponse<User> response = new BaseResponse<>();
        List<User> userList = userService.getAllUser();
        response.setData(userList);
        response.setMessage("获取用户列表成功");
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        return response;
    }

}
