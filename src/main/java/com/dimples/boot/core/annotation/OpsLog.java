package com.dimples.boot.core.annotation;

import com.dimples.boot.core.eunm.OpsLogTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义日志注解
 * Inherited 说明子类可以继承父类中的该注解
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/8
 */
@Documented
@Inherited
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface OpsLog {

    String value() default "";

    OpsLogTypeEnum[] type() default {};

}














