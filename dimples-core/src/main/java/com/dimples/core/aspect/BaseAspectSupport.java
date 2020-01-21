package com.dimples.core.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * aspect基础操作类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/23
 */
public abstract class BaseAspectSupport {

    /**
     * 处理方法
     *
     * @param point ProceedingJoinPoint
     * @return Method
     */
    protected Method resolveMethod(ProceedingJoinPoint point) {
        MethodSignature methodSignature;
        Signature signature = point.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;
        // 获取使用注解的类
        Class<?> targetClass = point.getTarget().getClass();
        // 获取方法
        Method method = getDeclaredMethod(targetClass, signature.getName(), methodSignature.getMethod().getParameterTypes());
        if (method == null) {
            throw new IllegalStateException("无法解析目标方法: " + methodSignature.getMethod().getName());
        }
        return method;
    }

    private Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getDeclaredMethod(superClass, name, parameterTypes);
            }
        }
        return null;
    }

}
