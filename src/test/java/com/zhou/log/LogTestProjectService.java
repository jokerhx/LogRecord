package com.zhou.log;

import org.springframework.stereotype.Service;

/**
 * []
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/11/15 18:00
 **/
@Service
public class LogTestProjectService {

    /**
     *
     */
    @PaasLogPoint(operationType = "更新项目",operationLatitude = "项目根据名称更新",content = "{#userId}通过接口调用更新项目名称{getProjectName{#projectDto.id}}，原始名称为{#projectDto.name}")
    public void updateProject(ProjectDto projectDto,String userId) {
        System.out.println("更新完成");
    }


    @PaasLogPoint(operationType = "新增项目",operationLatitude = "项目根据名称新增项目",content = "我新增了项目")
    public void insert(ProjectDto projectDto,String userId) {
        System.out.println("新增完成");
    }


}
