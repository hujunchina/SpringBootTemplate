package com.hujunchina.atop;


import com.alibaba.fastjson.JSON;
import com.hujunchina.common.HttpUtilsX;
import com.hujunchina.manager.domain.TokenUserDTO;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/7 3:08 下午
 * @Version 1.0
 * 定义一个http访问的私有类给SDK使用（edge-ipc）
 */
public class AtopFacade {

    private final String AtopUrl = "http://www.hujunchina.com/?device_id=%s&status=%s";

    private AtopFacade atopFacade;

    /** 实例化一个atop*/
    public AtopFacade getAtopFacade() {
        return atopFacade;
    }

    public void setAtopFacade(AtopFacade atopFacade) {
        this.atopFacade = atopFacade;
    }

    /** 上报数据信息*/
    public void uploadStatus(String deviceId, String status){
        String url = String.format(AtopUrl, deviceId, status);
        String result = HttpUtilsX.doPost(url, null);
        TokenUserDTO user = JSON.parseObject(result, TokenUserDTO.class);
        // TODO
    }
}
