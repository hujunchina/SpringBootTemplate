package com.hujunchina.client.swordoffer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/28 10:52 下午
 * @Version 1.0
 * 给定一个数组 nums 和滑动窗口的大小 k，请找出所有滑动窗口里的最大值。
 */
public class A59 {
    public static void main(String[] args) {
        int[] nums = {1,3,-1,-3,5,3,6,7};
        int k = 3;
        System.out.println(Arrays.toString(maxSlidingWindow(nums, k)));
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        ArrayList<Integer> res = new ArrayList<>();
        int max = 0;
        int left=0, right=0;
        for(int i=0; i<nums.length; i++){
            if(nums[max] < nums[i]){
                max = i;
            }
            if(i==k-1){ //第一个
                res.add(nums[max]);
                left = 0;
                right = i;
            } else if (i>=k){
                if (left != max){ //左边不是最大值
                    res.add(nums[max]);
                    left++;
                } else { //左边是最大值，去掉后要寻找最大值
                    left++;
                    max = left;
                    for(int t = left; t<=i; t++){
                        if(nums[max] < nums[t]){
                            max = t;
                        }
                    }
                    res.add(nums[max]);
                }
            }
        }
        int[] r = new int[res.size()];
        for(int i=0; i<res.size(); i++){
            r[i] = res.get(i);
        }
        return r;
    }

}
