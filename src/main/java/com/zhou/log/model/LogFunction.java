package com.zhou.log.model;

import lombok.Data;

/**
 * [日志自定义函数]
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/11/15 20:05
 **/
@Data
public class LogFunction {

    /**
     * 名称
     */
    private String name;

    /**
     * 当前是参数还是自定义函数
     */
    private String type;

    /**
     * 参数表达式
     */
    private String argName;

    /**
     * 完整表达式，用于字符串替换
     */
    private String completeExpression;

    /**
     * 执行表达式过后的结果
     */
    private String result;

}
