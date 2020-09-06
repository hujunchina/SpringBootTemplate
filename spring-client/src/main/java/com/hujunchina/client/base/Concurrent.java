package com.hujunchina.client.base;

import com.hujunchina.common.ComUtils;
import com.mysql.cj.Session;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/30 5:32 下午
 * @Version 1.0
 * 多线程并发编程
 */
public class Concurrent {

    //测试
    public static void main(String[] args) {
        //counter();
        //safeLock();
        consumerProducer();
    }

    /**
     * 演示多线程下对临界资源修改，非原子操作的问题
     * 无法演示了，lambda不能c++
     * 使用concurrent包下的atomicInteger原子类操作
     */
    public static void counter(){
        AtomicInteger comValue = new AtomicInteger();
        new Thread(()->{
            for(int i=0; i<10; i++) {
                comValue.getAndIncrement();
                System.out.println(comValue);
            }
        }).start();
        new Thread(()->{
            for(int i=0; i<10; i++) {
                comValue.getAndDecrement();
                System.out.println(comValue);
            }
        }).start();
    }

    //演示可重入锁机制，使用ReentrantLock类
    public static void safeLock(){
        //定义临界资源
        class Friend{
            private String name;
            private Lock lock = new ReentrantLock();
            Friend(String name){this.name = name;}
            //即将发生扔球，所以要演示加锁过程
            public boolean impendingSend(Friend send){
                boolean sendLock = false; //发送的
                boolean receiveLock = false; //接收的，默认当前类
                try {
                    //Acquires the lock only if it is free at the time of invocation.
                    receiveLock = lock.tryLock();  //当前接收加锁
                    sendLock = send.lock.tryLock(); //发送的加锁
                } finally {
                    if (!(receiveLock&&sendLock)){  //只要有一个没加锁成功，立刻释放锁
                        if(receiveLock){lock.unlock();}
                        if(sendLock){send.lock.unlock();}
                    }
                }
                return receiveLock && sendLock;
            }
            //扔球, 接收一个发送的对象，当前对象得到球，才能扔回去
            public void send(Friend send){
                if(impendingSend(send)){ //加锁成功，表示扔球成功
                    try{
                        ComUtils.getUtils().echo(name, send.name + " send to me " + name);
                        send.sendBack(this);
                    } finally { //发送完，要解锁
                        lock.unlock();
                        send.lock.unlock();
                    }
                }else{ //球在我方手里，当前扔给send
                    ComUtils.getUtils().echo(name, "I was already send to him "+ send.name);
                }
            }
            //扔回去
            public void sendBack(Friend send){
                ComUtils.getUtils().echo(name, send.name + " send back to me " + name);
            }
        }
        //定义一个循环发送的类
        Friend AAA = new Friend("AAAA");
        Friend BBB = new Friend("BBBB");
        new Thread(()->{
            for(;;){ComUtils.getUtils().sleep(1000);AAA.send(BBB);}
        }).start();
        new Thread(()->{
            for(;;){ComUtils.getUtils().sleep(900);BBB.send(AAA);}
        }).start();
    }

    public static void consumerProducer(){
        class HotDog {
            private String msg;
            private boolean empty = true; //资源是否还有，默认无
            public synchronized String getMsg(){
                //如果为空，就等待，直到被生产set通知打断
                while(empty){ try{ wait(); } catch (InterruptedException e){} }
                empty = true;  //消费了msg，设置为空
                notifyAll();   //通知生产
                return msg;
            }
            public synchronized void setMsg(String msg){
                //还有资源，就等待，直到被消费get通知打断
                while(!empty){ try { wait(); } catch (InterruptedException e){} }
                empty = false;  //生产资源，设置为满
                this.msg = msg; //资源赋值
                notifyAll();    //通知消费
            }
        }
        //临界资源，只能声明一个
        HotDog hotDog = new HotDog();
        Random random = new Random();
        //生产者
        new Thread(()->{
            for (int i=0; i<10; i++){
                hotDog.setMsg("hujun"+i);
                System.out.println("producer: "+hotDog.msg);
                ComUtils.getUtils().sleep(random.nextInt(3000));
            }
            hotDog.setMsg("done");
        }).start();
        //消费者
        new Thread(()->{
            for(String msg = hotDog.getMsg(); !msg.equals("done");msg=hotDog.getMsg()){
                System.out.println("consumer: "+msg);
                ComUtils.getUtils().sleep(random.nextInt(3000));
            }
            System.out.println("consumer: "+hotDog.msg);
        }).start();
    }

    //线程池测试
    public static void threadPool(){
        //直接使用封装好的服务，Executors
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(()->{
            System.out.println(1);
        });
        //自己手动声明，无法执行 Callable 接口
        SynchronousQueue<Runnable> queue = new SynchronousQueue<>();
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(10,
                20,
                100,
                TimeUnit.MILLISECONDS,
                queue);
        tpe.execute(()->{
            System.out.println(2);
        });
    }

    /**
     * 饿汉式是立即加载的方式，无论是否会用到这个对象，都会加载。
     * 如果在构造方法里写了性能消耗较大，占时较久的代码，比如建立与数据库的连接，那么就会在启动的时候感觉稍微有些卡顿。
     * 为什么立即加载？因为程序加载运行的时候，会提前把静态变量（语句）加载到内存（静态段）中
     */
    public static class SingleHungry {
        //    1.私有化构造方法使得该类无法在外部通过new 进行实例化
        private SingleHungry(){}
        //    2.声明一个私有的静态对象
        private static SingleHungry instance = new SingleHungry();
        //    3.声明一个静态方法，返回一个对象，由于只有一个
        public static SingleHungry getInstance(){
            return instance;
        }

        public static int cnt = 0;
        public void say(){
            System.out.println("single hungry style");
            System.out.format("Call %d times%n", cnt++);
        }
    }
    /**
     * 懒汉式，是延迟加载的方式，只有使用的时候才会加载。 并且有线程安全的考量(鉴于同学们学习的进度，暂时不对线程的章节做展开)。
     * 使用懒汉式，在启动的时候，会感觉到比饿汉式略快，因为并没有做对象的实例化。 但是在第一次调用的时候，会进行实例化操作，感觉上就略慢。
     */
    public static class SingleLazy {
        private SingleLazy(){}

        private static SingleLazy instance= null;
        //    3.只有在为空的情况下会创建一个新对象，而不在多个类中创建
        public static SingleLazy getInstance(){
            if(instance == null){
                instance = new SingleLazy();
            }
            return instance;
        }
    }

    /**
     * 竞态条件（RaceCondition）：当两个线程竞争同一资源时，如果对资源的访问顺序敏感，就称存在竞态条件。\n
     * 临界区（CriticalSections）：导致竞态条件发生的代码区称作临界区。,
     * https://dunwu.github.io/javacore/concurrent/java-concurrent-introduction.html,
     * 使用基于冲突检测的乐观并发策略：先进行操作，如果没有其它线程争用共享数据，那操作就成功了，
     * 否则采取补偿措施（不断地重试，直到成功为止）。这种乐观的并发策略的许多实现都不需要将线程阻塞，
     * 因此这种同步操作称为非阻塞同步。
     * 为什么说乐观锁需要硬件指令集的发展才能进行？因为需要操作和冲突检测这两个步骤具备原子性。
     * 而这点是由硬件来完成，如果再使用互斥同步来保证就失去意义了。
     * 保证数据的一致性：原子性、可见性、有序性（volatile和Synchronized）
     */
    /*
    这类乐观锁指令常见的有：

    测试并设置（Test-and-Set）
    获取并增加（Fetch-and-Increment）
    交换（Swap）
    比较并交换（CAS）
    加载链接、条件存储（Load-linked / Store-Conditional）
    Java 典型应用场景：J.U.C 包中的原子类（基于 Unsafe 类的 CAS 操作）
     */
    public static void testThread(){
        //        new Thread(new TaskTest()).start();

//        new Thread(new ITC.Producer("hujun1")).start();
//        new Thread(new ITC.Producer("hujun2")).start();
//        new Thread(new ITC.Customer("ljh1")).start();
//        new Thread(new ITC.Customer("ljh2")).start();
//        new Thread(new ITC.Customer("ljh3")).start();
//        new Thread(new ITC.Customer("ljh4")).start();

        ThreadClose threadClose = new ThreadClose();
        new Thread(threadClose).start();
        sleep(500);
        threadClose.close();

        new Thread(new JoinTest()).start();
    }

    public static void innerThread(){
        new Thread(() -> {
            Thread.currentThread().setName("inner-thread");
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+"==hujun");
//                1到10,默认为5
                System.out.println(Thread.currentThread().getPriority());
            }
        }).start();
    }

    static class TaskTest extends TimerTask {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+"==task");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                System.out.println(sdf.format(new Date()));
            }
        }
    }

    public static void testSpeed(){
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        System.out.println("spend time : "+(start-end));
    }

    //    开关变量设为volatile，让修改立即可见
//    让其他线程控制该线程，开关是给其他线程使用是的
    static class ThreadClose implements Runnable{
        public volatile boolean flag = false;
        @Override
        public void run() {
            while(!flag){
                System.out.println("Thread runing");
                sleep(500);
            }
        }
        public void close(){
            flag = true;
        }
    }
    /*
    每一个 Java 对象都有一个与之对应的 监视器（monitor）
    每一个监视器里面都有一个 对象锁 、一个 等待队列、一个 同步队列
    线程间通信
     */
    static class ITC{

        public static final int shared = 10;
        public static final Queue<Integer> queue = new LinkedList<>();

        static class Producer implements Runnable{
            String name;
            public Producer(String name){
                this.name = name;
            }
            @Override
            public void run() {
                synchronized (queue){
                    while(queue.size()>=shared){
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            queue.notifyAll();
                        }
                    }
                    for (int i = 0; i < 12; i++) {
                        queue.offer(1);
                        System.out.println(name+"炸了一个鸡腿，现在还有"+queue.size()+"个鸡腿");
                        queue.notifyAll();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        static class Customer implements Runnable{
            String name;
            public Customer(String name){
                this.name = name;
            }
            @Override
            public void run() {
                synchronized (queue){
                    while(queue.size()<=0){
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            queue.notifyAll();
                        }
                    }
                    queue.poll();
                    System.out.println(name+"吃了一个鸡腿，还有"+queue.size()+"个鸡腿");
                    queue.notifyAll();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }

    //    B创建A执行，A调用join，然后A等待B执行完，最后A再执行
//    主->B->B-----B------B-----B---end
//        ->A----->A.join--waiting--A......A.....end
    static class JoinTest implements Runnable{
        @Override
        public void run() {
            new Thread(()->{
                for (int i = 0; i < 20; i++) {
                    System.out.println("辅线程执行"+i);
                    sleep(500);
                    if(i==10){
                        try {
                            Thread.currentThread().join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            sleep(1000);
            for (int i = 0; i < 10; i++) {
                System.out.println("主程执行"+i);
                sleep(500);
            }
        }
    }
    static class ThreadDownload extends Thread{
        private final int BUFF_LEN = 64;
        private long start;
        private long stop;
        private InputStream is;
        private RandomAccessFile mm;
        public ThreadDownload(long start, long stop, InputStream is, RandomAccessFile mm){
            System.out.println(start+"--->"+stop);
            this.start = start;
            this.stop = stop;
            this.is = is;
            this.mm = mm;
        }
        public void getStart(){
            final int DOWN_THREAD_NUM = 4;
            final String OUT_FILE_NAME = "down.jpg";
            InputStream[] isArr = new InputStream[DOWN_THREAD_NUM];
            RandomAccessFile[] mmArr = new RandomAccessFile[DOWN_THREAD_NUM];
            try{
                URL url = new URL("https://s2.ax1x.com/2019/07/10/ZcPsSI.png");
                isArr[0] = url.openStream();
                long fileLen = getFileLength(url);
                System.out.println("Net Source size: "+fileLen);
                mmArr[0] = new RandomAccessFile(OUT_FILE_NAME, "rw");
                for(int i=0; i<fileLen; i++){
                    mmArr[0].write(0);
                }
                long numPerThread = fileLen / DOWN_THREAD_NUM;
                long left = fileLen % DOWN_THREAD_NUM;
                for(int i=0; i<DOWN_THREAD_NUM; i++){
                    if( i != 0){
                        isArr[i] = url.openStream();
                        mmArr[i] = new RandomAccessFile(OUT_FILE_NAME, "rw");
                    }
                    if(i==DOWN_THREAD_NUM-1){
                        new ThreadDownload(i*numPerThread, (i+1)*numPerThread+left, isArr[i], mmArr[i]).start();
                    }else{
                        new ThreadDownload(i*numPerThread, (i+1)*numPerThread, isArr[i], mmArr[i]).start();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            try{
                is.skip(start);
                mm.seek(start);
                byte[] buff = new byte[BUFF_LEN];
                long contentLen = stop - start;
                long times = contentLen / BUFF_LEN +4;
                int hasRead = 0;
                for(int i=0; i<times; i++){
                    hasRead = is.read(buff);
                    if(hasRead < 0){
                        break;
                    }
                    mm.write(buff, 0, hasRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try{
                    if(is!=null){
                        is.close();
                    }
                    if(mm!=null){
                        mm.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        private static long getFileLength(URL url) throws IOException {
            URLConnection con = url.openConnection();
            return con.getContentLength();
        }
    }

    public static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 并发核心机制：JUC包中的并发集合、Synchronized、volatile、Threadlocal
     * 原子类:AtomicBoolean,AtomicInteger,AtomicIntegerArray,AtomicLong,AtomicLongArray
     * AtomicReference,AtomicReferenceArray,AtomicStampedReference
     * 锁：Lock,ReentrantLock,ReentrantReadWriteLock
     */
    public static void testAtom(){
        AtomTest.atomicBoolean();
//        AtomTest.atomicInteger();
//        AtomTest.CASTest();
        AtomTest.IntegerArray();
    }
    public static String getThreadName(){
        return Thread.currentThread().getName();
    }
    public static String time(){
        return new SimpleDateFormat(" [HH:mm:SS]").format(new Date()).toString();
    }

/*
结果：
Thread-0false
Thread-1false
Thread-3false
Thread-0
几个线程并发操作原子类，通过cas设置，发现执行compareAndSet方法需要一些时间
在方法执行时，一直尝试修改线程的值，期间1号3号线程运行完后，才修改成功的
此时，没有其他线程争夺共享资源了，即使值未匹配也能修改, 因为新值与原有值一样了，无需改变
Thread-3false
Thread-0false
Thread-1false
Thread-0true
Thread-0true
Thread-2true
但cas原理是比较V和A是否相同不是比较V和B是否相同
因为V!=A,所以cas失败，是很快速的失败，然后输出无法看出cas过程!
 */

    /*
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long valueOffset;

    static {
        try {
            valueOffset = unsafe.objectFieldOffset
                (AtomicInteger.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    private volatile int value;
     */
    static class AtomTest{
        public volatile int shard = 1;
        static AtomicBoolean aBoolean = new AtomicBoolean();
        public static void atomicBoolean(){
            new Thread(()->{
                sleep(3000);
                System.out.println(getThreadName()+aBoolean.get()+time());
                aBoolean.set(true);
                System.out.println(getThreadName()+aBoolean.get()+time());
//                如果当前值==预期值，则将该值原子设置为给定的更新值, 期待false，如果原本是false，这成功compare，然后set为true
                aBoolean.compareAndSet(false, true);
                System.out.println(getThreadName()+aBoolean.get()+time());
            }).start();
            new Thread(()->{
                sleep(3000);
                System.out.println(getThreadName()+aBoolean.get());
//                无条件地设置为给定的值
                aBoolean.set(true);
            }).start();
            new Thread(()->{
                sleep(5000);
                System.out.println(getThreadName()+aBoolean.get());
//                最终设定为给定值
                aBoolean.lazySet(true);
            }).start();
            new Thread(()->{
                sleep(3000);
//                将原子设置为给定值并返回上一个值, 返回旧值并设定新值
                System.out.println(getThreadName()+aBoolean.getAndSet(true));
            }).start();
            new Thread(()->{
                while(!aBoolean.get()){
                    System.out.println(getThreadName()+(new SimpleDateFormat(" [HH:mm:SS]").format(new Date())));
                    sleep(1000);
                }
            }).start();
        }

        public static void atomicInteger(){
            AtomicInteger aInteger = new AtomicInteger(1);
            aInteger.getAndIncrement();
            new Thread(()->{
                sleep(10);     // 结果是1，因为睡眠，主线程读到旧值
                aInteger.set(5);    // 具有volatile属性，可见性和有序性
            }).start();
            new Thread(()->{
                aInteger.lazySet(6);  // 不具有可见性，最后才修改，无法保证立刻写到主内存中
            }).start();
//            sleep(10000);  睡眠10s还是6，这个lazy是真的没有可见性
            System.out.println(aInteger.get()); // 5
        }

        public static void CASTest(){
            AtomicInteger atomicInteger = new AtomicInteger(1);
            new Thread(()->{
                System.out.println(getThreadName()+time()+atomicInteger.get());
                atomicInteger.set(10);
                sleep(3000);
//                并不是不断尝试的，而是fast-fail，只要期待值不同，立刻失败
                boolean cas = atomicInteger.compareAndSet(10, 20);
                System.out.println(getThreadName()+time()+atomicInteger.get()+cas);
            }).start();
            new Thread(()->{
                while(true){
                    int i = atomicInteger.incrementAndGet();
                    System.out.println(getThreadName()+time()+i);
                    sleep(1000);
                    if(atomicInteger.get()==11){
                        break;
                    }
                }
            }).start();
        }

        public static void IntegerArray(){
//            创建一个给定长度的新AtomicIntegerArray，所有元素最初为零。
            AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(3);
//            arr[0]=0+2 = 2;
            int ret = atomicIntegerArray.addAndGet(0, 2);
            ret = atomicIntegerArray.addAndGet(0, 2);
            System.out.println(ret);
            ret = atomicIntegerArray.decrementAndGet(0);
            System.out.println(ret);
            new Thread(()->{
                atomicIntegerArray.decrementAndGet(0);
            }).start();
//            System.out.println(atomicIntegerArray.getAcquire(0));
            System.out.println(atomicIntegerArray.get(0));
            atomicIntegerArray.getAndIncrement(0);
            atomicIntegerArray.getAndDecrement(1);
            atomicIntegerArray.getAndSet(0,3);
//            atomicIntegerArray.getPlain(0);
        }

        public static void atomicLongDouble(){
            AtomicLong atomicLong = new AtomicLong();
            atomicLong.set(100);

//            DoubleAccumulator accumulator = new DoubleAccumulator();
            DoubleAdder adder = new DoubleAdder();
            adder.add(10.0);
            System.out.println(adder.sum());
        }

        public static void atomicLongArray(){
            AtomicLongArray atomicLongArray = new AtomicLongArray(3);
        }

        public static void atomicField(){
//            基于反射的实用程序，可对指定类的指定 volatile int字段进行原子更新。
            AtomicIntegerFieldUpdater<AtomTest> fieldUpdater =
                    AtomicIntegerFieldUpdater.newUpdater(AtomTest.class, "shard");
            if(fieldUpdater.compareAndSet(new AtomTest(), 1, 2)){
                System.out.println("修改了volatile字段");
            }
        }

        public static void reference(){
//            可以原子方式更新的对象引用
//            类似类型不想写了，如char，short等，给你一个引用泛型类
            AtomicReference<Integer> ref = new AtomicReference<>();
//            维护一个对象引用以及一个整数“标记”，可以原子方式更新
//            解决ABA问题，使用整数标记修改记录和版本发送
            AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(1, 100);
            stampedReference.attemptStamp(2, 200);
            stampedReference.compareAndSet(2,3,200,300);
        }
    }


    /**
     * "原子类:AtomicBoolean,AtomicInteger,AtomicIntegerArray,AtomicLong,AtomicLongArray"+
     * "AtomicReference,AtomicReferenceArray,AtomicStampedReference"+
     * "锁：Lock接口，ReentrantLock，ReentrantReadWriteLock",
     * "可重入锁又名递归锁，是指同一个线程在外层方法获取了锁，在进入内层方法会自动获取锁。"+
     * "ReentrantReadWriteLock其写锁是独享锁，其读锁是共享锁。"+
     * "读锁是共享锁使得并发读是非常高效的,读写,写读,写写的过程是互斥的。",
     * "ReentrantReadWriteLock大多数场景下，读操作比写操作频繁，"+
     * "只要保证每个线程都能读取到最新数据，并且在读数据时不会有其它线程在修改数据，"+
     * "那么就不会出现线程安全问题。这种策略减少了互斥同步，自然也提升了并发性能，"+
     * "ReentrantReadWriteLock就是这种策略的具体实现"+
     * "允许多个读操作并发执行，但每次只允许一个写操作。"
     */
    static class LockTest{
        public static void sleep(long time){
            try { Thread.sleep(time); } catch (InterruptedException e) {}
        }
        public int i = 1;
        public void lockDef(){
            Lock lock = new ReentrantLock(true);
            Condition isEmpty = lock.newCondition();
            Condition isFull = lock.newCondition();

            new Thread(new Runnable() {
                @Override
                public void run() {
//                    lock.tryLock();
                    while(i<10){
                        i++;
                    }
//                    lock.unlock();
                    System.out.println(i);
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    lock.tryLock();
                    i++;
//                    lock.unlock();
                    System.out.println(i);
                }
            }).start();
/*
不加锁情况下：
    不sleep->10,10,11,第二个thread出错
    sleep->10,11,11,哪怕是1，也正确
 */
//            sleep(1);
            System.out.println(i);
        }

        public void lockReadWrite(){

        }
    }

    /*
    public interface Lock {
        void lock();
        void lockInterruptibly() throws InterruptedException;
        boolean tryLock();
        boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
        void unlock();
        Condition newCondition();
    }
     */
    public static void testLock(String[] args) {

        LockTest lockTest = new LockTest();
        lockTest.lockDef();

//test
        UnboundedCache<Integer, Integer> cache = new UnboundedCache<>();
        ExecutorService threads = Executors.newCachedThreadPool();
        for (int i = 0; i < 20; i++) {
            threads.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 3; j++) {
                        cache.put(j,new Random().nextInt(20));
                    }

                }
            });
            cache.get(0);
        }
        threads.shutdown();
    }

    // 使用可重入锁实现一个多线程缓存
    static class UnboundedCache<K,V>{
        private final Map<K,V> map = new WeakHashMap<>();
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        public boolean put(K key, V value){
//            写，只能独享锁，单线程操作
            lock.writeLock().lock();
            try {
                map.put(key, value);
                System.out.println("put "+key+" : "+value);
            } finally {
                lock.writeLock().unlock();
            }
            return true;
        }

        public V get(K key){
//          读，可共享锁，多线程操作
            lock.readLock().lock();
            V val;
            try{
                val = map.get(key);
                System.out.println("get: "+val);
            } finally {
                lock.readLock().unlock();
            }
            return val;
        }

        public boolean remove(K key){
            lock.writeLock().lock();
            try{
                map.remove(key);
            } finally {
                lock.writeLock().unlock();
            }
            return true;
        }

        public boolean clear(){
            lock.writeLock().lock();
            try{
                map.clear();
            } finally {
                lock.writeLock().unlock();
            }
            return true;
        }
    }

    /**
     * 原子类、锁、并发容器https://dunwu.github.io/javacore/concurrent/java-concurrent-container.html
     * 并发容器：ConcurrentHashMap，CopyOnWriteArrayList，CopyOnWriteArraySet,
     * 和同步容器不同，同步容器实现是在读写修改方法上加Synchronized锁，并发容器使用AQS队列同步器
     * ConcurrentHashMap:维护了一个Segment数组，一般称为分段桶。
     * finalSegment<K,V>[]segments;
     * 把锁的对象分成多段，每段独立控制，使得锁粒度更细，减少阻塞开销，从而提高并发性,
     * Java1.8之前采用分段锁机制细化锁粒度，降低阻塞，从而提高并发性。
     * Java1.8之后基于CAS实现。
     * 取消segments字段，直接采用transientvolatileHashEntry<K,V>[]table保存数据，
     * 采用table数组元素作为锁，从而实现了对每一行数据进行加锁，进一步减少并发冲突的概率。
     * 将原先table数组＋单向链表的数据结构，变更为table数组＋单向链表＋红黑树的结构。
     * 对于hash表来说，最核心的能力在于将keyhash之后能均匀的分布在数组中。
     * 如果hash之后散列的很均匀，那么table数组中的每个队列长度主要为0或者1。
     * 但实际情况并非总是如此理想，虽然ConcurrentHashMap类默认的加载因子为0.75，
     * 但是在数据量过大或者运气不佳的情况下，还是会存在一些队列长度过长的情况，
     * 如果还是采用单向列表方式，那么查询某个节点的时间复杂度为O(n)；
     * 因此，对于个数超过8(默认值)的列表，jdk1.8中采用了红黑树的结构，
     * 那么查询的时间复杂度可以降低到O(logN)，可以改进性能。
     * CopyOnWrite字面意思为写入时复制。CopyOnWriteArrayList是线程安全的ArrayList。
     * 读操作不同步不加锁不会阻塞，所有写操作都会加锁同步，使用可重入锁ReentrantLock
     */
    /*
ConcurrentHashMap	HashMap	Java 1.8 之前采用分段锁机制细化锁粒度，降低阻塞，从而提高并发性；Java 1.8 之后基于 CAS 实现。
ConcurrentSkipListMap	SortedMap	基于跳表实现的
CopyOnWriteArrayList	ArrayList
CopyOnWriteArraySet	Set	基于 CopyOnWriteArrayList 实现。
ConcurrentSkipListSet	SortedSet	基于 ConcurrentSkipListMap 实现。
ConcurrentLinkedQueue	Queue	线程安全的无界队列。底层采用单链表。支持 FIFO。
ConcurrentLinkedDeque	Deque	线程安全的无界双端队列。底层采用双向链表。支持 FIFO 和 FILO。
ArrayBlockingQueue	Queue	数组实现的阻塞队列。
LinkedBlockingQueue	Queue	链表实现的阻塞队列。
LinkedBlockingDeque	Deque	双向链表实现的双端阻塞队列
 */
     static class A04ConcurrentCollection {

        public static void concurrentHashMap() {
            ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
            concurrentHashMap.put(1, 1);
//        key不能为null
//        concurrentHashMap.put(null, 1);
//        value不能为null
//        concurrentHashMap.put(1, null);
        }

        public static void copyonwrite() {
            CopyOnWriteArrayList<Integer> arrayList = new CopyOnWriteArrayList<>();
            CopyOnWriteArraySet<Integer> arraySet = new CopyOnWriteArraySet<>();
        }

        public static void queue() {
            ConcurrentLinkedQueue<Thread> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

        }
    }

    /**
     * "J.U.C包中的工具类是基于synchronized、volatile、CAS、ThreadLocal这样的并发核心机制打造的。"+
     * "所以，要想深入理解J.U.C工具类的特性、为什么具有这样那样的特性，就必须先理解这些核心机制",
     * "synchronized关键字是通过加锁来实现线程的互斥同步，保证同一时刻只能有一个线程访问共享区域（临界区）"+
     * "原理是：javac编译代码后，其加锁的语句前后有monitor-enter和monitor-exit两个语句"+
     * "这两个字节码指令，这两个字节码指令都需要一个引用类型的参数来指明要锁定和解锁的对象"+
     * "每个类都有一个monitor对象，一个monitor包含一个锁一个同步队列一个等待队列"+
     * "所以当线程执行竞态代码时，先执行monitor-enter，monitor（操作系统内核态）检测有没有锁，如果有就给该线程；如果没有就把线程放在同步队列中"+
     * "其他线程执行monitor-exit时，monitor解锁，会从同步队列中选一个线程加锁，不是按先来顺序给锁，所以是非公平的"+
     * "https://segmentfault.com/a/1190000016417017",
     * "加锁方式3中，对象锁，class对象锁，自己指定对象加锁"+
     * "特性：原子性，可见性，有序性满足"+
     * "可重入性，同步的代码块可以在得到外锁的情况下，自动得到里面的锁，避免死锁发送（互相争夺资源）"+
     * "非公平性，同步的临界区在选择线程时，是非公平的"+
     * "互斥性（操作的原子性），同步代码块会阻塞其他线程访问"+
     * "可见性，在线程退出临界区（monitor-exit）时保证工作内存的数据写入到主内存中"+
     * ""+
     * "锁优化：轻量级锁、偏向锁、适应性自旋、锁粗化、锁消除"+
     * "Synchronized锁状态:无锁状态->偏向锁->轻量级锁->重量级锁"+
     * "Java1.6中引入了自适应的自旋锁。自适应意味着自旋的次数不再固定了，而是由前一次在同一个锁上的自旋次数及锁的拥有者的状态来决定"+
     * "锁消除是指对于被检测出不可能存在竞争的共享数据的锁进行消除"+
     * "锁粗化如果虚拟机探测到由这样的一串零碎的操作都对同一个对象加锁，将会把加锁的范围扩展（粗化）到整个操作序列的外部。"+
     * "偏向锁的思想是偏向于让第一个获取锁对象的线程，这个线程在之后获取该锁就不再需要进行同步操作，甚至连CAS操作也不再需要。"+
     * "轻量级锁是相对于传统的重量级锁而言，它使用CAS操作来避免重量级锁使用互斥量的开销，可以先采用CAS操作进行同步，如果CAS失败了再改用互斥量进行同步"
     */
    /*
Synchronized锁状态改变，一开始是偏向锁状态：把锁给第一个加锁的线程，不进行cas同步操作
偏向锁依据：对于绝大部分锁，在整个同步周期内不仅不存在竞争，而且总由同一线程多次获得
如果多个线程访问同步区，那么锁升级为轻量级锁，基于cas机制比较并设置，不断尝试修改直到成功或失败
如果cas次数过多会转为重量级锁
重量级锁就是上面介绍的内容，使用moniter object机制，底层是mutex互斥量实现的，所以增加了系统的开销。
而轻量级锁使用cas，基于乐观并发策略，不使用互斥量，减少开销
偏向锁连cas尝试都不用，直接是给第一个加锁的线程。
https://www.cnblogs.com/butterfly100/p/8786856.html

重量级     互斥量       monitor object
轻量级     不断尝试     cas
偏向锁     直接给       第一个线程

 */

    /**
     * "volatile:实现了三原则中的可见性和有序性，但没有实现原子性，无法i++"+
     * "原理：javac编译后字节码加上会多出一个lock前缀指令，形成内存屏障"+
     * "所以使用有条件："+
     * "对变量的写操作不依赖于当前值\n"+
     * "该变量没有包含在具有其他变量的不变式中"
     */
    //    双重效验单例模式
    static class Singleton{
        private volatile static Singleton instance = null;
        private Singleton() {}
        public static Singleton getInstance() {
            if(instance==null) {
                synchronized (Singleton.class) {
                    if(instance==null)
                        instance = new Singleton();
                }
            }
            return instance;
        }
    }

    /**
     * 互斥同步最主要的问题是线程阻塞和唤醒所带来的性能问题,互斥同步属于一种悲观的并发策略+
     * 用户态核心态转换、维护锁计数器和检查是否有被阻塞的线程需要唤醒等操作+
     * CAS:基于冲突检测的乐观并发策略,先进行操作，如果没有其它线程争用共享数据，那操作就成功了，+
     * 否则采取补偿措施（不断地重试，直到成功为止）。这种乐观的并发策略的许多实现都不需要将线程阻塞，+
     * 因此这种同步操作称为非阻塞同步。+
     * CAS（CoSwmpareandap），字面意思为比较并交换。CAS有3个操作数，分别是：内存值V，旧的预期值A，要修改的新值B。+
     * 当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则什么都不做。,
     * 原理：利用Unsafe这个类提供的CAS操作，实现的Atomic::cmpxchg指令+
     * Atomic::cmpxchg的实现使用了汇编的CAS操作，并使用CPU提供的lock信号保证其原子性+
     * 应用：原子类、自旋锁（线程反复检查锁变量是否可用，直到成功为止）+
     * CAS比锁性能更高。因为CAS是一种非阻塞算法，所以其避免了线程阻塞和唤醒的等待时间。+
     * 问题：ABA问题，解决方法加入标记，对比两个A是否一样，AtomicStampedReference+
     * 循环开销大，（定时或自旋一定次数）+
     * 只能保证一个共享变量的原子性，无法对多个变量保证，需要对变量重定义
     */
    static class CASTest{
        public static void test() throws InterruptedException {
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            final AtomicInteger count = new AtomicInteger(0);
            for (int i = 0; i < 10; i++) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        count.incrementAndGet();
                    }
                });
            }
            executorService.shutdown();
            executorService.awaitTermination(3, TimeUnit.SECONDS);
            System.out.println("Final Count is : " + count.get());
        }
    }


    /**
     * 一个存储线程本地副本的工具类，解决线程安全另一个方法，不一定要同步，同步只是不让多个线程修改共享资源+
     * 如果不涉及共享资源也就没必要同步了，怎么做:1.可重入代码，2.线程本地副本+
     * 1.可重入代码：一个方法，它的返回结果是可以预测的，即只要输入了相同的数据，就能返回相同的结果，那它就满足可重入性+
     * 共享区的数据对方法本身没有影响的+
     * 2.线程本地副本：使用ThreadLocal为共享变量在每个线程中都创建了一个本地副本，这个副本只能被当前线程访问，其他线程无法访问+
     * 就是利用了线程栈的私有性，把共享数据变为私有数据+
     * 应用场景：管理数据库连接、Session。,
     * 原理：Thread类中维护着一个ThreadLocal.ThreadLocalMap类型的成员threadLocals。+
     * 这个成员就是用来存储线程独占的变量副本。+
     * ThreadLocalMap是ThreadLocal的内部类，它维护着一个Entry数组，Entry用于保存键值对，+
     * 其key是ThreadLocal对象，value是传递进来的对象（变量副本）。+
     * 2.ThreadLocalMap采用线性探测的方式来解决Hash冲突。+
     * 所谓线性探测，就是根据初始key的hashcode值确定元素在table数组中的位置，+
     * 如果发现这个位置上已经被其他的key值占用，则利用固定的算法寻找一定步长的下个位置，依次判断，直至找到能够存放的位置。+
     * 3.ThreadLocalMap的Entry继承了WeakReference，所以它的key（ThreadLocal对象）是弱引用，而value（变量副本）是强引用。+
     * 如果ThreadLocal对象没有外部强引用来引用它，那么ThreadLocal对象会在下次GC时被回收。+
     * 此时，Entry中的key已经被回收，但是value由于是强引用不会被垃圾收集器回收。+
     * 如果创建ThreadLocal的线程一直持续运行，那么value就会一直得不到回收，产生内存泄露+
     * 那么如何避免内存泄漏呢？方法就是：使用ThreadLocal的set方法后，显示的调用remove方法。
     */
    /*
public class ThreadLocal<T> {
    public T get() {}
    public void set(T value) {}
    public void remove() {}
    public static <S> ThreadLocal<S> withInitial(Supplier<? extends S> supplier) {}
}
get - 用于获取 ThreadLocal 在当前线程中保存的变量副本。
set - 用于设置当前线程中变量的副本。
remove - 用于删除当前线程中变量的副本。如果此线程局部变量随后被当前线程读取，
则其值将通过调用其 initialValue 方法重新初始化，除非其值由中间线程中的当前线程设置。
这可能会导致当前线程中多次调用 initialValue 方法。
initialValue - 为 ThreadLocal 设置默认的 get 初始值，需要重写 initialValue 方法 。
 */
        /*
那么如何避免内存泄漏呢？方法就是：使用 ThreadLocal 的 set 方法后，显示的调用 remove 方法 。
ThreadLocal<String> threadLocal = new ThreadLocal();
try {
    threadLocal.set("xxx");
    // ...
} finally {
    threadLocal.remove();
}
 */
    static class ThreadLocalTest{
        public String DB_URL = "";
        private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
            @Override
            public Connection initialValue() {
                try {
                    return DriverManager.getConnection("");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        public static Connection DBConnection(){
            return connectionHolder.get();
        }

        private static final ThreadLocal<Session> sessionHolder = new ThreadLocal<>();
        public static Session getSession() {
            Session session = (Session) sessionHolder.get();
            try {
                if (session == null) {
                    session = createSession();
                    sessionHolder.set(session);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sessionHolder.remove();
            }
            return session;
        }

        private static Session createSession() {
            return sessionHolder.get();
        }
    }

    /**
     * aqs队列同步器abstractqueuedsynchronizer,处理同步，是并发锁的基础原理+
     * 锁都没有直接继承AQS，而是定义了一个Sync类去继承AQS+
     * 因为锁面向的是使用用户，而同步器面向的则是线程控制+
     * API:三个获取，一个得到,
     * 原理：获取独占锁：先尝试获取同步状态state，如果获取同步状态成功(加锁了)，则结束方法，直接返回。+
     * 将当前节点设为头节点\n+
     * 如果获取同步状态不成功(被其他线程占用)，AQS不断尝试利用CAS将当前线程插入等待同步队列的队尾，直到成功为止+
     * 接着，不断尝试为等待队列中的线程节点获取独占锁。检测是否为头节点
     */
/*
 独享锁API
public final void acquire(int arg)获取独占锁
public final void acquireInterruptibly(int arg)获取可中断的独占锁
区别仅在于它会通过 Thread.interrupted 检测当前线程是否被中断，如果是，则立即抛出中断异常
public final boolean tryAcquireNanos(int arg, long nanosTimeout)
public final boolean release(int arg)
tryAcquireNanos - 尝试在指定时间内获取可中断的独占锁。在以下三种情况下回返回：
在超时时间内，当前线程成功获取了锁；
当前线程在超时时间内被中断；
超时时间结束，仍未获得锁返回 false。
区别在于它会根据超时时间和当前时间计算出截止时间。
在获取锁的流程中，会不断判断是否超时，如果超时，直接返回 false；
如果没超时，则用 LockSupport.parkNanos 来阻塞当前线程
release - 释放独占锁。
先尝试获取解锁线程的同步状态，如果获取同步状态不成功，则结束方法，直接返回。
如果获取同步状态成功，AQS 会尝试唤醒当前线程节点的后继节点。

#共享锁 API
public final void acquireShared(int arg)
public final void acquireSharedInterruptibly(int arg)
public final boolean tryAcquireSharedNanos(int arg, long nanosTimeout)
public final boolean releaseShared(int arg)
acquireShared - 获取共享锁。
acquireShared 方法和 acquire 方法的逻辑很相似，区别仅在于自旋的条件以及节点出队的操作有所不同。
成功获得共享锁的条件如下：
tryAcquireShared(arg) 返回值大于等于 0 （这意味着共享锁的 permit 还没有用完）。
当前节点的前驱节点是头结点。
acquireSharedInterruptibly - 获取可中断的共享锁。
tryAcquireSharedNanos - 尝试在指定时间内获取可中断的共享锁。
release - 释放共享锁。
releaseShared 首先会尝试释放同步状态，如果成功，则解锁一个或多个后继线程节点。
释放共享锁和释放独享锁流程大体相似，区别在于：
    对于独享模式，如果需要 SIGNAL，释放仅相当于调用头节点的 unparkSuccessor

  */
    public static void testAQS(){
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try{
                    lock();
                    Thread.sleep(1000);
                } catch (InterruptedException e){

                } finally {
                    unlock();
                }

            }).start();

        }
    }
    static class ExclusiveLock {
    }

    static class Sync extends AbstractQueuedSynchronizer {
        protected boolean isHeldExclusive() {
            return getState() == 1;
        }

        public boolean tryAcquire(int acquires) {
            assert acquires == 1;
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        protected boolean tryRelease(int releases) {
            assert releases == 1;
            if (getState() == 0) {      // 该线程没有获得同步状态即未加锁，无法释放锁
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);      //把当前排他线程设空
            setState(0);                        //把状态设置为0
            return true;
        }

        Condition newCondition() {       // 得到一个条件，以支持await和asignal，线程等待和唤醒
            return new ConditionObject();
        }
//       实现序列化的方法还有writeObject等
//        private void readObject(ObjectInputStream ois){
//            ois.defaultReadObject();
//            setState(0);
//        }
    }

    //    本质就是mutex的01信号量
    private static final Sync sync = new Sync();

    public static void lock() {
        sync.acquire(1);
    }

    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }

    public static void unlock() {

        sync.release(1);
    }

    public boolean tryUnlock() {
        return sync.tryRelease(0);
    }

    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked() {
        return sync.isHeldExclusive();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    /**
     * condition版生产者消费者模型
     */
    private static Lock lock = new ReentrantLock();
    public static Queue<Integer> queue = new PriorityQueue<>();
    private static Condition isEmpty = lock.newCondition();
    private static Condition isFull = lock.newCondition();
    private static volatile boolean isClose = false;

    public static void testCP() throws InterruptedException {
        new Thread(new Producer()).start();
        new Thread(new Customer("AAA")).start();
        new Thread(new Customer("BBB")).start();
        new Thread(new Customer("CCC")).start();

        Thread.sleep(100);
        isClose = true;
        System.out.println("END : queue size = "+queue.size());
    }

    static class Producer implements Runnable{
        String name = "produce";
        @Override
        public void run() {
            while(!isClose){
                lock.lock();
                try {
                    while(queue.size()==3){
                        try {
                            isFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            isEmpty.signal();
                        }
                    }
                    queue.add(new Random().nextInt(10));
                    System.out.println(name+" produce 1");
                    isEmpty.signal();
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    static class Customer implements Runnable{
        String name;
        public Customer(String name){
            this.name = name;
        }
        @Override
        public void run() {
            while(!isClose){
                lock.lock();
                try{
                    while(queue.size()==0){
                        try {
                            isEmpty.await();
                        } catch (InterruptedException e) {
                            isFull.signal();
                        }
                    }
                    queue.poll();
                    System.out.println(name+" queue poll");
                    isFull.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * 线程池https://dunwu.github.io/javacore/concurrent/java-thread-pool.html+
     * Executor-运行任务的简单接口。\n+
     * ExecutorService-扩展了Executor接口。扩展能力：支持有返回值的线程，支持管理线程的生命周期\n+
     * ScheduledExecutorService-扩展了ExecutorService接口。扩展能力：支持定期执行任务。\n+
     * AbstractExecutorService-ExecutorService接口的默认实现。\n+
     * ThreadPoolExecutor-Executor框架最核心的类，它继承了AbstractExecutorService类。\n+
     * ScheduledThreadPoolExecutor-ScheduledExecutorService接口的实现，一个可定时调度任务的线程池。\n+
     * Executors-可以通过调用Executors的静态工厂方法来创建线程池并返回一个ExecutorService对象。+
     * 支持有返回值的线程-sumbit、invokeAll、invokeAny方法中都支持传入Callable对象。\n+
     * 支持管理线程生命周期-shutdown、shutdownNow、isShutdown等方法,
     * 4大构造方法，参数意义，线程池工作原理，4大拒绝策略+
     * execute方法提交任务并执行，submit方法针对有返回值的线程+
     * 4大默认线程池single，fixed，cached，scheduled
     */
    public void test(){
        Executor poolA = new ThreadPoolExecutor(10, 100, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        ExecutorService poolB = Executors.newCachedThreadPool();
        ExecutorService poolC = Executors.newSingleThreadExecutor();
        ExecutorService poolD = Executors.newFixedThreadPool(12);
        ExecutorService poolE = Executors.newScheduledThreadPool(10);

        ScheduledExecutorService poolF = new ScheduledThreadPoolExecutor(3);
    }

    /**
     * 并发工具类
     * CountDownLatch递减计数锁，CountDownLatch维护一个计数器count，表示需要等待的事件数量。
     * countDown方法递减计数器，表示有一个事件已经发生。
     * 调用await方法的线程会一直阻塞直到计数器为零，或者等待中的线程中断，或者等待超时
     * CyclicBarrier循环栅栏,可以让一组线程等待至某个状态（遵循字面意思，不妨称这个状态为栅栏）之后再全部同时执行
     * parties数相当于一个阈值，当有parties数量的线程在等待时，CyclicBarrier处于栅栏状态
     * Semaphore信号量。Semaphore用来控制同时访问某个特定资源的操作数量，或者同时执行某个指定操作的数量。
     * 管理着一组虚拟的许可（permit），permit的初始数量可通过构造方法来指定。
     * 每次执行acquire方法可以获取一个permit，如果没有就等待；而release方法可以释放一个permit。
     * CountDownLatch和CyclicBarrier都能够实现线程之间的等待，只不过它们侧重点不同：\n
     * CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；\n
     * CyclicBarrier一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；\n
     * 另外，CountDownLatch是不可以重用的，而CyclicBarrier是可以重用的。\n
     * Semaphore其实和锁有点类似，它一般用于控制对某组资源的访问权限
     *
     */

    public void testTL(){
        CountDownLatch countDownLatch = new CountDownLatch(3);     //阻塞，直到3个线程执行过了
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);  //当两个线程齐了就一起开始工作

        Semaphore semaphore = new Semaphore(2);  // 2个许可，最多两个线程获得执行，其他阻塞
    }
}
