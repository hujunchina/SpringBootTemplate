package com.hujunchina.client.swordoffer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/26 11:25 下午
 * @Version 1.0
 * 在一个数组 nums 中除一个数字只出现一次之外，其他数字都出现了三次。
 * 请找出那个只出现一次的数字。
 */
public class A56_2 {
    public static void main(String[] args) {
        System.out.println(findUniqueNumber(new int[]{5,20,20,20}));
        System.out.println(findUniqueNum(new int[]{5, 4, 4, 4}));
        System.out.println(findUnique(new int[]{5, 4, 4, 4}));
    }

    //判断重复，最直白的方式，用map比对
    public static int findUniqueNumber(int[] nums) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int i=0; i<nums.length; i++) {
            if(count.containsKey(nums[i])){
                count.put(nums[i], 2);
            }else{
                count.put(nums[i], 1);
            }
        }
        for (Integer key : count.keySet()) {
            if (count.get(key)==1){
                return key;
            }
        }
        return 1;
    }

    //还可以用位图方式判断
    public static int findUniqueNum(int[] nums){
        int t = 0;
        for (int i=0; i<nums.length; i++) {
            // 按位或会不断累加
            // 按位与永远都是0
            // 按位异或可以，但是重复的数是3次很不好只能搞成6次
            t = t ^ nums[i];
        }
        return t;
    }

    //排序算法接近线性运算，是无代价算法，可以用，要经常用
    //排序要优于hashmap映射
    public static int findUnique(int[] nums){
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if(i==0 && nums[0]!=nums[1]){
                return nums[0];
            }
            if(i==nums.length-1 && nums[nums.length-1]!=nums[nums.length-2]) {
                return nums[nums.length-1];
            }
            if(nums[i]!=nums[i+1] && nums[i]!=nums[i-1]){
                return nums[i];
            }
        }
        return nums[0];
    }
}
