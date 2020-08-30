package com.hujunchina.client.pattern.A06Command;

import java.util.List;
import java.util.Stack;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/15 5:25 下午
 * @Version 1.0
 */
public class CommandHistory {

    private Stack<ICmd> history = new Stack<>();

    public static CommandHistory commandHistory = null;

    private CommandHistory(){}

    public static CommandHistory getCommand(){
        if (commandHistory == null) {
            commandHistory = new CommandHistory();
        }
        return commandHistory;
    }

    public void push(ICmd cmd){
        history.push(cmd);
    }

    public void pop(){
        history.pop();
    }

}
