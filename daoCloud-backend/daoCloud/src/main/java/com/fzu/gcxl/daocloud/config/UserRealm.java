package com.fzu.gcxl.daocloud.config;

import com.fzu.gcxl.daocloud.entity.Account;
import com.fzu.gcxl.daocloud.entity.User;
import com.fzu.gcxl.daocloud.mapper.AccountMapper;
import com.fzu.gcxl.daocloud.service.AccountService;
import com.fzu.gcxl.daocloud.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    AccountMapper accountMapper;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权。。。");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //拿到当前登陆的对象
        Subject subject = SecurityUtils.getSubject();
        Account currentAccount = (Account) subject.getPrincipal();
        // 设置字段:手机号码
        info.addStringPermission(currentAccount.getLoginPhone());

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证。。。");
        //用户名，密码 数据库中取
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        Account loginaccount = accountMapper.queryAccountByPhone(userToken.getUsername());
        //User loginuser = userService.queryUserByPhone(userToken.getUsername());
        System.out.println(loginaccount);
        //封装用户的登陆数据
        if (loginaccount==null){
            return null;
        }

       return new SimpleAuthenticationInfo(loginaccount,loginaccount.getLoginPassword(),"");
    }
}
