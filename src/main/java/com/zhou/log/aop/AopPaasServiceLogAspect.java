package com.zhou.log.aop;

import com.alibaba.fastjson.JSON;
import com.zhou.event.EventPublisher;
import com.zhou.log.LogTemplateParseUtils;
import com.zhou.log.PaasLogPoint;
import com.zhou.log.event.PaasLogEvent;
import com.zhou.log.func.LogCustomFunction;
import com.zhou.log.model.LogExpression;
import com.zhou.log.model.PaasBusinessLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * [切面，对@PaasLogPoint注解的方法进行日志记录]
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/10/26 20:08
 **/
@Aspect
@Slf4j
@Component
public class AopPaasServiceLogAspect {

    @Resource
    private EventPublisher eventPublisher;


    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    private EvaluationContext context = new StandardEvaluationContext();

    private String appCode;

    private Map<String, MethodLocation> map = new HashMap<>();

    private SpelExpressionParser parser = new SpelExpressionParser();

    @PostConstruct
    public void init() {
        appCode =  "测试app";
    }

    /**
     * 仅拦截用spring service注解声明的类的@PaasLogPoint方法
     */
    @Pointcut("@annotation(com.zhou.log.PaasLogPoint)")
    public void annotationPointCut() {
    }


    @Around("annotationPointCut()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {

        Signature signature = pjp.getSignature();
        Class serviceClass = pjp.getSignature().getDeclaringType();
        String location = serviceClass + "." + signature.getName();
        if (!map.containsKey(location)) {
            map.put(location, createMethodLocation(pjp, signature, serviceClass));
        }

        MethodLocation methodLocation = map.get(location);
        String content = methodLocation.methodPoint.content();
        //解析方法参数
        this.bindParam(methodLocation.method, pjp.getArgs());

        if (!CollectionUtils.isEmpty(methodLocation.expressionList)) {
            //需要进行表达式的处理
            List<LogExpression> list = methodLocation.expressionList;
            for (LogExpression logExpression : list) {
                boolean b = this.setResult(logExpression, true);
                if (b) {
                    //得到结果过后将表达式的内容进行替换
                    content = content.replace(logExpression.getCompleteExpression(), logExpression.getResult());
                }
            }
        }

        PaasBusinessLog aopServiceLog = createPaasBusinessLog(methodLocation);
        try {
            aopServiceLog.setArguments(toString(pjp.getArgs()));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
            aopServiceLog.setArguments("参数序列化失败");
        }
        long start = System.currentTimeMillis();
        boolean success = false;
        try {
            Object obj = pjp.proceed();
            success = true;
            //执行在目标方法执行后的函数
            if (!CollectionUtils.isEmpty(methodLocation.expressionList)) {
                //需要进行表达式的处理
                List<LogExpression> list = methodLocation.expressionList;
                for (LogExpression logExpression : list) {
                    boolean b = this.setResult(logExpression, false);
                    if (b) {
                        //得到结果过后将表达式的内容进行替换
                        content = content.replace(logExpression.getCompleteExpression(), logExpression.getResult());
                    }
                }
            }
            return obj;
        } catch (Exception e) {
            //todo 可以根据某些特定的错误类型，将其记录入日志中
            e.printStackTrace();
            throw e;
        } finally {
            //收集content
            if (methodLocation.methodPoint != null && !StringUtils.isEmpty(methodLocation.methodPoint.content())) {
                //获取方法的参数值
                aopServiceLog.setContent(content);
            }
            aopServiceLog.setSuccess(success);
            aopServiceLog.setTotalTime(System.currentTimeMillis() - start);
            eventPublisher.publish(new PaasLogEvent(this, aopServiceLog));
        }
    }

    /**
     * 构建方法上所需数据
     */
    private MethodLocation createMethodLocation(ProceedingJoinPoint pjp, Signature signature, Class serviceClass) {
        MethodLocation methodLocation = new MethodLocation();
        methodLocation.serviceName = serviceClass.getName();
        methodLocation.methodName = signature.getName();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        PaasLogPoint methodLogPoint = targetMethod.getAnnotation(PaasLogPoint.class);
        if (methodLogPoint != null) {
            methodLocation.operationType = methodLogPoint.operationType();
            methodLocation.operationLatitude = methodLogPoint.operationLatitude();
            methodLocation.expressionList = LogTemplateParseUtils.pares(methodLogPoint.content());
        }
        methodLocation.method = targetMethod;
        methodLocation.methodPoint = methodLogPoint;
        return methodLocation;
    }

    /**
     * 创建日志对象
     */
    private PaasBusinessLog createPaasBusinessLog(MethodLocation methodLocation) {
        PaasBusinessLog aopServiceLog = new PaasBusinessLog();
        aopServiceLog.setFunction(methodLocation.methodName);
        aopServiceLog.setService(methodLocation.serviceName);
        aopServiceLog.setOperationType(methodLocation.operationType);
        aopServiceLog.setOperationLatitude(methodLocation.operationLatitude);
        aopServiceLog.setSystemCode(appCode);
        //todo 设置用户和请求相关属性
//        PaasProtocol paasProtocol = PaasProtocolContext.get();
        if (methodLocation.methodPoint != null) {
            aopServiceLog.setLogLevel(methodLocation.methodPoint.level());
        }
        aopServiceLog.setTimestamp(System.currentTimeMillis());
        return aopServiceLog;
    }

    //JSON序列化参数对象
    private String toString(Object[] args) {
        if (args.length == 0) {
            return "";
        }
        if (args.length == 1) {
            if (args[0].getClass().isPrimitive() || args[0] instanceof String) {
                return (String) args[0];
            }
        }
        return JSON.toJSONString(args);
    }

    private static class MethodLocation {
        private String serviceName;
        private String methodName;

        /**
         * 操作类型
         */
        private String operationType;

        /**
         * 操作纬度
         */
        private String operationLatitude;
        private PaasLogPoint methodPoint;
        private Method method;

        /**
         * 内容中包含的表达式集合
         */
        private List<LogExpression> expressionList;
    }


    /**
     * 将方法的参数名和参数值绑定
     *
     * @param method 方法，根据方法获取参数名
     * @param args   方法的参数值
     * @return
     */
    private void bindParam(Method method, Object[] args) {
        //获取方法的参数名
        String[] params = discoverer.getParameterNames(method);

        //将参数名与参数值对应起来
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
    }

    /**
     * 设置表达式对应的结果
     * @param logExpression 表达式
     * @param before 是否在当前方法调用前
     * @return 是否设置结果
     */
    private boolean setResult(LogExpression logExpression,boolean before) {
        if (LogExpression.ARG.equals(logExpression.getType())) {
            if (before) {
                //参数在目标方法执行前统一执行
                //SpEL表达式值获取
                SpelExpression spelExpression = parser.parseRaw(logExpression.getName());
                String value = (String) spelExpression.getValue(context);
                logExpression.setResult(value);
            } else {
                return false;
            }
        } else {
            //自定义函数调用
            //获取对应的实现类
            LogCustomFunction logCustomFunction = LogTemplateParseUtils.getCustomFunction(logExpression.getName());
            if (logCustomFunction.executeBefore() != before) {
                return false;
            }
            //参数解析
            String argName = logExpression.getArgName();
            Expression expression = parser.parseExpression(argName);
            Object value = expression.getValue(context);
            String apply = logCustomFunction.apply(value);
            logExpression.setResult(apply);
        }
        return true;
    }
}
