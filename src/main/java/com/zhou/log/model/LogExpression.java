package com.zhou.log.model;

import lombok.Data;

/**
 * [日志表达式]
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/11/15 20:05
 **/
@Data
public class LogExpression {

    public static final String LEFT_SPLIT_STR = "{";
    public static final String RIGHT_SPLIT_STR = "}";

    public static final String SpEL_SIGN_STR = "#";
    public static final String ARG = "arg";
    public static final String FUNC = "func";

    /**
     * 名称
     */
    private String name;

    /**
     * 当前是参数还是自定义函数
     * {@link LogExpression#FUNC}
     * {@link LogExpression#ARG}
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
