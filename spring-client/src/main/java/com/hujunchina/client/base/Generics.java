package com.hujunchina.client.base;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/1 8:46 上午
 * @Version 1.0
 * 泛型
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Generics {
    public static void main(String[] args) {
//        filter();
        // Integer extends Number
        List<Integer> list = new ArrayList<>();
//        subBound(list);
        System.out.println(findMax(Arrays.asList(1, 2, 3, 4, 8), 0, 5));
    }

    interface UnaryPredicate<T>{
        boolean test(T obj);
    }
    //泛型过滤器+Lambda表达式
    public static void filter(){
        class OddFilter implements UnaryPredicate<Integer>{
            @Override
            public boolean test(Integer obj) {
                return obj%2==0;
            }
        }
        class GenericFilter{
            public <T> int countIf(Collection<T> c, UnaryPredicate<T> u){
                int count = 0;
                for(T t : c){ if(u.test(t)){  count++; } }
                return count;
            }
        }
        Collection<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        System.out.println(new GenericFilter().countIf(list, (o)->{return o%2==0;}));
    }

    public static <T> void subBound(List<? extends Number> list){ }

    //写一个计算list最大元素的泛型类
    //? super T, ? 的下限是 T 类型
    public static <T extends Number & Comparable<? super T>> T findMax(List<? extends T> list, int left, int right){
        T max = list.get(left);
        //对象比较不能用 > ,所以要用 compaable接口
        for(++left; left<right; left++){
            // list > max
            if(list.get(left).compareTo(max) > 0) {
                max = list.get(left);
            }
        }
        return max;
    }
}
