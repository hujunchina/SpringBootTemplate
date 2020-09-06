package com.hujunchina.client.bio;

import com.hujunchina.common.ComUtils;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/30 4:14 下午
 * @Version 1.0
 *
 */
public class BIOClient implements Runnable{

    //全局变量，加载到方法区中，不放在栈中，类实例化，会放在堆中
    private Socket socket = null;

    //初始化
    public BIOClient() throws IOException {
        socket = new Socket(BIOConfig.getHost(), BIOConfig.getPort());
        ComUtils.getUtils().echo(this.getClass().getName(), "Started");
    }

    //发送文件
    @Override
    public void run() {
        int modCount = 0; //多线程计数标识
        Scanner in = new Scanner(System.in);
        System.out.println("Please input some msg to send, using q or Q to quit");
        // 接收
        new Thread(()->{
            Socket socket1 = this.socket; //使用新socket，什么远离？
            try {
                InputStream is = socket1.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String res = null;
                while((res=br.readLine())!="") {
                    ComUtils.getUtils().echo(Thread.currentThread().getName(), res);
                }
            } catch (IOException e) {
                System.out.println("Client receive handler crashed.");
                try {
                    socket1.close();
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        }).start();

        //发送
        while(true) {
            try {
                OutputStream out = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
                if (modCount==0) {
                    pw.println("A msg from BIO client"+modCount++);
                } else {
                    String msg = in.nextLine();
                    if (msg.equals("q") || msg.equals("Q")) {
                        ComUtils.getUtils().echo(Thread.currentThread().getName(), "Exit!");
                        break;
                    }
                }
                pw.flush();
                ComUtils.getUtils().echo(Thread.currentThread().getName(), "Client has sent a msg");
                ComUtils.getUtils().sleep(1000);
            } catch (IOException e) {
                System.out.println("Client send handler crashed.");
            }
        }
    }
}
