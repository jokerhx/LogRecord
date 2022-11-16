package com.zhou.log.service;

import com.zhou.log.func.LogCustomFunction;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ServiceLocator implements ApplicationContextAware {

    /**
     * 用于保存接口实现类名及对应的类
     */
    private Map<String, LogCustomFunction> functionMap = new HashMap<>();

    /**
     * 获取应用上下文并获取相应的接口实现类
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //根据接口类型返回相应的所有bean
        Map<String, LogCustomFunction> map = applicationContext.getBeansOfType(LogCustomFunction.class);
        for (String name : map.keySet()) {
            LogCustomFunction logCustomFunction = map.get(name);
            String functionName = logCustomFunction.functionName();
            functionMap.put(functionName, map.get(name));
        }
    }

    public Map<String, LogCustomFunction> getMap() {
        return functionMap;
    }


}

