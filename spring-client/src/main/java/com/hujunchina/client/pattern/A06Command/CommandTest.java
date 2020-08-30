package com.hujunchina.client.pattern.A06Command;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/15 5:03 下午
 * @Version 1.0
 * 命令行模式（行为类型）：抽象一类操作，隔离业务或GUI，把业务的逻辑实现剥离出来
 * 以一个记事本的复制、粘贴等操作为例，展示这些命令如何抽象出来的。
 * 方式：
 * 声明一个命令抽象类，并提供通用的调用方法
 * 所有的命令具体类都实现方法，进而实现业务逻辑
 */
public class CommandTest {

    public static void main(String[] args) {
        ICmd cmd = new CopyCommand();
        doCommand(cmd);

        cmd = new PasteCommand();
        doCommand(cmd);

        undoCommand();

        cmd = new CutCommand();
        doCommand(cmd);

    }

    public static void doCommand(ICmd cmd){
        cmd.execute();
        CommandHistory.getCommand().push(cmd);
    }

    public static void undoCommand(){
        CommandHistory.getCommand().pop();
    }
}
