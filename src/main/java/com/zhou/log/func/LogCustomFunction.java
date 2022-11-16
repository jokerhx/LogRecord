package com.zhou.log.func;

/**
 * [日志自定义函数接口]
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/11/16 10:05
 **/
public interface LogCustomFunction {

    /**
     * 函数名称
     * @return 表达式中的函数名必须和此方法返回的相同
     */
    String functionName();

    /**
     * 函数实现
     * @param value 入参
     * @return 返回参数，用于日志记录的字符串拼接
     */
    String apply(Object value);

}
