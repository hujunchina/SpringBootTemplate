package com.hujunchina.service.eventHandler.event;

import com.hujunchina.service.eventHandler.EventContext;
import com.hujunchina.service.eventHandler.EventResult;
import com.hujunchina.service.eventHandler.MqttEventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/8 11:24 上午
 * @Version 1.0
 */
@Slf4j
public class MqttDoorOpenEventHandler implements MqttEventHandler {
    @Override
    public void handler(EventContext eventContext) {
        log.info("开门事件已处理：dp={}，结果：{}", eventContext.getDp(), EventResult.SUCCESS);
    }
}
