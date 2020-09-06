package com.hujunchina.client.base;

import java.util.PriorityQueue;
import java.util.concurrent.*;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/3 7:58 下午
 * @Version 1.0
 * Q 队列, 常用在线程池中
 */
public class CollectionQueue {
    public static void main(String[] args) {
        blockingQueue();
    }

    //BlockingQueue 不接受 null 值元素
    //阻塞队列使用
    //PriorityBlockingQueue的容量虽然有初始化大小，但是不限制大小
    // 如果当前容量已满，插入新元素时会自动扩容。
    //可重入锁在插入和删除时候确保线程安全
    public static void blockingQueue(){
        //优先队列需要一个顺序，默认自然顺序
        class RedisThread implements Runnable, Comparable{
            int tag;
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
            @Override
            public int compareTo(Object o) {
                RedisThread r = (RedisThread)o;
                return this.tag - r.tag;
            }
        }
        BlockingQueue<RedisThread> redis = new PriorityBlockingQueue<>();
        RedisThread r = new RedisThread();
        redis.add(r);
        Thread t = new Thread(redis.poll());
        t.start();
    }

    //ArrayBlockingQueue 实现并发同步的原理就是，
    // 读操作和写操作都需要获取到 AQS 独占锁才能进行操作。
    //构造方法必须要有容量，可以指定独占锁的公平性，
    // 非公平锁高吞吐量（CPU运行时占总时间的比值）
    //公平锁保证先来的先执行，有顺序性
    public static void arrayBlockingQueue() throws InterruptedException {
        //final ReentrantLock lock;
        //private final Condition notEmpty;
        //private final Condition notFull;
        BlockingQueue<Integer> a = new ArrayBlockingQueue<>(6, true);
        a.take();
        a.put(2);
    }

    //通过锁和 condition 条件控制
    //takeLock 和 notEmpty 搭配
    //putLock 需要和 notFull 搭配
    public static void linkedBlockingQueue(){
        BlockingQueue<Integer> l = new LinkedBlockingQueue<>();

    }

    //容量为0，无法放入元素，所以报错
    //保证队列中所有任务都能立刻执行
    public static void synchronous(){
        BlockingQueue<Integer> s = new SynchronousQueue<>();

    }
}
