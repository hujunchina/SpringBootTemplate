package com.hujunchina.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/30 3:58 下午
 * @Version 1.0
 * 工具类（无法分类的都放这里）
 * 使用单例模式，直接调用相应的方法即可
 */
public class ComUtils {

    //静态属性，在类加载机制中的解释环节会放入到方法区，默认是null
    private static ComUtils utils = null;

    private ComUtils(){}

    //静态方法，invokestatic 指令加载到方法区中
    public static ComUtils getUtils(){
        if(utils==null){  //utils是类，判断真假不能用 !utils
            utils = new ComUtils();
        }
        return utils;
    }

    /**
     * 输出类似log的指令，包括类名，和信息
     * @param self 类名或方法名
     * @param msg  输出信息
     */
    public void echo(String self, String msg){
        String time = new SimpleDateFormat("YYYY-MM-DD HH:mm:SSS").format(new Date());
        System.out.format("[%s] %s %s%n", time, self, msg);
    }

    /**
     * 睡眠方法，直接在该方法中处理异常，调用者可以美化代码格式
     * @param time 睡眠时间，单位毫秒
     */
    public void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

