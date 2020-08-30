package com.hujunchina.client.pattern.A03TemplateMethod;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/6 9:38 上午
 * @Version 1.0
 * 提供定位服务接口，SDK提供
 */
public interface LocationService {

    String location(Long latitude, Long longitude);
}
