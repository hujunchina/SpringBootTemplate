package com.hujunchina.client.huawei;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/29 6:26 下午
 * @Version 1.0
 * 某商店规定：三个空汽水瓶可以换一瓶汽水。小张手上有十个空汽水瓶，她最多可以换多少瓶汽水喝？”答案是5瓶，
 * 方法如下：先用9个空瓶子换3瓶汽水，喝掉3瓶满的，喝完以后4个空瓶子，用3个再换一瓶，喝掉这瓶满的，
 * 这时候剩2个空瓶子。然后你让老板先借给你一瓶汽水，喝掉这瓶满的，喝完以后用3个空瓶子换一瓶满的还给老板。
 * https://www.nowcoder.com/test/question/fe298c55694f4ed39e256170ff2c205f
 *
 * 思路：模拟题，直接模拟过程，最后剩余空瓶为2，再加一（处理特殊条件）
 * 更新空瓶 ： n-=【用的空瓶+得到空瓶】
 */
import java.util.Scanner;
public class A001 {
    public static void main(String[] args) {
        int n;
        int res=0;
        Scanner in = new Scanner(System.in);
        while(in.hasNextInt()){
            n = in.nextInt();
            res = 0;
            if(n==0){
                return;
            }
            while(n>=3){
                res += n/3;
                n = n-(n/3)*3+n/3;
            }
            if( n==2 ){
                res += 1;
            }
            System.out.println(res);
        }
    }
}
