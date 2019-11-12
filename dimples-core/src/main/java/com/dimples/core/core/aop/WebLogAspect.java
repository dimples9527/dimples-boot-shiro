package com.dimples.core.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * AOP的方式统一处理网页请求日志<br>
 *
 * @author zhongyj
 * @date 2019/7/1
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {

    /**
     * 拦截controller下面的所有类的所有方法<br>
     */
    @Pointcut("execution(public * com.dimples.boot.biz.controller.*.*(..))")
    public void webLog() {
    }

    /**
     * 使用AOP前置通知拦截请求参数信息<br>
     *
     * @param joinPoint JoinPoint
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("========================= 请求start ============================");
        // 接收到请求，记录请求内容 记录 最多半年数据迁移 云备份 NoSql 数据库
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            log.info("name:{},value:{}", name, request.getParameter(name));
        }
    }

    /**
     * 后置通知<br>
     *
     * @param ret Object
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        log.info("RESPONSE : " + ret);
        log.info("========================= 请求end ============================");
    }
}
