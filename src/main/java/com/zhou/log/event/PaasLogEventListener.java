package com.zhou.log.event;

import com.zhou.log.service.PaasLogRecordService;
import com.zhou.log.service.PaasLogRecordServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * [日志事件监听]
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/10/26 20:34
 **/
@Service
@Slf4j
public class PaasLogEventListener implements ApplicationListener<PaasLogEvent> {

    @Resource
    private PaasLogRecordServiceFactory factory;

    @Override
    public void onApplicationEvent(PaasLogEvent paasLogEvent) {
        List<PaasLogRecordService> paasLogRecordServices = factory.get();
        for (PaasLogRecordService paasLogRecordService : paasLogRecordServices) {
            paasLogRecordService.log(paasLogEvent.getLog());
        }

    }
}
