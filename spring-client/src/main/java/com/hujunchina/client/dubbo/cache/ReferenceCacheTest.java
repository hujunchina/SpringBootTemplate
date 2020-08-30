package com.hujunchina.client.dubbo.cache;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/6 12:04 下午
 * @Version 1.0
 */
public class ReferenceCacheTest {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ReferenceConfig<QueryService> reference = new ReferenceConfig<>();
        reference.setInterfaceClass(RoleQueryService.class);
        reference.setVersion("1.0.0");

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        QueryService service = cache.get(reference);
        System.out.println(service.query("hujun"));

        // 更改版本
        reference.setVersion("1.0.0");
        service = cache.get(reference);
        System.out.println(service.query("hujun1"));
//        cache.<QueryService>destory(reference);
        //因为类型能够被jvm推断，所以不用显式声明<T>了
        cache.destory(reference);
    }
}
