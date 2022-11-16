package com.zhou.log;

import com.zhou.log.model.LogFunction;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * []
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/11/15 17:59
 **/
@SpringBootTest
public class LogTest {
    @Resource
    private LogTestProjectService logTestProjectService;


    @Test
    public void updateProject() {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId("1");
        projectDto.setName("测试项目");
        logTestProjectService.updateProject(projectDto,"用户id");
    }


    @Test
    public void insert() {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId("1");
        projectDto.setName("测试项目");
        logTestProjectService.insert(projectDto,"用户id");
    }

}
