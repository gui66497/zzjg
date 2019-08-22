package com.zzjz.zzjg.shiro;

import com.zzjz.zzjg.bean.User;
import com.zzjz.zzjg.service.UserService;
import com.zzjz.zzjg.util.JwtUtil;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * @ClassName: JWTFilter
 * @Description: jwt自定义filter
 * @author 房桂堂
 * @date 2019/8/21 9:23
 */
@Component
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    UserService userService;

    /**
     * 刷新周期为24小时.
     */
    private static final int TOKEN_REFRESH_INTERVAL = 60 * 60 * 24;

	/**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    /*@Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        LOGGER.info("判断用户是否想要登录：{}",authorization);
        return authorization != null;
    }*/

    /**
     * 登录验证.
     * @param request request
     * @param response response
     * @return 结果
     * @throws Exception Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization");
        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 执行登录认证并按需刷新token.
     * @param request request
     * @param response response
     * @param mappedValue mappedValue
     * @return 结果
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) {
        try {
            executeLogin(request, response);

            // 认证成功后判断是否需要刷新token
            String oldToken = getAuthzHeader(request);
            boolean shouldRefresh = shouldTokenRefresh(Objects.requireNonNull(JwtUtil.getIssuedAt(oldToken)));
            if (shouldRefresh) {
                String userName = JwtUtil.getUsername(oldToken);
                User user = userService.getByUserName(userName);
                if (user != null) {
                    HttpServletResponse httpResponse = WebUtils.toHttp(response);
                    //生成新的TOKEN
                    String newToken = JwtUtil.sign(user.getUserName(), user.getPassword());
                    httpResponse.setHeader("token", newToken);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    /*@Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                response401(request, response);
            }
        }
        return true;
    }*/


    /**
     * 将非法请求跳转到 /401
     */
    private void response401(ServletRequest req, ServletResponse resp) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            httpServletResponse.sendRedirect("/401");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * 判断是否需要刷新TOKEN.
     * @param issueAt token签发日期
     * @return 是否需要刷新TOKEN
     */
    private boolean shouldTokenRefresh(Date issueAt) {
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(TOKEN_REFRESH_INTERVAL).isAfter(issueTime);
    }
}
