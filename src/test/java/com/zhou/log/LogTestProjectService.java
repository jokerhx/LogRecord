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

    @PaasLogPoint(operationType = "更新项目",operationLatitude = "项目根据名称更新",content = "{#userId}通过接口调用更新项目名称为{getProjectName{#projectDto.id}}，原始名称为{getProjectName_before{#projectDto.id}}")
    public void updateProject(ProjectDto projectDto,String userId) {
        projectDto.setId("2");
        System.out.println("更新完成");
    }


    @PaasLogPoint(operationType = "新增项目",operationLatitude = "项目根据名称新增项目",content = "我新增了项目")
    public void insert(ProjectDto projectDto,String userId) {
        System.out.println("新增完成");
    }

    @PaasLogPoint(operationType = "错误配置项目",operationLatitude = "项目根据名称新增项目",content = "我#{userId}新增了项目")
    public void errorInsert(ProjectDto projectDto,String userId) {
        System.out.println("新增完成");
    }

    @PaasLogPoint(operationType = "新增项目",operationLatitude = "项目根据名称新增项目",content = "{getUserName{#projectDto}}新增了项目")
    public void insertByUser(ProjectDto projectDto) {
        System.out.println("新增完成");
    }


}
