package com.hujunchina.client.base;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/4 11:22 下午
 * @Version 1.0
 * lambda表达式集合
 */
public class Lambda {
    public static void main(String[] args) {
//        String s = getString(()->"hujun");
//        System.out.println(s);
//
//        wast("hujun", (String name)->{
//            System.out.println(name);
//        });
        test2();
    }

    public static Comparator<String> getComparator(){
        return (o1,o2)->o2.length()-o1.length();
    }

    public static void sortByLen(){
        String[] arr = {"hu", "jun", "love"};
        System.out.println(Arrays.toString(arr));
        Arrays.sort(arr, getComparator());
        System.out.println(Arrays.toString(arr));
    }

    public static String getString(Supplier<String> sup){
        return sup.get();
    }

    public static void wast(String name, Consumer<String> com){
        com.accept(name);
    }

    //自己实现一个filter
    interface FilterTest{
        boolean test(Object i);
    }
    public static List<Object> myFilter(List<Object> list, FilterTest filterTest){
        List<Object> res = new LinkedList<>();
        for(Object o : list){
            if(filterTest.test(o)){
                res.add(o);
            }
        }
        return res;
    }
    //使用系统自带的filter
    public static void test2(){
        List<Integer> list = Arrays.asList(1,2,3,4);
        list.stream().filter(i->i>2).forEach(i-> System.out.println(i));
    }
}
