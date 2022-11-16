package com.zhou.log;

import com.zhou.log.func.LogCustomFunction;
import org.springframework.stereotype.Component;

/**
 * []
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/11/16 10:08
 **/
@Component
public class ProjectNameFunc implements LogCustomFunction {
    /**
     * 函数名称
     *
     * @return 表达式中的函数名必须和此方法返回的相同
     */
    @Override
    public String functionName() {
        return "getProjectName";
    }

    /**
     * 函数实现
     *
     * @param value 入参
     * @return 返回参数，用于日志记录的字符串拼接
     */
    @Override
    public String apply(Object value) {
        String id = (String) value;
        if (id.equals("1")) {
            return "测试项目1";
        }
        return null;
    }
}
