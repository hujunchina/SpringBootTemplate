package com.hujunchina.service.YunFace;

import com.hujunchina.common.ConstTag;
import com.hujunchina.service.eventHandler.EventContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/9 4:27 下午
 * @Version 1.0
 * 设备端实现的接口
 */
@Slf4j
public class FaceImageReceiveEventImpl implements FaceImageReceiveEvent{

    @Override
    public void addFaceImage(FaceImageRequest f, EventContext e) {
        String url = f.getFaceImgUrl();
        log.info("{} : get url {}", ConstTag.FACE_IMAGE_DEVICE, url);
        log.info("{} : save image done !", ConstTag.FACE_IMAGE_DEVICE);
    }

    @Override
    public void updateFaceImage(FaceImageRequest f) {

    }

    @Override
    public void removeFaceImage(RemoveFaceImageRequest r) {

    }
}
