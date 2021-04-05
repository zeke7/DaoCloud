package com.fzu.gcxl.daocloud.config;

import com.fzu.gcxl.daocloud.config.JWTPackage.JwtCredentialsMatcher;
import com.fzu.gcxl.daocloud.config.JWTPackage.JwtFilter;
import com.fzu.gcxl.daocloud.config.JWTPackage.MultiRealmAuthenticator;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.*;

import javax.servlet.Filter;

@Configuration
public class ShiroConfig {


    /**
     * 交由 Spring 来自动地管理 Shiro-Bean 的生命周期
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 为 Spring-Bean 开启对 Shiro 注解的支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator app = new DefaultAdvisorAutoProxyCreator();
        app.setProxyTargetClass(true);
        return app;

    }

    /**
     * 不向 Spring容器中注册 JwtFilter Bean，防止 Spring 将 JwtFilter 注册为全局过滤器
     * 全局过滤器会对所有请求进行拦截，而本例中只需要拦截除 /login 和 /logout 外的请求
     * 另一种简单做法是：直接去掉 jwtFilter()上的 @Bean 注解
     */
    @Bean
    public FilterRegistrationBean<Filter> registration(JwtFilter filter) {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

    /**
     * 配置访问资源需要的权限
     */
    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/authorized");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        // 添加 jwt 专用过滤器，拦截除 /login 和 /logout 外的请求
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("jwtFilter", jwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();


        //放行Swagger接口
        filterChainDefinitionMap.put("/v2/api-docs","anon");
        filterChainDefinitionMap.put("/swagger-resources/configuration/ui","anon");
        filterChainDefinitionMap.put("/swagger-resources","anon");
        filterChainDefinitionMap.put("/swagger-resources/configuration/security","anon");
        filterChainDefinitionMap.put("/swagger-ui.html","anon");
        filterChainDefinitionMap.put("/webjars/**","anon");


        filterChainDefinitionMap.put("/toSignup", "anon"); // 可匿名访问
        filterChainDefinitionMap.put("/toLogin", "anon"); // 可匿名访问

        filterChainDefinitionMap.put("/signup", "anon"); // 注册 可匿名访问
        filterChainDefinitionMap.put("/login", "anon"); // 登陆 可匿名访问

        filterChainDefinitionMap.put("/logout", "logout"); // 退出登录
        filterChainDefinitionMap.put("/**", "jwtFilter, authc"); // 需登录才能访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 配置 ModularRealmAuthenticator
     */
    @Bean
    public ModularRealmAuthenticator authenticator() {
        ModularRealmAuthenticator authenticator = new MultiRealmAuthenticator();
        // 设置多 Realm的认证策略，默认 AtLeastOneSuccessfulStrategy
        AuthenticationStrategy strategy = new FirstSuccessfulStrategy();
        authenticator.setAuthenticationStrategy(strategy);
        return authenticator;
    }

    /**
     * 禁用session, 不保存用户登录状态。保证每次请求都重新认证
     */
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    /**
     * JwtRealm 配置，需实现 Realm 接口
     */
    @Bean
    JwtRealm jwtRealm() {
        JwtRealm jwtRealm = new JwtRealm();
        // 设置加密算法
        CredentialsMatcher credentialsMatcher = new JwtCredentialsMatcher();
        // 设置加密次数
        jwtRealm.setCredentialsMatcher(credentialsMatcher);
        return jwtRealm;
    }

    /**
     * ShiroRealm 配置，需实现 Realm 接口
     */
    @Bean
    ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        // 设置加密算法
       // HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("SHA-1");
        // 设置加密次数
       // credentialsMatcher.setHashIterations(16);
        //shiroRealm.setCredentialsMatcher(credentialsMatcher);
        return shiroRealm;
    }

    /**
     * 配置 SecurityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 1.Authenticator
        securityManager.setAuthenticator(authenticator());

        // 2.Realm
        List<Realm> realms = new ArrayList<Realm>(16);
        realms.add(jwtRealm());
        realms.add(shiroRealm());
        securityManager.setRealms(realms);

        // 3.关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator());
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    //ShiroFilterFactoryBean:3
//    @Bean
//    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDWSM") DefaultWebSecurityManager defaultWebSecurityManager){
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        // 设置安全管理器
//        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
//        // 添加shiro的内置过滤器
//        /*
//         * anon: 无需认证就可以访问
//         * authc: 必须认证了才能访问
//         * user: 必须拥有 记住我 功能才能用
//         * perms: 拥有对某个资源的权限才能访问
//         * role: 拥有某个角色权限才能访问
//         * */
//        //拦截
//        Map<String, String> filterchain = new LinkedHashMap<>();
////        filterchain.put("/user/add","authc");
////        filterchain.put("/user/update","authc");
//        //授权, 会跳转到未授权页面
////        filterchain.put("/user/add", "perms[user:add]");
////
////        filterchain.put("/user/*","authc");
////        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterchain);
//
//        // 设置登陆的请求
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        // 未授权页面
//        shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");
//
//
//        return shiroFilterFactoryBean;
//    }
//
//    //DefaultWebSecurityManager:2
//    @Bean(name = "getDWSM")
//    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
//        DefaultWebSecurityManager SecurityManager = new DefaultWebSecurityManager();
//        //关联UserRealm
//        SecurityManager.setRealm(userRealm);
//
//        return SecurityManager;
//    }
//
    //创建realm:1



}
