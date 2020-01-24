package com.dimples.core.aspect;

import com.dimples.core.annotation.OpsLog;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.exception.BizException;
import com.dimples.core.util.HttpContextUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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
public class OpsLogAspect extends BaseAspectSupport {

    private static final String LOG_CONTENT = "[ 类名 ] ==>%s \n[ 方法 ] ==> %s \n[ 参数 ] ==> %s \n[ IP  ] ==> %s ";

    @Pointcut(value = "@annotation(com.dimples.core.annotation.OpsLog)")
    public void opsLogAnnotation() {
    }

    @Around("opsLogAnnotation()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
        // 先执行业务
        Object result = point.proceed();
        try {
            handleLog(point);
        } catch (Exception e) {
            throw new BizException("日志记录出错", e);
        }
        return result;
    }

    /**
     * 创建日志记录
     * 1、写入到数据库
     * 2、写入到文件中
     *
     * @param point ProceedingJoinPoint
     */
    private void handleLog(ProceedingJoinPoint point) {
        Method currentMethod = resolveMethod(point);
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
        log.info("\n =============================== {} ========================================\n" +
                "{} \n" +
                "======================================================================================", value, content);
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
                bf.append(key).append("=").append(request.getParameter(key)).append("&");
            }
            if (bf.toString().isEmpty()) {
                bf.append(request.getQueryString());
            } else {
                bf.deleteCharAt(bf.length() - 1);
            }
        }
        return String.format(LOG_CONTENT, className, methodName, bf.toString(), HttpContextUtil.getIp());
    }


}


















