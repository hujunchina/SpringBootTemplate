package com.hujunchina.client.base;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/3 7:35 下午
 * @Version 1.0
 */
public class CollectionSet {
    public static void main(String[] args) {

    }

    //hashset是无序，散列的，而linkedhashset是按照输入顺序保存的
    //非线程安全，调用hashmap的构造方法实例化，底层是双链表存储插入的节点的次序
    public static void linkedSet(){
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        //可以放入null
        linkedHashSet.add(null);
        linkedHashSet.add("1");

        System.out.println(linkedHashSet.size());
        for(String string : linkedHashSet){
            System.out.println(string);
        }
    }

    //hashset底层是hashmap，是无序散列的，允许null，非线程安全
    //维护一个 HashMap 实体来实现 HashSet 方法
    //PRESENT 是用于关联 map 中当前操作元素的一个虚拟值
    public static void hashSet(){
        //放入的类要重写equals方法和hashcode方法
        HashSet<Integer> books = new HashSet<>();
    }

    //treeset底层基于treemap实现，是元素有序的，把set中的元素当做key存在map中
    //因为对key排序，所以不能加入null值
    public static void treeSet(){
        TreeSet<Integer> set = new TreeSet<>();
        //表示范围 小于4集合
        System.out.println(set.headSet(4));
        //大于等于5集合
        System.out.println(set.tailSet(5));
        //范围集合
        System.out.println(set.subSet(-3,4));
    }
}
