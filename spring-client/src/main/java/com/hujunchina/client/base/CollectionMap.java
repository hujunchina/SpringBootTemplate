package com.hujunchina.client.base;

import java.util.*;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/3 7:35 下午
 * @Version 1.0
 */
public class CollectionMap {
    public static void main(String[] args) {
        linkedMap();
    }

    //LinkedHashMap<K,V> extends HashMap<K,V> implements Map<K,V>
    //底层使用双向链表存储kv，插入的顺序是有序的，可以按照顺序找到
    //其实顺序和存储无关，存储还是hashmap，而插入顺序被保存在了一个Entry<K,V>的节点中，即双向链表，有前后关系了
    public static void linkedMap(){
        HashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(1,null);
        linkedHashMap.put(null, "2");
        //linkedhashmap可以插入null， kv均可为null
        System.out.println(linkedHashMap.size());
        System.out.println(linkedHashMap.get(null));
        System.out.println(linkedHashMap.get(1));

        linkedHashMap.put(3, "3");
        linkedHashMap.put(4, "4");
        linkedHashMap.put(5, "5");
        //输出是按照输入顺序的
        for(Integer i : linkedHashMap.keySet()){
            System.out.println(i+" "+linkedHashMap.get(i));
        }

        linkedHashMap.remove(3);
        for(Integer i : linkedHashMap.keySet()){
            System.out.println(i+" "+linkedHashMap.get(i));
        }
    }

    //HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V>, Cloneable, Serializable
    //以kv键值对存储数据，底层以桶存储，使用链表（8个以下）或红黑树（8个以上）解决hash冲突问题。
    //非线程安全，k和v均可以为空，k=null时存放在桶0号位
    //先通过hashcode计算hash值，然后通过与桶长度与操作计算桶下标，如果桶内为空直接放入，不为空就冲突处理
    //两个因素：桶大小默认16，负载因子0.75，即桶内元素饱和度，最多可用存12个，多了就要再散列，扩大为原来的2倍
    //hash计算：return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16); 让高位参与运算，减少冲突
    //扩容方式：两倍就是二进制高一位变成1，然后把链表中按0，1，0，1方式间隔变高位，并把1的复制到新桶上。
    public static void hashMap(){
        Map<String, String> map = new HashMap<>();
        map.put("hujun", "liujihong");
        map.put("hujun", "covered");

        map.put(null, null);
        map.put(null, "covered null key");
        map.put("null", null);
//        hashmap都可以放入null，占长度，均可输出
        System.out.println(map.get(null));
        System.out.println(map.get("null"));

        int h=5;
        int hash1 = (h^h)>>>16;
        int hash2 = h^h>>>16;
        System.out.format("%d, %d, %d", hash1, hash2, 5>>>16);
        System.out.println(4-1&3);
    }

    //直接基于红黑树实现，完成对key的排序存储，输出也是有序的，非线程安全
    //因为对key排序，所以不需要hash解决冲突，直接存储即可
    public static void treeMap(){
        final String[] chars = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ");
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        for (int i = 0; i < chars.length; i++) {
            treeMap.put(i, chars[i]);
        }

//       key 不能为 null，因为红黑树要比较key大小，为null怎么比较？
//        treeMap.put(null, "2");
//       但 value 可以为null
        treeMap.put(2, null);

        System.out.println(treeMap);
        Integer low = treeMap.firstKey();
        Integer high = treeMap.lastKey();
        System.out.println(low);
        System.out.println(high);
        Iterator<Integer> it = treeMap.keySet().iterator();
        for (int i = 0; i <= 6; i++) {
            if (i == 3) { low = it.next(); }
            if (i == 6) { high = it.next(); } else { it.next(); }
        }
        System.out.println(low);
        System.out.println(high);
        System.out.println(treeMap.subMap(low, high));
        System.out.println(treeMap.headMap(high));
        System.out.println(treeMap.tailMap(low));
    }
}
