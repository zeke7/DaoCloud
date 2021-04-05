package com.fzu.gcxl.daocloud.config;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fzu.gcxl.daocloud.entity.Account;
import com.fzu.gcxl.daocloud.service.implemention.AccountServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 同时开启身份验证和权限验证，需要继承 AuthorizingRealm 
 * 并实现其  doGetAuthenticationInfo()和 doGetAuthorizationInfo 两个方法
 */
@SuppressWarnings("serial")
public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	AccountServiceImpl accountService;

//	public static Map<String, UserEntity> userMap = new HashMap<String, UserEntity>(16);
	public static Map<String, Set<String>> roleMap = new HashMap<String, Set<String>>(16);
	public static Map<String, Set<String>> permMap = new HashMap<String, Set<String>>(16);



	static {
		// 用户角色: "1"->学生 "2"->老师
		roleMap.put("admin", new HashSet<String>() {
			{
				add("admin");
			}
		});
		permMap.put("normal", new HashSet<String>() {
			{
				add("article:read");
				add("user:modify");
			}
		});
		permMap.put("special", new HashSet<String>() {
			{
				add("article:read");
				add("user:modify");
			}
		});

	}
 
	/**
	 * 限定这个 Realm 只处理 UsernamePasswordToken
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}
	
	/**
	 * 查询数据库，将获取到的用户安全数据封装返回
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("ShiroRealm->doGetAuthenticationInfo。。。");
		// 从 AuthenticationToken 中获取当前用户
		String username = (String) token.getPrincipal();
		// 查询数据库获取用户信息，此处使用 Map 来模拟数据库
		Account account = accountService.queryAccountByPhone(username);
		//UserEntity user = userMap.get(username);
 
		// 用户不存在
		if (account == null) {
			throw new UnknownAccountException("用户不存在！");
		}
 
		// 使用用户名作为盐值
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
 
		/**
		 * 将获取到的用户数据封装成 AuthenticationInfo 对象返回，此处封装为 SimpleAuthenticationInfo 对象。
		 *  参数1. 认证的实体信息，可以是从数据库中获取到的用户实体类对象或者用户名 
		 *  参数2. 查询获取到的登录密码 
		 *  参数3. 盐值
		 *  参数4. 当前 Realm 对象的名称，直接调用父类的 getName() 方法即可
		 */
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(account, account.getAccountPwd(), credentialsSalt,
				getName());
		return info;
	}
 
	/**
	 * 查询数据库，将获取到的用户的角色及权限信息返回
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("ShiroRealm->doGetAuthorizationInfo。。。");
		// 获取当前用户
		Account currentUser = (Account) SecurityUtils.getSubject().getPrincipal();
		String account_role = currentUser.getAccountRole();
		String account_perm = currentUser.getAccountPerm();
		// 查询数据库，获取用户的角色信息
		Set<String> roles = roleMap.get(account_role);
		// 查询数据库，获取用户的权限信息
		Set<String> perms = permMap.get(account_perm);
 
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roles);
		info.setStringPermissions(perms);
		return info;
	}
 
}