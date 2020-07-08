package com.hujunchina.service.eventHandler;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/8 11:21 上午
 * @Version 1.0
 * 事件处理器的事件内容
 */
@Data
public class EventContext implements Serializable {

    private String context;

    private Integer dp;
}
