package com.hujunchina.client.pattern.A06Command;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/15 5:24 下午
 * @Version 1.0
 */
public class UndoCommand implements ICmd{
    @Override
    public String toJsonDP() {
        return "undo dp";
    }

    @Override
    public void execute() {
        System.out.println("execute undo");
    }
}
