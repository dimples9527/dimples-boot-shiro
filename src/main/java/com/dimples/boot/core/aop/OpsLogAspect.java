package com.dimples.boot.core.aop;

import com.dimples.boot.core.annotation.OpsLog;
import com.dimples.boot.core.eunm.OpsLogTypeEnum;
import com.dimples.boot.core.util.HttpContextUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志记录切面类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/8
 */
@Slf4j
@Component
@Aspect
public class OpsLogAspect {

    private static final String LOG_CONTENT = "[类名]:%s <br/>[方法]:%s <br>[参数]:%s <br/>[  IP ]:%s";

    @Pointcut(value = "@annotation(com.dimples.boot.core.annotation.OpsLog)")
    public void opsLogAnnotation() {
    }

    @Around("opsLogAnnotation()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {

        // 先执行业务
        Object result = point.proceed();

        try {
            handle(point);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }

        return result;
    }

    /**
     * 创建日志记录
     * 1、写入到数据库
     * 2、写入到文件中
     *
     * @param point ProceedingJoinPoint
     * @throws Exception Exception
     */
    private void handle(ProceedingJoinPoint point) throws Exception {
        // 获取拦截的方法名
        Signature signature = point.getSignature();
        MethodSignature methodSignature;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        String methodName = currentMethod.getName();
        // 获取注解中的内容
        OpsLog opsLog = currentMethod.getAnnotation(OpsLog.class);
        String value = opsLog.value();
        OpsLogTypeEnum[] opsLogTypeEnums = opsLog.type();
        String opsLogTypeName = null;
        if (opsLogTypeEnums.length > 0) {
            opsLogTypeName = opsLogTypeEnums[0].name();
        }
        // 获取请求的IP
        HttpServletRequest request = HttpContextUtil.getRequest();
        // 构建一些基础信息
        String content = buildContent(point, methodName, request);
        log.info("执行的操作：{}, 类型：{}, 信息: {}", value, opsLogTypeName, content);
    }

    /**
     * 获取方法，请求等信息
     *
     * @param joinPoint  ProceedingJoinPoint
     * @param methodName String
     * @param request    HttpServletRequest
     * @return String
     */
    private String buildContent(ProceedingJoinPoint joinPoint, String methodName, HttpServletRequest request) {
        String className = joinPoint.getTarget().getClass().getName();
        Object[] params = joinPoint.getArgs();
        StringBuilder bf = new StringBuilder();
        if (params != null && params.length > 0) {
            Enumeration<String> paraNames = request.getParameterNames();
            while (paraNames.hasMoreElements()) {
                String key = paraNames.nextElement();
                bf.append(key).append("=");
                bf.append(request.getParameter(key)).append("&");
            }
            if (bf.toString().isEmpty()) {
                bf.append(request.getQueryString());
            }
            log.info("REQUEST PARAMS :" + bf.toString());
        }
        return String.format(LOG_CONTENT, className, methodName, bf.toString(), HttpContextUtil.getIp());
    }

}


















