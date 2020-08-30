package com.hujunchina.client.swordoffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/27 10:51 下午
 * @Version 1.0
 * 输入一个正整数 target ，输出所有和为 target 的连续正整数序列（至少含有两个数）。
 * 序列内的数字由小到大排列，不同序列按照首个数字从小到大排列。
 */
public class A57_2 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(findContinuousSequence(15)));
    }

    //直观法
    public static int[][] findContinuousSequence(int target) {
        int sum = 0;
        List<Integer> res = new ArrayList<>();
        List<List<Integer>> resList = new ArrayList<>();
        for(int j=1; j<=target/2+1; j++) {
            sum = 0;
            res = new ArrayList<>();
            for (int i = j; i <= target/2+1; i++) {
                sum += i;
                if (sum == target) {
                    res.add(i);
                    resList.add(res);
                } else if (sum > target) {
                    break;
                } else {
                    res.add(i);
                }
            }
        }
        int[][] resArr = new int[resList.size()][];
        int k=0,t=0;
        for(List<Integer> r : resList){
            int[] tmp = new int[r.size()];
            t=0;
            for(Integer i : r){
                tmp[t++] = i;
            }
            resArr[k++] = tmp;
        }
        //System.out.println(resList.toString());
        return resArr;
    }

    //优化，使用求和方法, 滑动窗口
    public int[][] findContinuousSequence2(int target) {
        int i = 1; // 滑动窗口的左边界
        int j = 1; // 滑动窗口的右边界
        int sum = 0; // 滑动窗口中数字的和
        List<int[]> res = new ArrayList<>();

        while (i <= target / 2) {
            if (sum < target) {
                // 右边界向右移动
                sum += j;
                j++;
            } else if (sum > target) {
                // 左边界向右移动
                sum -= i;
                i++;
            } else {
                // 记录结果
                int[] arr = new int[j-i];
                for (int k = i; k < j; k++) {
                    arr[k-i] = k;
                }
                res.add(arr);
                // 左边界向右移动
                sum -= i;
                i++;
            }
        }

        return res.toArray(new int[0][]);
    }
}
