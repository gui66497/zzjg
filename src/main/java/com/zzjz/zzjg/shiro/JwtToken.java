package com.zzjz.zzjg.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ClassName: JWTToken
 * @Description:
 * @author 房桂堂
 * @date 2019/8/20 17:10
 */
public class JwtToken implements AuthenticationToken {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 秘钥
	 */
	private String token;

	/**
	 * 构造
	 * @param token token
	 */
	public JwtToken(String token) {
		this.token = token;
	}

	@Override
	public Object getPrincipal() {
		return token;
	}

	
	@Override
	public Object getCredentials() {
		return token;
	}

}
