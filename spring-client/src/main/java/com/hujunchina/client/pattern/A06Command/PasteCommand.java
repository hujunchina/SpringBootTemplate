package com.hujunchina.client.pattern.A06Command;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/15 5:23 下午
 * @Version 1.0
 */
public class PasteCommand implements ICmd{
    @Override
    public String toJsonDP() {
        return "paste dp";
    }

    @Override
    public void execute() {
        System.out.println("execute paste");
    }
}
