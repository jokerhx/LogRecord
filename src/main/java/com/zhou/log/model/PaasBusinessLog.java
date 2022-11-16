package com.zhou.log.model;

import lombok.Data;
import org.slf4j.event.Level;

/**
 * [PAAS系统业务日志投递对象]
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/10/25 20:55
 **/
@Data
public class PaasBusinessLog {

    //xx时间，由xx人使用xxIp在xx系统进行xx操作（操作纬度），具体内容为：{业务系统拼接内容（包含业务错误原因）}，结果为：,耗时。

    /**
     * 操作人ID
     */
    private String userId;

    /**
     * 操作人姓名
     */
    private String userName;

    /**
     * 操作日期
     */
    private Long timestamp;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * 系统名
     */
    private String systemCode;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 操作纬度
     */
    private String operationLatitude;

    /**
     * 日志具体内容
     */
    private String content;

    /**
     * 执行耗时
     */
    private Long totalTime;

    /**
     * 方法是否执行成功
     */
    private boolean success;


    /*=====================以下字段为日志详情才需要展示的字段=====================*/


    /**
     * service类名
     */
    private String service;
    /**
     * 方法名
     */
    private String function;

    /**
     * 请求参数
     */
    private String arguments;

    /**
     * 日志等级
     */
    private Level logLevel;

    /**
     * 请求标识
     */
    private String requestId;

}
