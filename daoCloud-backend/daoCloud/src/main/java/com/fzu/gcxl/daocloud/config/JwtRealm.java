package com.fzu.gcxl.daocloud.config;

import java.util.Set;

import com.fzu.gcxl.daocloud.entity.Account;
import com.fzu.gcxl.daocloud.config.JWTPackage.JwtToken;
import com.fzu.gcxl.daocloud.service.implemention.AccountServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * JwtRealm 只负责校验 JwtToken
 */
public class JwtRealm extends AuthorizingRealm {

	@Autowired
	AccountServiceImpl accountService;
	/**
	 * 限定这个 Realm 只处理我们自定义的 JwtToken
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JwtToken;
	}

	/**
	 * 此处的 SimpleAuthenticationInfo 可返回任意值，密码校验时不会用到它
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		System.out.println("JwtRealm->doGetAuthenticationInfo。。。");
		JwtToken jwtToken = (JwtToken) authcToken;
		if (jwtToken.getPrincipal() == null) {
			throw new AccountException("JWT token参数异常！");
		}
		// 从 JwtToken 中获取当前用户
		String username = jwtToken.getPrincipal().toString();
		System.out.println(username);
		Account account = accountService.queryAccountByPhone(username);
		System.out.println(account);

		// 用户不存在
		if (account == null) {
			throw new UnknownAccountException("用户不存在！");
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(account, username, getName());
		return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("JwtRealm->doGetAuthenticationInfo。。。");
		// 获取当前用户
		Account currentUser = (Account) SecurityUtils.getSubject().getPrincipal();
		// UserEntity currentUser = (UserEntity) principals.getPrimaryPrincipal();
		System.out.println(currentUser);

		// 查询数据库，获取用户的角色信息
		Set<String> roles = ShiroRealm.roleMap.get("plum");
		// 查询数据库，获取用户的权限信息
		Set<String> perms = ShiroRealm.permMap.get("plum");



		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roles);
		info.setStringPermissions(perms);
		return info;
	}

}
