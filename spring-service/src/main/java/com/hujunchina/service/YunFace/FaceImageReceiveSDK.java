package com.hujunchina.service.YunFace;

import com.hujunchina.service.eventHandler.EventContext;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/9 4:34 下午
 * @Version 1.0
 */
public class FaceImageReceiveSDK {

    public void addFaceImageEventHandler(String uid, String fid, String url){
        FaceImageRequest f = new FaceImageRequest();
        f.setUid(uid);
        f.setFaceId(fid);
        f.setFaceImgUrl(url);
        EventContext e = new EventContext();
        FaceImageReceiveEvent event = new FaceImageReceiveEventImpl();
        event.addFaceImage(f, e);
    }
}
