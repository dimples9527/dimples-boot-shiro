package com.dimples.common.annotation;

import com.dimples.common.util.ShiroUtil;
import com.dimples.core.aspect.BaseAspectSupport;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.exception.BizException;
import com.dimples.core.util.AddressUtil;
import com.dimples.core.util.HttpContextUtil;
import com.dimples.monitor.service.OpsLogService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private ObjectMapper objectMapper;
    private OpsLogService opsLogService;

    @Autowired
    public OpsLogAspect(ObjectMapper objectMapper, OpsLogService opsLogService) {
        this.objectMapper = objectMapper;
        this.opsLogService = opsLogService;
    }

    @Pointcut(value = "@annotation(com.dimples.common.annotation.OpsLog)")
    public void opsLogAnnotation() {
    }

    @Around("opsLogAnnotation()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
        // 先执行业务
        long start = System.currentTimeMillis();
        Object result = point.proceed();
        try {
            handleLog(point, start);
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
    private void handleLog(ProceedingJoinPoint point, long start) {
        com.dimples.monitor.po.OpsLog opsLogPo = new com.dimples.monitor.po.OpsLog();

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
        // 存储操作日志
        this.save(opsLogPo, point, currentMethod, opsLog, start);
        // 存储
        this.opsLogService.save(opsLogPo);
    }

    private void save(com.dimples.monitor.po.OpsLog opsLogPo, ProceedingJoinPoint point, Method currentMethod, OpsLog opsLog, long start) {
        // 设置用户
        opsLogPo.setUsername(ShiroUtil.getCurrentUser().getUsername());
        // 设置方法
        opsLogPo.setMethod(point.getTarget().getClass().getName() + "." + currentMethod.getName() + "()");
        // 设置参数
        Object[] args = point.getArgs();
        // 设置操作
        opsLogPo.setOperation(opsLog.value());
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(currentMethod);
        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            params = handleParams(params, args, Arrays.asList(paramNames));
            opsLogPo.setParams(params.toString());
        }
        // 设置IP
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) attributes;
        String ip = servletRequestAttributes != null ? servletRequestAttributes.getRequest().getRemoteAddr() : StringUtils.EMPTY;
        opsLogPo.setIp(ip);
        // 设置操作时间
        opsLogPo.setTime(((System.currentTimeMillis() - start) / 1000.00) + "秒");
        opsLogPo.setCreateTime(new Date());
        // 设置地址
        opsLogPo.setLocation(AddressUtil.getCityInfo(ip));

    }

    @SuppressWarnings("all")
    private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) {
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Map) {
                    Set set = ((Map) args[i]).keySet();
                    List<Object> list = new ArrayList<>();
                    List<Object> paramList = new ArrayList<>();
                    for (Object key : set) {
                        list.add(((Map) args[i]).get(key));
                        paramList.add(key);
                    }
                    return handleParams(params, list.toArray(), paramList);
                } else {
                    if (args[i] instanceof Serializable) {
                        Class<?> aClass = args[i].getClass();
                        try {
                            aClass.getDeclaredMethod("toString", new Class[]{null});
                            // 如果不抛出 NoSuchMethodException 异常则存在 toString 方法 ，安全的 writeValueAsString ，否则 走 Object的 toString方法
                            params.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i]));
                        } catch (NoSuchMethodException e) {
                            params.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i].toString()));
                        }
                    } else if (args[i] instanceof MultipartFile) {
                        MultipartFile file = (MultipartFile) args[i];
                        params.append(" ").append(paramNames.get(i)).append(": ").append(file.getName());
                    } else {
                        params.append(" ").append(paramNames.get(i)).append(": ").append(args[i]);
                    }
                }
            }
        } catch (Exception ignore) {
            params.append("参数解析失败");
        }
        return params;
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


















