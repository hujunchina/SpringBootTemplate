package com.hujunchina.client.huawei;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/29 7:01 下午
 * @Version 1.0
 * 明明想在学校中请一些同学一起做一项问卷调查，为了实验的客观性，他先用计算机生成了N个1到1000之间的随机整数（N≤1000），
 * 对于其中重复的数字，只保留一个，把其余相同的数去掉，不同的数对应着不同的学生的学号。
 * 然后再把这些数从小到大排序，按照排好的顺序去找同学做调查。请你协助明明完成“去重”与“排序”的工作
 * https://www.nowcoder.com/question/next?pid=1088888&qid=36846&tid=36687870
 *
 * 思路：去重用Set，排序用sort
 * 如果手写的话，还是要练一下排序算法
 */
import java.util.Scanner;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

public class A002 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(in.hasNextInt()){
            int n = in.nextInt();
            int[] arr = new int[n];
            Set<Integer> set = new HashSet<>();
            for(int i=0; i<n; i++){
                int tmp = in.nextInt();
                if(!set.contains(tmp)){
                    set.add(tmp);
                    arr[i] = tmp;
                }
            }
            Arrays.sort(arr);
            for(int t=0; t<n; t++){
                if(arr[t]!=0){
                    System.out.println(arr[t]);
                }
            }
        }
    }
}
