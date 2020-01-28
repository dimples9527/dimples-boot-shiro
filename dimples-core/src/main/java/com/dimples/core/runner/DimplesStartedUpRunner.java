package com.dimples.core.runner;

import com.dimples.core.helper.RedisHelper;
import com.dimples.core.properties.DimplesProperties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/21
 */
@Slf4j
@Component
public class DimplesStartedUpRunner implements ApplicationRunner {

    public static final String DEV = "dev";
    public static final String WINDOWS = "windows";

    private ConfigurableApplicationContext context;
    private DimplesProperties dimplesProperties;
    private RedisHelper redisHelper;

    @Autowired
    public DimplesStartedUpRunner(ConfigurableApplicationContext context, DimplesProperties dimplesProperties, RedisHelper redisHelper) {
        this.context = context;
        this.dimplesProperties = dimplesProperties;
        this.redisHelper = redisHelper;
    }

    @Value("${server.port:8080}")
    private String port;
    @Value("${server.servlet.context-path:}")
    private String contextPath;
    @Value("${spring.profiles.active}")
    private String active;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            // 测试 Redis连接是否正常
            redisHelper.hasKey("dimples_test");
        } catch (Exception e) {
            log.error(" ____   __    _   _ ");
            log.error("| |_   / /\\  | | | |");
            log.error("|_|   /_/--\\ |_| |_|__");
            log.error("                        ");
            log.error("Dimples启动失败，{}", e.getMessage());
            log.error("Redis连接异常，请检查Redis连接配置并确保Redis服务已启动");
            // 关闭 Dimples
            context.close();
        }
        if (context.isActive()) {
            InetAddress address = InetAddress.getLocalHost();
            String urlBase = String.format("http://%s:%s", address.getHostAddress(), port);
            String url = urlBase;
            String loginUrl = dimplesProperties.getShiro().getLoginUrl();
            if (StringUtils.isNotBlank(contextPath)) {
                url += contextPath;
            }
            if (StringUtils.isNotBlank(loginUrl)) {
                url += loginUrl;
            }
            log.info(" __    ___   _      ___   _     ____ _____  ____ ");
            log.info("/ /`  / / \\ | |\\/| | |_) | |   | |_   | |  | |_  ");
            log.info("\\_\\_, \\_\\_/ |_|  | |_|   |_|__ |_|__  |_|  |_|__ ");
            log.info("                                                      ");
            log.info("DIMPLES 权限系统启动完毕，地址：{}", url);
            log.info("DIMPLES 文档地址：{}", urlBase + "/doc.html");

            boolean auto = dimplesProperties.isAutoOpenBrowser();
            if (auto && StringUtils.equalsIgnoreCase(active, DEV)) {
                String os = System.getProperty("os.name");
                // 默认为 windows时才自动打开页面
                if (StringUtils.containsIgnoreCase(os, WINDOWS)) {
                    // 开发时使用
                    url = urlBase + "/web/index";
                    //使用默认浏览器打开系统登录页
                    Runtime.getRuntime().exec("cmd  /c  start " + url);
                }
            }
        }
    }
}

















