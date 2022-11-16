package com.zhou.log.service;

import com.zhou.log.model.PaasBusinessLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * [将日志数据记录到MQ，然后使用logstash监听MQ，最后推送至ES]
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/10/26 20:40
 **/
@Slf4j
@Service
public class KafkaPaasLogRecordService implements PaasLogRecordService {
/*
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "test.log";


    @KafkaTopic(TOPIC)
    public void send(PaasBusinessLog msg) {
        kafkaTemplate.send(TOPIC, msg);
    }*/

    /**
     * 记录日志
     *
     * @param businessLog
     */
    @Override
    public void log(PaasBusinessLog businessLog) {
        log.info("当前日志为:{}", businessLog);
//        this.send(businessLog);
    }

}
