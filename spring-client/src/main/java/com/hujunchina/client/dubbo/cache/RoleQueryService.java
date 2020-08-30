package com.hujunchina.client.dubbo.cache;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/6 11:51 上午
 * @Version 1.0
 */
public class RoleQueryService implements QueryService{

    private String version = "0";

    @Override
    public String query(String condition) {
        return condition+version;
    }
}
