package com.hujunchina.client.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/30 4:56 下午
 * @Version 1.0
 */
public class BIOServerConnectPerThread implements Runnable {

    private ServerSocket serveSocket = null;

    public BIOServerConnectPerThread() throws IOException {
        // 服务者只能绑定0.0.0.0
        serveSocket = new ServerSocket(BIOConfig.getPort());
    }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            try {
                Socket client = serveSocket.accept();
                //每个client，一个读写线程
                new Thread(()->{

                }).start();
            } catch (IOException e) {

            }
        }
    }
}
