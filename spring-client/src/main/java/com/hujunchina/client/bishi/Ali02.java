package com.hujunchina.client.bishi;

import com.hujunchina.common.Question;

import java.util.Arrays;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/6 6:09 下午
 * @Version 1.0
 */

@Question(
        question = "最长上升字符串 Longest Increasing Subsequence",
        condition = "上升字符串为s[i]>=s[i-1], 如aaa是，acb不是" +
                "给定n个上升字符串，选择任意拼在一起，最长",
        solution = "动态规划：状态dp[][]+最后一步（一定是得到最长字符串）" +
                "1.确定状态：最后一步+优化子问题" +
                "2.转移方程：dp[i] = max(dp[i], dp[i]+arr[i])" +
                "3.初始条件和边界：dp[0]=0, 结束是遍历完String数组" +
                "4.执行顺序："
)
public class Ali02 {
    public static void main(String[] args) {
        int len = 6;
        String[] strs = {
                "ddddeeef",
                "aaabbb",
                "bcccddd",
                "abc",
                "eeff",
                "bcccdd",
        };
        System.out.println(longestSeq(strs));
    }

    public static Integer longestSeq(String[] strs){
        Integer max = 0;
        Arrays.sort(strs);
        System.out.println(Arrays.toString(strs));
        int[] dp = new int[strs.length]; //dp状态表示当前i时的最长结果
        dp[0] = strs[0].length();
        for(int i=0; i<strs.length; i++){  // 需要每次循环最后拼接的字符串
            //因为dp记录了第0到i-1处的所有max长度，所以只要第i次和任何一个拼接上都能得到max长度
            for(int j=0; j<i; j++){  // 把i前面所有字符串都比较一下
                char tail = strs[j].charAt(strs[j].length()-1); //所以这里去j的尾部
                if(tail <= strs[i].charAt(0)){  //与i头部比较看是否可以拼接
                    System.out.println(strs[j]);
                    dp[i] = Math.max(dp[i], dp[j]+strs[i].length()); //可以拼接就更新其值
                    //因为是更新当前i的长度，所以第一个是dp[i]，
                    //然后通过加上拼接的i长度到原来就有的长度，每次循环比较保证了dp存储的是最大的长度
                    //不用担心长度丢失或跳跃
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
