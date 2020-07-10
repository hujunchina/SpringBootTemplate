package com.hujunchina.service.YunFace;

import lombok.Data;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/9 4:04 下午
 * @Version 1.0
 * 人脸响应类
 */
@Data
public class FaceImageRequest {

    /** 用户id*/
    private String uid;

    /** 人脸id*/
    private String faceId;

    /** 人脸url*/
    private String faceImgUrl;

}
