package com.fzu.gcxl.daocloud.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //ShiroFilterFactoryBean:3
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDWSM") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        // 添加shiro的内置过滤器
        /*
         * anon: 无需认证就可以访问
         * authc: 必须认证了才能访问
         * user: 必须拥有 记住我 功能才能用
         * perms: 拥有对某个资源的权限才能访问
         * role: 拥有某个角色权限才能访问
         * */
        //拦截
        Map<String, String> filterchain = new LinkedHashMap<>();
//        filterchain.put("/user/add","authc");
//        filterchain.put("/user/update","authc");
        //授权, 会跳转到未授权页面
//        filterchain.put("/user/add", "perms[user:add]");
//
//        filterchain.put("/user/*","authc");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterchain);

        // 设置登陆的请求
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");


        return shiroFilterFactoryBean;
    }

    //DefaultWebSecurityManager:2
    @Bean(name = "getDWSM")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager SecurityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        SecurityManager.setRealm(userRealm);

        return SecurityManager;
    }

    //创建realm:1
    @Bean
    public UserRealm userRealm(){

        return new UserRealm();
    }



}
