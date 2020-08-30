package com.hujunchina.client.swordoffer;

import java.util.Arrays;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/29 11:12 上午
 * @Version 1.0
 * 从扑克牌中随机抽5张牌，判断是不是一个顺子，即这5张牌是不是连续的。
 * 2～10为数字本身，A为1，J为11，Q为12，K为13，而大、小王为 0, 可以看成任意数字。A 不能视为 14。
 * 思路：先排序，然后再遍历，如果孔雀空缺了，就用0补，0用完了就over。
 */
public class A61 {
    public static void main(String[] args) {

    }

    public static boolean isStraight(int[] nums){
        Arrays.sort(nums);
        int king = 0;
        for (int i=0; i<nums.length-1; i++){
            if (nums[i]==0){
                king++;
            } else {
                int tmp = nums[i+1]-nums[i];
                if (tmp==0){ //same num
                    return false;
                } else if(tmp==2){
                    if(king<=0){
                        return false;
                    } else {
                        king --;
                    }
                } else if(tmp==3 && king!=2){
                    return false;
                } else if (tmp>3){
                    return false;
                }
            }
        }
        return true;
    }
}
