package com.zhou.log.event;

import com.zhou.log.model.PaasBusinessLog;
import org.springframework.context.ApplicationEvent;

/**
 * [日志事件]
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/10/26 20:28
 **/
public class PaasLogEvent extends ApplicationEvent {

    private PaasBusinessLog log;
    
    public PaasLogEvent(Object source,PaasBusinessLog log) {
        super(source);
        this.log  = log;
    }

    public PaasBusinessLog getLog() {
        return log;
    }
}
