package com.zhou.log.func;

/**
 * [日志快照定义接口]
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/11/20 23:42
 **/
public interface LogSnapshotFunction {

    /**
     * 函数名称
     * @return 表达式中的函数名必须和此方法返回的相同，保证唯一性
     */
    String functionName();

    /**
     * 快照函数实现
     * @param value 入参
     * @return 此方法返回值会被放入缓存中，可在此次方法调用中使用，包括自定义函数
     */
    Object snapshotApply(Object value);

}
