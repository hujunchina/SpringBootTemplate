package com.hujunchina.service;

import com.hujunchina.common.ServiceResponseCode;
import lombok.Data;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/1 1:45 下午
 * @Version 1.0
 */
@Data
public class ServiceResponse<T> extends BaseResponse{

    private ServiceResponseCode code;
    private Long time;
    private T data;

    /** 设置范型返回结果 (就是包装了一下 T， 加入了 code）*/
    public static <T> ServiceResponse<T> asSuccess(ServiceResponseCode code, T data) {
         ServiceResponse<T> response = new ServiceResponse<>();
         response.setCode(code);
         response.setTime(System.currentTimeMillis());
         response.setData(data);
         return response;
    }

    public static <T> ServiceResponse<T> asFail(ServiceResponseCode code, T data) {
        ServiceResponse<T> response = new ServiceResponse<>();
        response.setCode(code);
        response.setTime(System.currentTimeMillis());
        response.setData(data);

        return response;
    }
}
