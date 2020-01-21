package com.dimples.core.framework.shiro;

import com.dimples.core.util.SysConstant;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro权限配置类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/13
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/403");

        //配置拦截器链，注意顺序
        //      anon: 无需认证即可访问
        //      authc: 必须认证才可访问
        //      user: 如果使用rememberMe的功能才可以访问
        //      perms: 该资源必须得到资源权限才可以访问
        //      role: 该资源必须得到资源权限才可以访问
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/test/**", "anon");
        filterChainDefinitionMap.put("/error", "anon");
        //放行静态资源
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        //swagger2
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/api-docs-ext/**", "anon");
        filterChainDefinitionMap.put("/api-docs/**", "anon");
        // actuator
        filterChainDefinitionMap.put("/actuator/**", "anon");
        // Druid
        filterChainDefinitionMap.put("/druid/**", "anon");

        filterChainDefinitionMap.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public Realm realm(HashedCredentialsMatcher matcher) {
        ShiroRealm realm = new ShiroRealm();
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置 rememberMeCookie
        securityManager.setRememberMeManager(rememberMeManager());
        // 配置 shiro session管理器
        securityManager.setSessionManager(sessionManager());
        // 配置 缓存管理类 cacheManager
        // securityManager.setCacheManager(cacheManager());
        // 配置 SecurityManager，并注入 shiroRealm
        securityManager.setRealm(realm(hashedCredentialsMatcher()));
        return securityManager;
    }

    @Bean
    public SimpleCookie rememberCookie() {
        return new SimpleCookie("rememberMe");
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(SysConstant.ALGORITHNAME);
        credentialsMatcher.setHashIterations(SysConstant.HASHNUM);
        return credentialsMatcher;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解支持
     * 比如：@RequireRoles @RequireUsers
     *
     * @param securityManager SecurityManager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * shiro 的session管理器
     *
     * @return SessionManager
     */
    @Bean
    public SessionManager sessionManager() {
        ShiroSessionManager mySessionManager = new ShiroSessionManager();
        mySessionManager.setSessionIdUrlRewritingEnabled(true);
        return mySessionManager;
    }

}















