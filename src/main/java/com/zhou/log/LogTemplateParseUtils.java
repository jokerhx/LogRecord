package com.zhou.log;

import com.zhou.log.model.LogFunction;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * [日志模板表达解析工具类]
 * 格式必须按照{#demo.name}，以{}作为解析符号，返回其中的SpEL表达式
 * 如果是自定义函数，则为{methodName{#user.id}},其中methodName为自定义函数方法名，#user.id为方法入参的SpEL表达式
 * 注：暂不支持方法嵌套使用
 * @author bihai.zhou@funi365.com
 * @date 2022/11/15 19:33
 **/
public class LogTemplateParseUtils {

    public static final String LEFT_SPLIT_STR = "{";
    public static final String RIGHT_SPLIT_STR = "}";

    public static final String SpEL_SIGN_STR = "#";
    public static final String ARG = "arg";
    public static final String FUNC = "func";

    public static List<LogFunction> pares(String content) {
        int length = content.length();
        int startNum = 0;
        int funcStart = 0;
        List<LogFunction> SpELList = new ArrayList<>();
        LogFunction current = null;
        for (int i = 0; i < length; i++) {
            if (content.charAt(i) == LEFT_SPLIT_STR.charAt(0)) {
                //{符号过后的后一位，判断是参数还是自定义函数
                if (current == null) {
                    current = new LogFunction();
                }
                if (content.charAt(i + 1) == SpEL_SIGN_STR.charAt(0)) {
                    if (current.getType() != null) {
                        //说明是方法，已经设置过类型
                        current.setName(content.substring(startNum + 1, i));
                    } else {
                        current.setType(ARG);
                    }
                } else {
                    current.setType(FUNC);
                    funcStart = i;
                }
                startNum = i;
            } else if (content.charAt(i) == RIGHT_SPLIT_STR.charAt(0)) {
                if (current.getType().equals(ARG)) {
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

    public static void main(String[] args) {
        String str = "通过接口调用更新项目名称{#projectDto.id}，原始名称为{#projectDto.oldId}";
        List<LogFunction> pares = LogTemplateParseUtils.pares(str);
        System.out.println(pares);
    }

}
