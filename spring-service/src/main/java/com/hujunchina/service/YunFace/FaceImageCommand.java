package com.hujunchina.service.YunFace;

import com.hujunchina.common.ConstTag;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/9 4:22 下午
 * @Version 1.0
 * 涂鸦云方，从app或社区发送人脸添加指令
 */
@Slf4j
public class FaceImageCommand {

    public void addFaceImageCommand(){
        log.info("{} : add commond send", ConstTag.FACE_IMAGE);
        log.info("{} : add user and capture a image", ConstTag.FACE_IMAGE);

        String url = "http://www.hujunchina.com/img/001";
        log.info("{} : save image in {}", ConstTag.FACE_IMAGE, url);

        log.info("{} : 通过mqtt下发指令执行设备SDK中对应的事件处理器", ConstTag.FACE_IMAGE);
        FaceImageReceiveSDK sdk = new FaceImageReceiveSDK();
        sdk.addFaceImageEventHandler("001","001", url);
    }

}
