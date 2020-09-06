package com.hujunchina.client.bio;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/30 4:39 下午
 * @Version 1.0
 * 最基本的BIO
 */
import com.hujunchina.common.ComUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer implements Runnable{

    private ServerSocket serveSocket = null;

    public BIOServer() throws IOException {
        // 服务者只能绑定0.0.0.0
        serveSocket = new ServerSocket(BIOConfig.getPort());
    }

    @Override
    public void run() {
        while(true) {
            try {
                Socket socket = serveSocket.accept();
                new Thread(()->{
                    Socket socket1 = socket;
                    while(true) {
                        try {
                            OutputStream out = socket.getOutputStream();
                            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
                            pw.println("A msg from server during 10s");
                            pw.flush();
                            ComUtils.getUtils().sleep(1000);
                        } catch (IOException e) {
                            if (socket1!=null) {
                                socket1 = null;
                            }
                            if (serveSocket!=null) {
                                serveSocket = null;
                            }
                        }
                    }
                }).start();
                ComUtils.getUtils().echo("server", "one client come in...");
                //读取
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String msg = null;
                while( (msg=br.readLine()) != ""){
                    ComUtils.getUtils().echo("server", msg);
                }
                //离线
                if(socket.isClosed()) {
                    ComUtils.getUtils().echo("server", "client disconnected");
                }
            } catch (IOException e) {
                System.out.println("Server handler crashed.");
                if (serveSocket!=null) {
                    serveSocket = null;
                }
            }
        }
    }
}
