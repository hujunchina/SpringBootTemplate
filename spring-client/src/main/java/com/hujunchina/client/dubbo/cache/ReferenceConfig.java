package com.hujunchina.client.dubbo.cache;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/6 11:50 上午
 * @Version 1.0
 * 泛型小容器
 */
public class ReferenceConfig<T> {

    private Class interfaceClass;

    private String version;

    public Class getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
