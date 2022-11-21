package com.zhou.log;

import com.zhou.log.func.LogCustomFunction;
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
     * 操作具体内容说明,此字段支持SpEL表达式和自定义函数
     * 自定义函数需要实现指定接口{@link LogCustomFunction}
     * 格式必须按照{#demo.name}，以{}作为解析符号，返回其中的SpEL表达式
     * 如果是自定义函数，则为{methodName{#user.id}},其中methodName为自定义函数方法名，#user.id为方法入参的SpEL表达式
     * 注：暂不支持方法嵌套使用
     */
    String content() default "";

    /**
     * 日志等级
     */
    Level level() default Level.INFO;

}
