package com.hujunchina.client.swordoffer;

import java.util.Arrays;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/27 10:28 下午
 * @Version 1.0
 * 输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。
 * 如果有多对数字的和等于s，则输出任意一对即可。
 */
public class A57 {

    public static void main(String[] args) {
        int[] nums = {10,26,30,31,47,60};
        int target = 40;
        System.out.println(Arrays.toString(twoSum(nums, target)));
    }

    //有序数组求和，双指针跑
    public static int[] twoSum(int[] nums, int target) {
        int i=0, j=nums.length-1;
        //先二分查找，找到中间的值
        if(j>10){
            if(nums[j/2]+nums[j/2+1]>target){
                j = j/2+1;
            }else {
                i = j/2;
            }
        }
        while(i!=j){
            if(nums[i]+nums[j]==target){
                return new int[]{nums[i], nums[j]};
            }else if(nums[i]+nums[j]>target){
                j--;
            }else{
                i++;
            }
        }
        return new int[]{nums[i], nums[j]};
    }
}
