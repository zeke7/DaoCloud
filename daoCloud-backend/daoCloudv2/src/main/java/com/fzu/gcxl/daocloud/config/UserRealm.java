package com.fzu.gcxl.daocloud.config;

import com.fzu.gcxl.daocloud.util.JwtUtil;
import com.fzu.gcxl.daocloud.util.StringUtil;
import com.fzu.gcxl.daocloud.config.jwtfilter.JwtToken;
import com.fzu.gcxl.daocloud.domain.entity.Account;
import com.fzu.gcxl.daocloud.domain.entity.Role;
import com.fzu.gcxl.daocloud.domain.entity.User;
import com.fzu.gcxl.daocloud.domain.persistence.AccountRepository;
import com.fzu.gcxl.daocloud.domain.persistence.RoleRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();

        Integer user_role_id = currentUser.getRoleId();
        Role user_role = roleRepository.findRoleById(user_role_id);
        // 查询数据库，获取用户的角色信息
        Set<String> roles = new HashSet<String>() {
            {
                add(user_role.getRoleName());
            };
        };
        // 查询数据库，获取用户的权限信息
        Set<String> perms = new HashSet<String>() {
            {
                add(user_role.getRolePerm());
            };
        };

        info.setRoles(roles);
        info.setStringPermissions(perms);

        return info;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getPrincipal();
        String username = JwtUtil.getClaim(token, "account");
        if (StringUtil.isBlank(username)) {
            throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
        }

        Account account = accountRepository.findAccountByPhone(username);

        if (account == null) {
            throw new AuthenticationException("该帐号不存在(The account does not exist.)");
        }
        if (JwtUtil.verify(token)) {
            return new SimpleAuthenticationInfo(token, token, "userRealm");
        }
        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
    }
}
