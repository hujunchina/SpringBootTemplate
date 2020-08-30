package com.hujunchina.client.swordoffer;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/26 10:50 下午
 * @Version 1.0
 * 1到n求和，不能使用乘除和for,if-else,?:等
 */
public class A64 {
    public static void main(String[] args) {

        System.out.println(sum(5));
        System.out.println(sumRecursive(5));
    }

    // 使用pow，但是不好
    public static int sum(int n) {
        return (n+(int)Math.pow(n,2))>>1;
    }

    // 使用递归表示，很好
    public static int sumRecursive(int n) {
        if(n==1){
            return 1;
        } else {
            return sumRecursive(n-1)+n;
        }
    }
}
