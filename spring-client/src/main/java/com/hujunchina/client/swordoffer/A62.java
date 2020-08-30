package com.hujunchina.client.swordoffer;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/29 11:53 上午
 * @Version 1.0
 * 0,1,,n-1这n个数字排成一个圆圈，从数字0开始，每次从这个圆圈里删除第m个数字。
 * 求出这个圆圈里剩下的最后一个数字。
 * 思路：直接通过bool数组记录判断，如果false输出；如果是true跳过
 */
public class A62 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(last(70866, 116922));
        long stop = System.currentTimeMillis();
        System.out.println((stop-start)/1000);
    }

    public static int last(int n, int m){
        boolean[] arr = new boolean[n];
        int sum = (n-1)*n/2;
        int tmpSum = 0;
        int index = 0;
        int left = n;
        int count = 0;
        while(left != 1){ //不剩最后一人
            index = index % n; //模拟循环
            if(arr[index]==false){
                count++;
                if(count==m){
                    arr[index]=true;
                    tmpSum+=index;
                    left--;
                    count=0;
                }
            }
            index++;
        }
        return sum-tmpSum;
    }
}
