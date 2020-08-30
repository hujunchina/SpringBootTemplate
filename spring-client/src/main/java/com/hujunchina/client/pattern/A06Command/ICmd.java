package com.hujunchina.client.pattern.A06Command;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/15 5:17 下午
 * @Version 1.0
 */
public interface ICmd {
    // 转为DP点
    String toJsonDP();

    // 执行操作
    void execute();
}
