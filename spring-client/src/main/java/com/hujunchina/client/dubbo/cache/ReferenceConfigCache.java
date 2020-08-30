package com.hujunchina.client.dubbo.cache;

import javax.security.auth.login.Configuration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/6 11:45 上午
 * @Version 1.0
 * 如何设计缓存？
 */
public class ReferenceConfigCache {

    public static ReferenceConfigCache referenceConfigCache;

    // Object为了通用泛型，用T报错
    private Map<String, Object> cacheMap = new HashMap<>();

    private ReferenceConfigCache(){}

    public static ReferenceConfigCache getCache(){
        referenceConfigCache = new ReferenceConfigCache();
        return referenceConfigCache;
    }


    public <T> void destory(ReferenceConfig<T> config){
        String key = config.getClass().getName()+config.getVersion();
        cacheMap.remove(key);
    }

    // 缓存，需要一个容器来缓存
    // 以服务 Group、接口、版本为缓存的 Key
    // 一个通用的get，因为config里面放的不一定是service，可以是任何object
    public <T> T get(ReferenceConfig<T> config) throws IllegalAccessException, InstantiationException {
        String key = config.getClass().getName()+config.getVersion();
        // 可有美酒按返回哦 诶紧张
        if (cacheMap.get(key) == null) {
            // 对Class类型通过反射生成实例
            T t = (T) config.getInterfaceClass().newInstance();
            cacheMap.put(key, t);
            System.out.println(key);
        }
        return (T)cacheMap.get(key);
    }
}
