package com.hujunchina.client.bishi;

import com.hujunchina.common.Question;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/6 6:05 下午
 * @Version 1.0
 */
@Question(
        question = "有一叠扑克牌，每张牌介于1和10之间" +
                "给10个数，表示1-10每种牌有几张，问最少要多少次能出完",
        condition = "有四种出牌方法：\n" +
                "单出1张\n" +
                "出2张对子\n" +
                "出五张顺子，如12345\n" +
                "出三连对子，如112233",
        solution = "DFS,其实是模拟试错，把所有情况都模拟一遍，然后选出最优" +
                "与普通模拟区别是：模拟没有分叉，一直向前模拟就行，如模拟时间从1点到2点，对中间发生的事处理；" +
                "但DFS对付有分叉的情况，模拟会改变状态，有多种情况，这时就要对状态进行恢复。" +
                "核心：模拟所有情况+递归搜集所有情况+递归这一情况后恢复状态。" +
                "递归的树的层高即操作次数，模拟次数。找出树层最低的即最优解。" +
                "其实每递归一次就是去模拟一次情况。"
)
public class Ali01 {
    private int[] arr;
    Integer ans = 100;
    public Ali01(int[] arr){
        this.arr = arr;
    }

    public void solution(int dep){
        boolean finish = true;
        int sum = 0;
        for(int i=1; i<=10; i++){
            sum+=1;
            if(arr[i]!=0){
                finish = false;
            }
        }
        if(finish){
            if(ans > dep ){
                ans = dep;
            }
            return;
        }
        if(dep+sum/6+1>= ans){
            return;
        }
        for(int i=1; i<=10; i++){
            //      第一步：先出顺子
            if(i<=8 && arr[i]>=2 && arr[i+1]>=2 && arr[i+2]>=2){
                arr[i]-=2;
                arr[i+1]-=2;
                arr[i+2]-=2;
                solution(dep+1);
                arr[i]+=2;
                arr[i+1]+=2;
                arr[i+2]+=2;
            }

//      第二步：先出三联对
            if(i<=6 && arr[i]>=1 && arr[i+1]>=1 && arr[i+2]>=1 && arr[i+3]>=1 && arr[i+4]>=1){
                arr[i]-=1;
                arr[i+1]-=1;
                arr[i+2]-=1;
                arr[i+3]-=1;
                arr[i+4]-=1;
                solution(dep+1);
                arr[i]+=1;
                arr[i+1]+=1;
                arr[i+2]+=1;
                arr[i+3]+=1;
                arr[i+4]+=1;
            }

//      第三步: 出对子或单张
            if(arr[i]>=2){
                arr[i]-=2;
                solution(dep+1);
                arr[i]+=2;
            }
            if(arr[i]>=1){
                arr[i]-=1;
                solution(dep+1);
                arr[i]+=1;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {0, 3, 3, 3, 3, 2, 3, 0, 0, 0, 0};
        Ali01 poker = new Ali01(arr);
        poker.solution(0);
        System.out.println(poker.ans);
    }

}
