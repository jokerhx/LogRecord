package com.zhou.log.service;


import com.zhou.log.model.PaasBusinessLog;

/**
 * [日志记录]
 *
 * @author bihai.zhou@funi365.com
 * @date 2022/10/26 20:38
 **/
public interface PaasLogRecordService {

    /**
     * 记录日志
     * @param businessLog
     */
    void log(PaasBusinessLog businessLog);

}
