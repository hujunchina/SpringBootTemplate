package com.hujunchina.service.eventHandler;

import lombok.Data;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/8 11:26 上午
 * @Version 1.0
 * 事件处理结果类
 */
public enum  EventResult {
    SUCCESS(200, "事件执行成功"),
    ERROR_ARGS(400, "参数错误"),
    ERROR_DP(500, "dp点找不到");

    private Integer code;

    private String result;

    EventResult(Integer code, String result){
        this.code = code;
        this.result = result;
    }
}
