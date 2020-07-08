package com.hujunchina.service.eventHandler.event;

import com.hujunchina.service.eventHandler.EventContext;
import com.hujunchina.service.eventHandler.EventResult;
import com.hujunchina.service.eventHandler.MqttEventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/8 2:17 下午
 * @Version 1.0
 */
@Slf4j
public class MqttExceptionEventHandler implements MqttEventHandler {
    @Override
    public void handler(EventContext eventContext) {
        log.info("不存在的DP点 {}，指令无法执行，{}", eventContext.getDp(), EventResult.ERROR_DP);
    }
}
