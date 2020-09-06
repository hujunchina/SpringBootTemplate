package com.hujunchina.client.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/3 7:14 下午
 * @Version 1.0
 * 排序算法
 */
public class Sorts {
    public static void main(String[] args) {
        arrayIsObject();
    }

    public static void collectionSort(){
        class Person implements Comparable<Person> {

            int age;
            String name;

            public Person(int age, String name){
                this.age = age;
                this.name = name;
            }
            @Override
            public int compareTo(Person person) {
                return this.age-person.age;
            }
        }
        ArrayList<Integer> list = new ArrayList<>();
        Collections.addAll(list, 234,34,1);
        System.out.println(list.toString());
        Collections.sort(list);
        System.out.println(list.toString());

        ArrayList<String> list1 = new ArrayList<>();
        Collections.addAll(list1, "hujun", "love", "liujihong");
        System.out.println(list1.toString());
        Collections.sort(list1);
        System.out.println(list1.toString());

        ArrayList<Person> list2 = new ArrayList<>();
        Collections.addAll(list2, new Person(12,"h"), new Person(9,"liu"));
        System.out.println(list2.toString());
        Collections.sort(list2);
        System.out.println(list2.toString());

        ArrayList<Integer> c = new ArrayList<>();
        c.add(1);
        c.add(2);
        c.add(3);
        System.out.println(Collections.max(c));
        Collections.sort(c);
        Collections.reverse(c);
        Collections.shuffle(c);
        Collections.binarySearch(c, 2);
        Collections.swap(c, 1, 3);
        Collections.rotate(c, 2);
        List<Integer> safe = Collections.synchronizedList(c);
    }

    public static void arraySort(){
        String[] as = {"a","C","b","D"};
        Arrays.sort(as,String.CASE_INSENSITIVE_ORDER);
        Arrays.sort(as, Collections.reverseOrder());
    }


    public static void copyArray(){
        int[] arr = {9, 7, 6, 4, 2, 8, 1, 11, 3, 0};
        int[] arr_img = java.util.Arrays.copyOf(arr, arr.length);
        System.out.println(java.util.Arrays.toString(arr_img));
        java.util.Arrays.fill(arr, 1);
        System.out.println(java.util.Arrays.toString(arr));
    }

    public static void arrayIsObject(){
        int[] arr = {1,2,3};
        Object obj = 2;
        //arr invoke object method
        arr.toString();
        if( arr instanceof Object){
            System.out.println("array is object;");
            System.out.println(((Object) arr).getClass().getSuperclass().getName());
        }
    }


    public static void selectSort(){
        int[] arr = {9, 7, 6, 4, 2, 8, 1, 11, 3, 0};
        System.out.println(java.util.Arrays.toString(arr));
//        select the min elem to order place
        int min = 0;
        for(int i=0; i<arr.length; i++){
            min = i;
            for(int j=i; j<arr.length; j++){
                if(arr[j] < arr[min]){
                    min = j;
                }
            }
            int tmp = arr[min];
            arr[min] = arr[i];
            arr[i] = tmp;
        }
        System.out.println(java.util.Arrays.toString(arr));
    }

    public static void bubbleSort(int[] arr){
        int times = 0;
        System.out.println(java.util.Arrays.toString(arr));
//        loop all elements
        for (int i = 0; i < arr.length-1; i++) {
//            loop j and j-1
            for(int j=arr.length-1; j>i; j--){
                if(arr[j-1] > arr[j]){
                    int tmp = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = tmp;
                    times++;
                }
            }
        }
        System.out.println(java.util.Arrays.toString(arr));
        System.out.println(times);
    }

    public static void checkSort(){
        int[] arr = {9, 7, 6, 4, 2, 8, 1, 11, 3, 0};
        System.out.println(java.util.Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if(arr[j]<arr[i]){
                    int tmp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = tmp;
                }
            }
        }
        System.out.println(java.util.Arrays.toString(arr));
    }


}
