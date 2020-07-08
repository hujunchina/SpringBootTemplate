package com.hujunchina.service.eventHandler;

import com.hujunchina.common.ServiceException;
import com.hujunchina.service.eventHandler.event.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/8 11:32 上午
 * @Version 1.0
 * 事件生产的工厂类
 */
public class MqttEventFactory {

    /** dp点定义*/
    private Integer dp;

    /** 事件处理类*/
    private MqttEventHandler eventHandler;

    /** 事件集合*/
    Map<Integer, MqttEventHandler> handlerMap;

    /** 先加载各个事件处理器*/
    public void init(){
        handlerMap = new HashMap<>();
        // 状态上报
        handlerMap.put(125, new MqttStatusUploadEventHandler());
        // 通行事件
        handlerMap.put(126, new MqttPassOverEventHandler());
        // 开门事件
        handlerMap.put(127, new MqttDoorOpenEventHandler());
        // 云可视对讲事件
        handlerMap.put(128, new MqttCallAppEventHandler());
    }

    /** 根据 dp 点得到相应的事件处理器 */
    // 异常的处理，dp点不存在
    public MqttEventHandler getEventHandler(Integer dp){
        if (!handlerMap.containsKey(dp)) {
            return new MqttExceptionEventHandler();
        }
        return handlerMap.get(dp);
    }

}
