package com.hujunchina.client.base;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/1 11:36 下午
 * @Version 1.0
 * 字符流有两个顶层抽象类Reader和Writer，派生出字符串、缓冲区、管道、文件、过滤、转换等流
 * 一个字符等于两个字节,但是转为byte时是占3个长度
 * 计算机一次读的字节称为编码单位(英文名叫CodeUnit)，也叫码元，byte中汉字占3个马元
 * https://juejin.im/post/5c847cb5f265da2dd63929c2
 * UTF-8中，如果首字节以1110开头，肯定是三字节编码(3个码元)。
 * 字节流在操作时本身不会用到缓冲区（内存），是文件本身直接操作的。\n
 * 字符流在操作时是使用了缓冲区，通过缓冲区再操作文件。
 * 除了纯文本数据文件使用字符流以外，其他文件类型都应该使用字节流方式。
 */
public class IOStream {
    public static void main(String[] args) {
//        fileByteStream();
        pathFile();
    }

    //文件字节流
    public static void fileByteStream() throws IOException {
        FileInputStream in = null;
        FileOutputStream out = null;
        try{
            in = new FileInputStream("readme.txt");
            out = new FileOutputStream("outagain.txt");
            int c;
            while( (c=in.read()) != -1){
                out.write(c);
            }
        } finally {
            if(in!=null){
                in = null;
            }
            if (out != null) {
                out = null;
            }
        }
    }

    //文件字符流
    public static void fileWordStream() throws IOException{
        FileReader in = null;
        FileWriter out = null;
        try{
            in = new FileReader("readme.txt");
            out = new FileWriter("outagainchar.txt");
            int c;
            while((c=in.read())!=-1){
                out.write(c);
            }
        }finally {
            if(in!=null){
                in = null;
            }
            if(out!=null){
                out = null;
            }
        }
    }

    //文件字符缓冲流，可以读取一行
    public static void fileLineStream() throws IOException{
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            in = new BufferedReader(new FileReader("readme.txt"));
            out = new PrintWriter(new FileWriter("outagainline.txt"));
            String line;
            while((line=in.readLine())!=null){
                out.println(line);
            }
        }finally {
            if(in!=null){ in = null; }
            if(out!=null){ out=null; }
        }
    }

    //文件流作为Scanner的输入
    public static void scanFile() throws IOException{
        Scanner s= null;
        try{
            s = new Scanner(new BufferedReader(new FileReader("readme.txt")));
            s.useDelimiter(",");
            while(s.hasNext()){
                System.out.println(s.next());
            }
        }finally { if(s!=null){ s=null; } }
    }

    //测试path
    public static void pathFile(){
        Path path = Paths.get(".");
        System.out.println(path.toAbsolutePath());
    }

    //    管道流，管道流的主要作用是可以进行两个线程间的通信
//    如果要进行管道通信，则必须把 PipedOutputStream 连接在 PipedInputStream 上。
//    为此，PipedOutputStream 中提供了 connect() 方法
    public static void PipedStream() throws IOException {

        Send sender = new Send();
        Receive receiver = new Receive();

        sender.getConnect(receiver.putPipe());
        new Thread(sender).start();
        new Thread(receiver).start();

//   每个汉字转为字节是不一样的，有的是3个有的是4个
        System.out.println("息".length() + " " + "息".getBytes().length);  // 1 3
        System.out.println("一".length() + " " + "一".getBytes().length);  // 1 3
        System.out.println("a".length() + " " + "a".getBytes().length);  // 1 1
        System.out.println("这是来自另一个线程的信".getBytes().length);  // 33
        System.out.println("这是来自另一个线程的信息".getBytes().length);  // 36
    }

    //    合并流, 类似linux中的管道 | > <
    public static void SequenceStream() throws IOException {
        InputStream is1 = new FileInputStream("temp1.log");
        InputStream is2 = new FileInputStream("temp2.log");
        SequenceInputStream sis = new SequenceInputStream(is1, is2);

        int temp = 0; // 接收内容
        OutputStream os = new FileOutputStream("temp3.logt");
        while ((temp = sis.read()) != -1) { // 循环输出
            os.write(temp); // 保存内容
        }

        sis.close(); // 关闭合并流
        is1.close(); // 关闭输入流1
        is2.close(); // 关闭输入流2
        os.close(); // 关闭输出流
    }

    static class Send implements Runnable{
        PipedOutputStream pos = new PipedOutputStream();;

        public void getConnect(PipedInputStream pis) throws IOException {
            pos.connect(pis);
        }

        @Override
        public void run() {
            try {
                pos.write("这是来自另一个线程的信息".getBytes());
                pos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Receive implements Runnable{
        PipedInputStream pis = new PipedInputStream();
        public PipedInputStream putPipe(){
            return pis;
        }
        @Override
        public void run() {
            try {
//                12个汉字，转为byte是多长？ 一个汉字3个字节，12个一共36个字节,  存储在数组中占了36个字节
                byte[] ret = new byte[36];
                pis.read(ret);
                System.out.println(ret.length+" "+ Arrays.toString(ret));
                System.out.println(new String(ret));
                pis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //文件读写, RandomAccessFile会打开文件从头开始写，然后覆盖文件内容，严格按照字符长度来读写
    public static void readFile(String filename) throws IOException {
        //写
//        String filename = "randomaccessfile.txt";
        File file = new File(filename);
        RandomAccessFile rdf = new RandomAccessFile(file, "rw");

        String name = null;
        int age = 0;

        name = "zhangsan"; // 字符串长度为8
        age = 30; // 数字的长度为4
        rdf.writeBytes(name); // 将姓名写入文件之中
        rdf.writeInt(age); // 将年龄写入文件之中

        name = "lisi    "; // 字符串长度为8
        age = 31; // 数字的长度为4
        rdf.writeBytes(name); // 将姓名写入文件之中
        rdf.writeInt(age); // 将年龄写入文件之中

        name = "wangwu  "; // 字符串长度为8
        age = 32; // 数字的长度为4
        rdf.writeBytes(name); // 将姓名写入文件之中
        rdf.writeInt(age); // 将年龄写入文件之中

        rdf.close(); // 关闭
        //读
        File f = new File(filename);    // 指定要操作的文件
        RandomAccessFile rdf2 = null;        // 声明RandomAccessFile类的对象
        rdf2 = new RandomAccessFile(f, "r");// 以只读的方式打开文件
        String name2 = null;
        int age2 = 0;
        byte b[] = new byte[8];    // 开辟byte数组
        // 读取第二个人的信息，意味着要空出第一个人的信息
        rdf2.skipBytes(12);        // 跳过第一个人的信息
        for (int i = 0; i < b.length; i++) {
            b[i] = rdf2.readByte();    // 读取一个字节
        }
        name2 = new String(b);    // 将读取出来的byte数组变为字符串
        age2 = rdf2.readInt();    // 读取数字
        System.out.println("第二个人的信息 --> 姓名：" + name + "；年龄：" + age);
        // 读取第一个人的信息
        rdf2.seek(0);    // 指针回到文件的开头
        for (int i = 0; i < b.length; i++) {
            b[i] = rdf2.readByte();    // 读取一个字节
        }
        name2 = new String(b);    // 将读取出来的byte数组变为字符串
        age2 = rdf2.readInt();    // 读取数字
        System.out.println("第一个人的信息 --> 姓名：" + name + "；年龄：" + age);
        rdf.skipBytes(12);    // 空出第二个人的信息
        for (int i = 0; i < b.length; i++) {
            b[i] = rdf2.readByte();    // 读取一个字节
        }
        name = new String(b);    // 将读取出来的byte数组变为字符串
        age = rdf2.readInt();    // 读取数字
        System.out.println("第三个人的信息 --> 姓名：" + name + "；年龄：" + age);
        rdf2.close();                // 关闭
    }

    //文件通道，NIO不仅有网络传输的socket，也有文件传输的通道
    //NIO是非阻塞IO，面向Buffer缓冲区，以块为单位传输数据，支持双向读写
    //虽然是面向缓冲区的，但是还是面向字节而不是字符
    //FileChannel是阻塞的！
    public static void fastCopy(String filename, String dist) throws IOException {

        FileInputStream fis = new FileInputStream(filename);
        FileChannel fcin = fis.getChannel();

        FileOutputStream fos = new FileOutputStream(dist);
        FileChannel fcout= fis.getChannel();

        //通道读写需要先设置缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (true) {
            /* 从输入通道中读取数据到缓冲区中 */
            int r = fcin.read(buffer);
            /* read() 返回 -1 表示 EOF */
            if (r == -1) {
                break;
            }
            /* 切换读写 */
            buffer.flip();
            /* 把缓冲区的内容写入输出文件中 */
            fcout.write(buffer);
            /* 清空缓冲区 */
            buffer.clear();
        }
    }

    //回答NIO，从四点出发：非阻塞、Buffer缓冲区、Channel通道、Selector选择器
    //NIO需要解决的最根本的问题就是存在于BIO中的两个阻塞，分别是等待连接时的阻塞和等待数据时的阻塞
    //在真实NIO中，并不会在Java层上来进行一个轮询，而是将轮询的这个步骤交给我们的操作系统来进行，
    //他将轮询的那部分代码改为操作系统级别的系统调用（select函数，在linux环境中为epoll），
    //在操作系统级别上调用select函数，主动地去感知有数据的socket。
    //NIO的IO行为还是同步的,在IO操作准备好时，业务线程得到通知，
    //接着就由这个线程自行进行IO操作，IO操作本身是同步的,
    public static void nioTest() throws IOException {
        NIOServer nioServer = new NIOServer();
        new Thread(nioServer).start();
        new Thread(new NIOOperator(nioServer.getSelectorClient())).start();
        for (int i = 0; i < 5; i++) {
            new Thread(new SocketClient("client"+i)).start();
        }
    }
    static class NIOServer implements Runnable{
        //        // 1. serverSelector负责轮询是否有新的连接，服务端监测到新的连接之后，不再创建一个新的线程，
//    // 而是直接将新连接绑定到clientSelector上，这样就不用 IO 模型中 1w 个 while 循环在死等
//        轮询服务端accept客户端
        Selector selectorServer;
        //        轮询客户端IO读写
        Selector selectorClient;
        //       服务端socket类似ServerSocket
        ServerSocketChannel serverSocketChannel = null;
        //        获得一个socket来操作
        Socket socket = null;

        public NIOServer() throws IOException {
            selectorServer = Selector.open();
            selectorClient = Selector.open();
//            初始化服务端通道
            serverSocketChannel = ServerSocketChannel.open();
//            绑定通道内socket端口
            serverSocketChannel.socket().bind(new InetSocketAddress(60000));
//            设置为非阻塞
            serverSocketChannel.configureBlocking(false);
//            注册到选择器上
            serverSocketChannel.register(selectorServer, SelectionKey.OP_ACCEPT);
        }
        public Selector getSelectorClient(){
            System.out.println("服务端启动");
            return selectorClient;
        }
        @Override
        public void run(){
            while(true) {
                // 监测是否有新的连接，这里的1指的是阻塞的时间为 1ms
                try {
                    if(selectorServer.select(1) > 0){
//                        选择器获得有读写信号的channel
                        Set<SelectionKey> selectionKeys = selectorServer.selectedKeys();
                        for(SelectionKey key : selectionKeys){
                            if( key.isAcceptable()){
                                // (1) 每来一个新连接，不需要创建一个线程，而是直接注册到clientSelector
                                SocketChannel socketChannel = ((ServerSocketChannel)key.channel()).accept();
                                socketChannel.configureBlocking(false);
                                socketChannel.register(selectorClient, SelectionKey.OP_READ);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class NIOOperator implements Runnable{
        Selector selectorClient;
        public NIOOperator(Selector selectorClient){
            this.selectorClient = selectorClient;
        }
        @Override
        public void run() {
//            select返回后还要再次遍历，来获知是哪一个请求有数据。
            while(true){
                try {
                    // (2) 批量轮询是否有哪些连接有数据可读，这里的1指的是阻塞的时间为 1ms
                    if( selectorClient.select(1) > 0){
                        Set<SelectionKey> selectedKeys = selectorClient.selectedKeys();
                        for(SelectionKey key : selectedKeys){
                            if(key.isReadable()){
                                SocketChannel socketChannel = (SocketChannel) key.channel();
//                                缓冲区
                                ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                                // (3) 面向 Buffer
                                socketChannel.read(buffer);
//                                转为写
                                buffer.flip();
                                System.out.println(Charset.defaultCharset().newDecoder().decode(buffer).toString());
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static String getDate(){
        return new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date());
    }
    public static void Msg(String msg){
        System.out.println(msg);
    }
    //   客户端
    static class SocketClient implements Runnable{
        Socket socket = null;
        String name = null;
        PrintWriter out = null;
        public SocketClient(String name) throws IOException {
            this.name = name;
            socket = new Socket("127.0.0.1", 60000);
            out = new PrintWriter(socket.getOutputStream(), true);
            Msg("客户端"+name+"启动");
        }
        @Override
        public void run() {
            while(true){
                out.println(getDate()+" MSG from "+name);
//                Msg("客户端已写入数据");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Msg("client sleep interrupted");
                }
            }
        }
    }

    /**
     * AIO也就是NIO2。在Java7中引入了NIO的改进版NIO2,它是异步非阻塞的IO模型。
     * 异步IO是基于事件和回调机制实现的，也就是应用操作之后会直接返回，不会堵塞在那里，
     * 当后台处理完成，操作系统会通知相应的线程进行后续的操作。
     * Netty之前也尝试使用过AIO，不过又放弃了
     * Netty的出现很大程度上改善了JDK原生NIO所存在的一些让人难以忍受的问题。
     */

    public static void readData(String file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        DataOutputStream dos = new DataOutputStream(fos);
        int i = 10;
        boolean b = true;
        char c = '胡';
        String str = "hujun";
        double pi = 3.14;
        dos.writeInt(i);
        dos.writeBoolean(b);
        dos.writeChar(c);
        dos.writeUTF(str);
        dos.writeDouble(pi);
        dos.close();


        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);
        int i2 = dis.readInt();
        boolean b2 = dis.readBoolean();
        char c2 = dis.readChar();
//        can not read string
        String str2 = dis.readUTF();
//        String str = "can not read string";
        double pi2 = dis.readDouble();
        System.out.format("%d,%b,%c,%s,%f%n", i2, b2, c2, str2, pi2);
    }
}
