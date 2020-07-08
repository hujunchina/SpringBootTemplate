package com.hujunchina.service;

import com.hujunchina.service.eventHandler.EventContext;
import com.hujunchina.service.eventHandler.MqttEventFactory;
import com.hujunchina.service.eventHandler.MqttEventHandler;
import com.hujunchina.service.eventHandler.MqttEventReceiver;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.test.context.TestPropertySource;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/8 11:45 上午
 * @Version 1.0
 */
@Slf4j
public class MqttEventHandlerTest {

    @Test
    public void eventHandlerTest(){
        MqttEventFactory eventFactory = new MqttEventFactory();
        eventFactory.init();

        MqttEventHandler eventHandler = null;
        EventContext eventContext = new EventContext();
        eventContext.setDp(125);
        eventHandler = eventFactory.getEventHandler(eventContext.getDp());
        eventHandler.handler(eventContext);

        eventContext.setDp(126);
        eventHandler = eventFactory.getEventHandler(eventContext.getDp());
        eventHandler.handler(eventContext);
    }

    @Test
    public void mqttReceiveTest(){
        MqttEventReceiver mqttEventReceiver = new MqttEventReceiver();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true) {
//                    mqttEventReceiver.start();
//                }
//            }
//        }).start();
        mqttEventReceiver.start();
    }
}
