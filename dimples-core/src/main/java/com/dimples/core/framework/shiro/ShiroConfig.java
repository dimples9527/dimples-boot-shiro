package com.dimples.core.framework.shiro;

import com.dimples.core.properties.DimplesProperties;
import com.dimples.core.properties.ShiroProperties;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * Shiro权限配置类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/13
 */
@Configuration
public class ShiroConfig {

    private DimplesProperties properties;

    @Autowired
    public ShiroConfig(DimplesProperties properties) {
        this.properties = properties;
    }

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password:}")
    private String password;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.database:0}")
    private int database;

    /**
     * shiro 中配置 redis 缓存
     *
     * @return RedisManager
     */
    private RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host + ":" + port);
        if (StringUtils.isNotBlank(password)) {
            redisManager.setPassword(password);
        }
        redisManager.setTimeout(timeout);
        redisManager.setDatabase(database);
        return redisManager;
    }

    private RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        // 解决登出提示需要 id 字段异常
        redisCacheManager.setPrincipalIdFieldName("userId");
        return redisCacheManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        ShiroProperties shiro = properties.getShiro();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl(shiro.getLoginUrl());
        shiroFilterFactoryBean.setSuccessUrl(shiro.getSuccessUrl());
        shiroFilterFactoryBean.setUnauthorizedUrl(shiro.getUnauthorizedUrl());

        //配置拦截器链，注意顺序
        //      anon: 无需认证即可访问
        //      authc: 必须认证才可访问
        //      user: 如果使用rememberMe的功能才可以访问
        //      perms: 该资源必须得到资源权限才可以访问
        //      role: 该资源必须得到资源权限才可以访问
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // TODO: 2020/1/29  开发时开放所有页面资源
//        filterChainDefinitionMap.put("/web/**", "anon");
        filterChainDefinitionMap.put("/sys/logout", "anon");
        filterChainDefinitionMap.put("/sys/login", "anon");
        filterChainDefinitionMap.put("/sys/captcha", "anon");
        filterChainDefinitionMap.put("/web/login", "anon");
        filterChainDefinitionMap.put("/test/**", "anon");
        filterChainDefinitionMap.put("/error", "anon");
        filterChainDefinitionMap.put("/user/register", "anon");
        //放行静态资源
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/layui/**", "anon");
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
    public SecurityManager securityManager(ShiroRealm shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置 rememberMeCookie
        securityManager.setRememberMeManager(rememberMeManager());
        // 配置 shiro session管理器
        securityManager.setSessionManager(sessionManager());
        // 配置 缓存管理类 cacheManager
        securityManager.setCacheManager(cacheManager());
        // 配置 SecurityManager，并注入 shiroRealm
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }

    /**
     * rememberMe cookie 效果是重开浏览器后无需重新登录
     *
     * @return SimpleCookie
     */
    private SimpleCookie rememberMeCookie() {
        // 设置 cookie 名称，对应 login.html 页面的 <input type="checkbox" name="rememberMe"/>
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置 cookie 的过期时间，单位为秒，这里为一天
        cookie.setMaxAge(properties.getShiro().getCookieTimeout());
        return cookie;
    }

    /**
     * cookie管理对象
     *
     * @return CookieRememberMeManager
     */
    private CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie 加密的密钥
        String encryptKey = "dimples_shiro_key";
        byte[] encryptKeyBytes = encryptKey.getBytes(StandardCharsets.UTF_8);
        String rememberKey = Base64Utils.encodeToString(Arrays.copyOf(encryptKeyBytes, 16));
        cookieRememberMeManager.setCipherKey(Base64.decode(rememberKey));
        return cookieRememberMeManager;
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
     * 用于开启 Thymeleaf 中的 shiro 标签的使用
     *
     * @return ShiroDialect shiro 方言对象
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * session 管理对象
     *
     * @return DefaultWebSessionManager
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<>();
        listeners.add(new ShiroSessionListener());
        // 设置 session超时时间
        sessionManager.setGlobalSessionTimeout(properties.getShiro().getSessionTimeout() * 1000L);
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

}















