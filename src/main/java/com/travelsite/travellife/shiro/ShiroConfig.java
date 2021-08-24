package com.travelsite.travellife.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;


@Configuration
public class ShiroConfig {

    //配置密码比较器
    @Bean("credentialsMatcher")
    public RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher(CacheManager cacheManager){
        RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManager);
        return retryLimitHashedCredentialsMatcher;
    }

    //定义关于URL的规则和访问权限
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(manager);
        /**
         *  —anon 无需认证(登录)可访问
         * 	—authc 必须认证才可访问
         * 	—perms[标识] 拥有资源权限才可访问
         * 	—role 拥有角色权限才可访问
         * 	—user 认证和自动登录可访问
         */
        //有序存储
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/login", "anon");
        filterMap.put("/admin/AdminLogin", "anon");
        filterMap.put("/logout", "anon");
        filterMap.put("/captcha", "anon");
        filterMap.put("/noAuth", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/images/**", "anon");
        filterMap.put("/lib/**", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/search","anon");
        // 拦截根目录下所有路径，需要放行的路径必须在之前添加
        filterMap.put("/admin/**", "authc");
        filterMap.put("system/**", "authc");
//      filterMap.put("/admin/**", "anon");

        // 设置过滤规则
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        // 设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/admin");
        shiroFilterFactoryBean.setSuccessUrl("/admin/index");
        // 未授权错误页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");

        return shiroFilterFactoryBean;
    }
    @Bean
    DefaultWebSecurityManager securityManager(AuthRealm authRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        return manager;
    }
    //自定义的Realm
    @Bean
    public AuthRealm getRealm(RetryLimitHashedCredentialsMatcher matcher) {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }
    /*开启AOP注解支持，使 @RequiresPermission注释 生效*/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    //解决aop和shiro注解不兼容问题
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }
}
