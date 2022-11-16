package com.zhou.log;

import com.zhou.log.model.LogFunction;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.List;
import java.util.UUID;

/**
 * []
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/11/16 9:46
 **/
public class SpELTest {
    @Test
    public void spelTest() {



        SpelExpressionParser parser = new SpelExpressionParser();
        String test = "通过接口调用更新项目名称{#root.id}}，原始名称为#{root.name}";
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(UUID.randomUUID().toString());
        projectDto.setName("测试项目");
        projectDto.setId("12131233");
        Expression expression = parser.parseExpression("#root.id");
        System.out.println(expression.getValue(projectDto));

    }

}
