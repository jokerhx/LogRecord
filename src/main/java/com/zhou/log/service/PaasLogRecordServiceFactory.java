package com.zhou.log.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * [根据配置决定日志的输出方式]
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/10/26 20:42
 **/
@Service
public class PaasLogRecordServiceFactory {

    /**
     * 获取配置的日志记录策略
     */
    private String strategy;

    @Resource
    private KafkaPaasLogRecordService kafkaPaasLogRecordService;

    public List<PaasLogRecordService> get() {
        List<PaasLogRecordService> list = new ArrayList<>();
        list.add(kafkaPaasLogRecordService);
        return list;
    }

}
