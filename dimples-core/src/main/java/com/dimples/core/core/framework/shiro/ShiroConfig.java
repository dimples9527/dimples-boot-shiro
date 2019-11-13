package com.dimples.core.core.framework.shiro;

import com.dimples.core.core.util.SysConstant;

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
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/login", "anon");
        //swagger2
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs/**", "anon");
        filterChainDefinitionMap.put("/webjars/springfox-swagger-ui/**", "anon");

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

    @Bean
    public SessionManager sessionManager() {
        ShiroSessionManager mySessionManager = new ShiroSessionManager();
        mySessionManager.setSessionDAO(redisSessionDAO());
        // mySessionManager.setCacheManager(cacheManager());
        mySessionManager.setSessionIdUrlRewritingEnabled(true);
        return mySessionManager;
    }

    /**
     * 使用shiro-redis配置
     * 获取配置文件中配置的Redis
     *
     * @return RedisManager
     */
    @ConfigurationProperties(prefix = "shiro.redis")
    private RedisManager redisManager() {
        return new RedisManager();
    }

    /**
     * redis实现缓存
     *
     * @return RedisCacheManager
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 使用Redis实现 shiro sessionDao
     *
     * @return RedisSessionDAO
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        // redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

//    @Bean
//    public SessionManager sessionManager() {
//        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
//        defaultWebSessionManager.setGlobalSessionTimeout(1800000);
//        defaultWebSessionManager.setSessionIdCookieEnabled(true);
//        defaultWebSessionManager.setSessionIdCookie(simpleCookie());
//        defaultWebSessionManager.setCacheManager(cacheManager());
//        defaultWebSessionManager.setSessionIdUrlRewritingEnabled(true);
//        defaultWebSessionManager.setSessionDAO(sessionDAO());
//        return defaultWebSessionManager;
//    }
//
//    @Bean
//    public SessionDAO sessionDAO() {
//        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
//        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
//        sessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
//        return sessionDAO;
//    }
//
//    @Bean
//    public SimpleCookie simpleCookie() {
//        SimpleCookie simpleCookie = new SimpleCookie();
//        simpleCookie.setName("cn.tycoding.id");
//        simpleCookie.setHttpOnly(true);
//        simpleCookie.setMaxAge(180000);
//        return simpleCookie;
//    }
//
//    /**
//     * Shiro本身只提供了Cahche缓存的接口，并不提供实现类。EhCacheManager是Shiro-Cache的一个实现类
//     *
//     * @return
//     */
//    @Bean
//    public CacheManager cacheManager() {
//        EhCacheManager cacheManager = new EhCacheManager();
//        cacheManager.setCacheManagerConfigFile("classpath:config/shiro-ehcache.xml");
//        return new EhCacheManager();
//    }

}















