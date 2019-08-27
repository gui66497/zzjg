package com.zzjz.zzjg.shiro;

import com.github.pagehelper.util.StringUtil;
import com.zzjz.zzjg.bean.User;
import com.zzjz.zzjg.service.UserService;
import com.zzjz.zzjg.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description Shiro认证
 * @Author 房桂堂
 * @Date 2019/8/20 17:35
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    /**
     * 必须重写此方法，不然shiro会报错.
     * @param token token
     * @return 结果
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可.
     * @param auth auth
     * @return 授权
     * @throws AuthenticationException AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) {
        String token = (String) auth.getPrincipal();
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        if (StringUtil.isEmpty(username)) {
            throw new IncorrectCredentialsException("用户名无效");
        }

        User user = userService.getByUserName(username);
        if (user == null) {
            throw new UnknownAccountException("用户[" + username + "]不存在");
        }

        if(!JwtUtil.verify(token, username, user.getPassword())){
            throw new AuthenticationException("验证失败");
        }

        return new SimpleAuthenticationInfo(token, token, getName());
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法
     * @param principals principals
     * @return 用户权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JwtUtil.getUsername(principals.toString());
        //User user = userService.getUser(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //simpleAuthorizationInfo.addRole(user.getRole());
        //Set<String> permission = new HashSet<String>(Arrays.asList(user.getPerms().split(",")));
        //simpleAuthorizationInfo.addStringPermissions(permission);

        return simpleAuthorizationInfo;
    }

}
