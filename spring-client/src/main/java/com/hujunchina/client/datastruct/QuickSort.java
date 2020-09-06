package com.hujunchina.client.datastruct;

import java.util.Arrays;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/6 6:22 下午
 * @Version 1.0
 */
public class QuickSort {
    public static boolean stringCompare(String a, String b){
        int i=0;
        if(a.compareTo(b)>0){  // a>b
            return false;
        }else{  //a<b
            return true;
        }
    }
    public static int findPivot(String[] strings, int left, int right){
        int pivot = left;
        String tmp;
        if(left<right){
            while(left<right && stringCompare(strings[pivot], strings[right])){
                right--;
            }
            tmp = strings[pivot];
            strings[pivot] = strings[right];
            strings[right] = tmp;
            pivot = right;

            while(left<right && stringCompare(strings[left], strings[pivot])){
                left++;
            }
            tmp = strings[pivot];
            strings[pivot] = strings[left];
            strings[left] = tmp;
            pivot = left;
        }
        return pivot;
    }

    public static void quickSort(String[] strings, int left, int right){
        if(left<right){
            int pivot = findPivot(strings, left, right);
            quickSort(strings, left, pivot);
            quickSort(strings, pivot+1, right);
        }
    }

    //第二个版本
    //find next fine pivot
    public int sort_pivot(int[] arr,int left,int right,int pivot){
        while(left<right){
            System.out.println(pivot);
            if(arr[pivot]<arr[right]){
                right--;
            }else{
                int tmp = arr[pivot];
                arr[pivot] = arr[right];
                arr[right] = tmp;
                pivot = right;
            }
            if(arr[left]<arr[pivot]){
                left++;
            }else{
                int tmp = arr[pivot];
                arr[pivot] = arr[left];
                arr[left] = tmp;
                pivot=left;
            }
        }
        return pivot;
    }
    public void sort(int[] arr,int left,int right){
        int pivot = left;
        if(left < right){
            int pivot_fiound = sort_pivot(arr, left, right, pivot);
            System.out.println(Arrays.toString(arr));
            sort(arr, left, pivot_fiound-1);
            sort(arr, pivot_fiound+1, right);
        }
    }

    public static void test2(String[] args) {
        QuickSort quickSort = new QuickSort();
        int[] arr = {5, 2, 1, 6, 3, 4};
        int[] arr2 = {6,5,4,3,2,1};
        quickSort.sort(arr2, 0, 5);
    }
}
