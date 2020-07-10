package com.hujunchina.service.YunFace;

import com.hujunchina.service.eventHandler.EventContext;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/9 4:03 下午
 * @Version 1.0
 * 人脸接收事件
 */
public interface FaceImageReceiveEvent {

    /** 新增人脸*/
    void addFaceImage(FaceImageRequest f, EventContext e);

    /** 更新人脸*/
    void updateFaceImage(FaceImageRequest f);

    /** 删除人脸*/
    void removeFaceImage(RemoveFaceImageRequest r);
}
