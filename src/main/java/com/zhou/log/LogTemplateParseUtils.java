package com.zhou.log;

import com.zhou.log.func.LogCustomFunction;
import com.zhou.log.func.LogSnapshotFunction;
import com.zhou.log.model.LogExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * [日志模板表达解析工具类]
 * 格式必须按照{#demo.name}，以{}作为解析符号，返回其中的SpEL表达式
 * 如果是自定义函数，则为{methodName{#user.id}},其中methodName为自定义函数方法名，#user.id为方法入参的SpEL表达式
 * 注：暂不支持方法嵌套使用
 * @author bihai.zhou@funi365.com
 * @date 2022/11/15 19:33
 **/
public class LogTemplateParseUtils {

    private static final Map<String, LogCustomFunction> customFunctionList = new HashMap();

    private static final Map<String, LogSnapshotFunction> snapshotFunctionList = new HashMap();


    public static List<LogExpression> pares(String content) {
        int length = content.length();
        int startNum = 0;
        int funcStart = 0;
        List<LogExpression> SpELList = new ArrayList<>();
        LogExpression current = null;
        for (int i = 0; i < length; i++) {
            if (content.charAt(i) == LogExpression.LEFT_SPLIT_STR.charAt(0)) {
                //{符号过后的后一位，判断是参数还是自定义函数
                if (current == null) {
                    current = new LogExpression();
                }
                if (content.charAt(i + 1) == LogExpression.SpEL_SIGN_STR.charAt(0)) {
                    if (current.getType() != null) {
                        //说明是方法，已经设置过类型
                        current.setName(content.substring(startNum + 1, i));
                    } else {
                        current.setType(LogExpression.ARG);
                    }
                } else {
                    current.setType(LogExpression.FUNC);
                    funcStart = i;
                }
                startNum = i;
            } else if (content.charAt(i) == LogExpression.RIGHT_SPLIT_STR.charAt(0)) {
                if (current.getType().equals(LogExpression.ARG)) {
                    current.setName(content.substring(startNum + 1, i));
                    current.setCompleteExpression(content.substring(startNum, i + 1));
                } else {
                    current.setArgName(content.substring(startNum + 1, i));
                    current.setCompleteExpression(content.substring(funcStart, i + 2));
                    i++;
                }
                SpELList.add(current);
                current = null;
            }

        }
        return SpELList;
    }

    /**
     * 初始化
     * @param map
     */
    public static void initCustomFunctionList(Map<String, LogCustomFunction> map) {
        for (String name : map.keySet()) {
            LogCustomFunction logCustomFunction = map.get(name);
            String functionName = logCustomFunction.functionName();
            customFunctionList.put(functionName, map.get(name));
        }
    }

    /**
     * 初始化
     * @param map
     */
    public static void initSnapshotFunctionList(Map<String, LogSnapshotFunction> map) {
        for (String name : map.keySet()) {
            LogSnapshotFunction logCustomFunction = map.get(name);
            String functionName = logCustomFunction.functionName();
            snapshotFunctionList.put(functionName, map.get(name));
        }
    }

    /**
     * 根据函数名获取对应的实现
     * @param functionName 函数名
     * @return 实现类
     */
    public static LogCustomFunction getCustomFunction(String functionName) {
        LogCustomFunction logCustomFunction = customFunctionList.get(functionName);
        if (logCustomFunction == null) {
            throw new RuntimeException("函数名匹配错误，请检查是否有该函数");
        }
        return logCustomFunction;
    }

    /**
     * 根据函数名获取对应的实现
     * @param functionName 函数名
     * @return 实现类
     */
    public static LogSnapshotFunction getSnapshotFunction(String functionName) {
        LogSnapshotFunction logSnapshotFunction = snapshotFunctionList.get(functionName);
        if (logSnapshotFunction == null) {
            throw new RuntimeException("函数名匹配错误，请检查是否有该函数");
        }
        return logSnapshotFunction;
    }


    public static void main(String[] args) {
        String str = "通过接口调用更新项目名称{#projectDto.id}，原始名称为{#projectDto.oldId}";
        List<LogExpression> pares = LogTemplateParseUtils.pares(str);
        System.out.println(pares);
    }

}
