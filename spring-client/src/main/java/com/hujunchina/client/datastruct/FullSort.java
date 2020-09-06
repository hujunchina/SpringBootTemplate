package com.hujunchina.client.datastruct;

import java.util.*;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/6 9:07 下午
 * @Version 1.0
 * DFS全排列才是正确的
 */
public class FullSort {
    private Stack<Integer> res = new Stack<Integer>();
    private boolean[] isPutIn = new boolean[10];
    private static int[] nums = {1,2,3,4,5,6,7,8,9,10,11,12};

    public void fullSort(int[] nums){
//        一定要先盘判断输出，递归出口放第一位
        if(res.size()==nums.length){
            while(!res.isEmpty()){
                System.out.print(res.pop() + " ");
            }
            System.out.println();
        }
        for(int i=0; i<nums.length; i++){
            if(isPutIn[i]==true){
                continue;
            }
            if(i>0 && nums[i]==nums[i-1] && isPutIn[i-1]==false){
                continue;
            }
            if(isPutIn[i]==false){

                isPutIn[i] = true;
                res.push(nums[i]);
                fullSort(nums);
                isPutIn[i] = false;
            }
        }

    }

    public static void main(String[] args) {
        FullSort a003FullSort = new FullSort();
//        a003FullSort.fullSort(a003FullSort.nums);
        List<List<Integer>> res3 = new ArrayList<List<Integer>>();
//        fullSort(nums, res3);
        test();
    }

    static List<List<Integer>> res4 = new ArrayList<>();

    public static void fullSort(int[] nums, List<List<Integer>> res3){
        Deque<Integer> res = new ArrayDeque<>();

        List<Integer> res2 = new ArrayList<>();
        boolean[] isPutIn = new boolean[nums.length];
        dfs(nums, isPutIn, res2, 0, res3);
        System.out.println(Arrays.toString(res3.toArray()));
        System.out.println(Arrays.toString(res4.toArray()));
    }

    public static void dfs(int[] nums, boolean[] isPutIn, List<Integer> res, int step, List<List<Integer>> res3){
//        出口
        if(step == nums.length){
            List<Integer>  res5 = res;
            res3.add(res5);
            System.out.println(Arrays.toString(res3.toArray()));
            res4 = res3;
//            step = 0; // 还是return？
            return; // 递归到头了，不要再step=0了
        }
//        分支
        for (int i = 0; i < nums.length; i++) {
            if(isPutIn[i]){
                continue;
            }
            res.add(nums[i]);
            isPutIn[i] = true;
            dfs(nums, isPutIn, res, step+1, res3);
            isPutIn[i] = false;
            res.remove(res.size()-1);
        }
    }

    public static void test() {
        boolean[] isPut = new boolean[12];
        int len = nums.length;
        int[] res = new int[len];
        fullSort(res, nums, isPut, len, 0);
    }
    public static void fullSort(int[] res, int[] nums, boolean[] isPut, int len, int idx){
        if(idx==len){
            System.out.println(Arrays.toString(res));
            return;
        }
        for(int k=0; k<len; k++){
            if(isPut[k]==false){
                res[idx] = nums[k];
                isPut[k] = true;
                fullSort(res, nums, isPut, len, idx+1);
                isPut[k] = false;
            }
        }
    }
}
