package com.zhou.log.service;

import com.zhou.log.LogTemplateParseUtils;
import com.zhou.log.func.LogCustomFunction;
import com.zhou.log.func.LogSnapshotFunction;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ServiceLocator implements ApplicationContextAware {

    /**
     * 获取应用上下文并获取相应的接口实现类
     * 初始化自定义函数和快照函数
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //根据接口类型返回相应的所有bean
        Map<String, LogCustomFunction> customFunctionMap = applicationContext.getBeansOfType(LogCustomFunction.class);
        LogTemplateParseUtils.initCustomFunctionList(customFunctionMap);
        Map<String, LogSnapshotFunction> snapshotFunctionMap = applicationContext.getBeansOfType(LogSnapshotFunction.class);
        LogTemplateParseUtils.initSnapshotFunctionList(snapshotFunctionMap);
    }

}

