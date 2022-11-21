package com.zhou.log;

import com.zhou.log.func.LogCustomFunction;
import org.springframework.stereotype.Service;

/**
 * [获取用户姓名]
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/11/18 15:17
 **/
@Service
public class UserNameFunc implements LogCustomFunction  {


    /**
     * 函数名称
     *
     * @return 表达式中的函数名必须和此方法返回的相同
     */
    @Override
    public String functionName() {
        return "getUserName";
    }

    /**
     * 函数实现
     *
     * @param value 入参
     * @return 返回参数，用于日志记录的字符串拼接
     */
    @Override
    public String apply(Object value) {
        if (value instanceof ProjectDto){
            ProjectDto projectDto = (ProjectDto) value;
            if (projectDto.getId().equals("1")) {
                return "项目1的用户名";
            }
        }
        return null;
    }
}
