package com.hujunchina.client.base;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/3 7:34 下午
 * @Version 1.0
 * 包括ArrayList、LinkedList
 */
public class CollectionList {
    public static void main(String[] args) {
        linkedList();
        queueList();
        vectorList();
        dequeList();
    }

    //ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable
    //频繁读取，不适合删除和插入情况，深拷贝，可序列化，随机访问
    //默认长度为10，超过就扩容为原来的1.5倍，使用arrays copyof来移位拷贝
    //动态数组，有初始长度，但可以动态扩容； 底层是一个Object[]数组存储
    public static void arrayList(){
        List<Integer> list = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>(10);
        list = Arrays.asList(1,2,3,4,5);
        list.add(6, 10);
        //System.arraycopy(elementData, 3, elementData, 2, numMoved);
        //把【3，end】直接复制到【2，end】位置
        list.remove(2);
        //可以放入null，占位置，可以输出
        list2.add(null);
        //newCapacity = oldCapacity + (oldCapacity >> 1);
        list2.get(1);
    }

    //LinkedList<E> extends AbstractSequentialList<E> implements List<E>, Deque<E>, Cloneable, java.io.Serializable
    //频繁删除插入情况，不适合频繁读取情况
    //可以实现双向链表，双端队列，队列等结构，默认无长度限制
    //底层是一个Node类实现的双向链表
    public static void linkedList(){
        List<Integer> list = new LinkedList<>();
        list.add(null);
        list.add(1);
        list.add(2,10);
        System.out.println(list.toString());
    }

    //单队列，Deque可以测试
    public static void queueList(){
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        queue.offer(null);
        System.out.println(queue.peek());
        queue.poll();
        System.out.println(queue.peek());
    }

    //线程安全的list，加了synchronized锁
    public static void vectorList(){
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(null);
        System.out.println(v.get(1));
    }

    //动态扩容的双端队列，底层是顺序表，Object[]数组
    //不能加入null，源代码里面有强烈的检测，throw new NullPointerException();
    public static void dequeList(){
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addFirst(2);
        System.out.println(deque.toString());
        deque.removeFirst();
        deque.removeLast();
    }
    
    //直接使用add first和last 实现双端队列或双向连标
    public static void dequeList2(){
        LinkedList<Integer> deque = new LinkedList<>();
        deque.addFirst(2);
        deque.addLast(3);
        deque.add(4); //=addLast(4)
        System.out.println(deque.getFirst());
        System.out.println(deque.getLast());
    }
}
