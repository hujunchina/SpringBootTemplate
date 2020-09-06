package com.hujunchina.client.datastruct;

import java.util.Arrays;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/6 10:32 下午
 * @Version 1.0
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] nums = {0,30,2,4,1,8};
        heapSort(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void heapSort(int[] nums){
//        先建堆
        for(int i=nums.length/2-1; i>=0; i--){
//            调整
            adjustDown(nums, i, nums.length);
        }
//        排序
        for(int i=nums.length-1; i>0; i--){
            int tmp = nums[i];
            nums[i] = nums[0];
            nums[0] = tmp;
            adjustDown(nums, 0, i);
        }
    }

    public static void adjustDown(int[] nums, int k, int len){
        int tmp = nums[k];
        int parent, child;
        for(parent=k; (parent*2+1)<len; parent=child){
            child = parent*2+1;
            if(child<len-1 && nums[child]<nums[child+1]){  // 先找到左右最大的值
                child++;
            }
            if(tmp>=nums[child]){    // 父节点比左右都大，不用比了
                break;
            }else{
                nums[parent] = nums[child]; // 子节点有一个大于父节点，交换
            }
        }
        nums[parent] = tmp; // 复原
    }
}
