package com.hujunchina.client.datastruct;

import java.util.Arrays;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/6 10:57 下午
 * @Version 1.0
 */
public class MergeSort {
    public void sort(int[] arr, int left, int right){
        if(left < right){
            int middle = (right+left)/2;
            sort(arr, left, middle);
            sort(arr, middle+1, right);
            MergeSortedArray(arr, left, middle, middle+1, right);
        }
    }
    //本质是两个有序数组合并
    public void MergeSortedArray(int[] arr, int left, int middleL, int middleR, int right){
        int[] cpy_arr = new int[arr.length];
        int idx=0;
        int l = left;
        while(left<=middleL && middleR<=right){
            if(arr[left]<arr[middleR]){
                cpy_arr[idx++] = arr[left++];
            }else if(arr[left]>arr[middleR]){
                cpy_arr[idx++] = arr[middleR++];
            }else{
                cpy_arr[idx++] = arr[left];
                left++;
                middleR++;
            }
        }
        while(left<=middleL){
            cpy_arr[idx++] = arr[left++];
        }
        while(middleR<=right){
            cpy_arr[idx++] = arr[middleR++];
        }
        for(int i=0; i<idx; i++){
            arr[l++] = cpy_arr[i];
        }
    }

    public static void main(String[] args) {
        MergeSort mergeSort = new MergeSort();
        int[] arr = {5, 2, 1, 3, 6, 4};
        int[] arr2 = {6,5,4,3,2,1};
        mergeSort.sort(arr, 0, 5);
        System.out.println(Arrays.toString(arr));
    }
}
