package com.zhou.log;

import org.slf4j.event.Level;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * [记录系统方法调用日志]
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/10/24 20:16
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PaasLogPoint {

    /**
     * 操作类型
     */
    String operationType() default "";

    /**
     * 操作纬度
     */
    String operationLatitude() default "";

    /**
     * 操作具体内容说明
     */
    String content() default "";

    /**
     * 日志等级
     */
    Level level() default Level.INFO;

}
