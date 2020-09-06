package com.hujunchina.client.bishi;

import com.hujunchina.common.Question;

import java.util.Scanner;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/6 7:23 下午
 * @Version 1.0
 * Update: 自己解决了！！
 */
@Question(
        question = "求出后一列减去前一列的绝对值的和的最小值",
        condition = "给定一个二维数组行数固定为3，列为n，比如\n" +
                "5 10 5 4 4\n" +
                "1  7  8 4 0\n" +
                "3  4  9 0 3\n" +
                "从每一列选择一个数，求出后一列减去前一列的绝对值的和的最小值\n" +
                "比如这里就是3 4 5 4 4，所以输出是1+1+1+0=3",
        solution = "需要记录每次运算的状态，并比较选择最小，动态规划问题，" +
                "状态矩阵dp[i]表示从0到i的最小值，最后dp[n-1]即为答案，" +
                "转移方程：" +
                "需要注意：不要平面看问题，两列相减不是一对一平行的，而是交差的" +
                "重新写：没有考虑到层层之间关系，后面的index会变，从而影响前面的" +
                "所以才要保存每个diff的index的状态" +
                "第一步：确定状态方程，并保存状态" +
                "第二步：用状态去判断是否为最小" +
                "根本就没有理解什么是状态：状态是当前的，即我for循环到哪个i就是哪的状态，而不是以前的i，" +
                "一开始dp[i][j]老是保存的是i-1的状态，非要反着减导致后面状态无法确保是最小的，" +
                "因为i的状态最小时的下标与i-1时的下标不一样就要返回再判断" +
                "dp[i][j]=arr[i+1][j]-arr[i][j], 我是这样算的【0，N-1】循环，导致一直算的是前一个状态" +
                "导致dp[i][j]表示arr[i-1][j]的状态，而不是arr[i][j]的状态。" +
                "用dp[i][j]表示arr[i][j]的最后状态，即前面的都保证了最小diff" +
                "这个状态一定是问题要求的最终状态，即一定是最小的，而不是临时最小的！！！" +
                "不然，动态规划出错，状态方程出错"
)
public class Ali04 {

    /**
     * 动规方程：
     * dp[i][j] = Math.min(dp[i][j], dp[k][j-1]+diff);
     * @param arr
     * @param N
     * @return
     */
    public static int getMinDiff(int[][] arr, int N){
        //定义动规方程，因为题目是二维的，所以保存每一个状态就要是二维的方程
        int[][] dp = new int[3][N];
        //初始化动规方程，由于要求最小值，所以要把每个状态设为最大，好比较更换
        for(int i=0; i<3; i++){
            for(int j=0; j<N; j++){
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        //初始化第一列的状态，因为没法减了，所以默认差值为0
        for(int i=0; i<3; i++){
            dp[i][0] = 0;
        }
        //循环求动规方程的每个状态，按列来循环，保证后一列减前一列
        for(int j=1; j<N; j++){
            //循环当前列的每个元素，并求他们的状态，即dp[i][j]的最小值
            for(int i=0; i<3; i++){
                //因为可以斜者减，所以需要对每一个dp[i][j]和前一列做3次比较
                //dp[i][j],比较dp[0][j-1],dp[1][j-1],dp[2][j-1]
                for(int k=0; k<3; k++){
                    //求arr[i][j]与arr[k][j-1]差值
                    int diff = Math.abs(arr[i][j]-arr[k][j-1]);
                    //更新状态，斜着的每个dp[k][j-1]都与dp[i][j]比较可能
                    dp[i][j] = Math.min(dp[i][j], dp[k][j-1]+diff);
                }
            }
        }
        //注意，状态的传递性，最后dp[i][N-1]其实是全部最小和，而不是N-1列减N-2列的最小值
        int min = dp[0][N-1];
        min = Math.min(min, dp[1][N-1]);
        min = Math.min(min, dp[2][N-1]);
        return min;
    }

    public static void main(String[] args) {
        int N = 5;
        int[][] arr = {
                {5,7,5,4,4},
                {1,7,8,4,0},
                {3,4,9,0,3},
        };
        System.out.println(getMinDiff(arr, N));
    }
}
