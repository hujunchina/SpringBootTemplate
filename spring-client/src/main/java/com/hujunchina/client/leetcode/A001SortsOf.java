package com.hujunchina.client.leetcode;

import com.hujunchina.common.Question;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/6 8:37 下午
 * @Version 1.0
 */
public class A001SortsOf {
    @Question(
            question = "https://leetcode-cn.com/explore/interview/card/top-interview-questions/261/before-you-start/1106/",
            condition = "给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。\n" +
                    "你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？",
            solution = "常规操作必须要平方时间的，每个数比较n次，n个数，还要求不要额外空间" +
                    "立刻想到redis的bitmap结构，有因为其他元素都出现二次，所以只有按位异或满足，不能用按位与" +
                    "2出现一次，则t的第3位有0 ^ 1 = 1; 2出现两次，有 1 ^ 1 = 0; 正好对t没有影响"
    )
    public static void onceNumber(){
        int[] arr = {4,1,2,3,1,2,3};
        int t=0;
        for(int i=0; i<arr.length; i++){
            t = t^arr[i];
        }
        System.out.println(t);
    }

    @Question(
            question = "给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。\n" +
                    "你可以假设数组是非空的，并且给定的数组总是存在多数元素。",
            solution = "元素出现次数大于1半的数字，一开始想求和或差或位运算都不行，还想中间间隔加负号都不行" +
                    "其实很简答，思维没过来，不是数字之间直接相加减，而是用一个计数器来加减统计！"
    )
    public static void halfNumber(){
        int[] nums = {2,2,1,1,1,2,2};
        // 摩尔投票法：核心就是拼资源消耗
        // 玩一个诸侯争霸的游戏，假设每一次出去打仗都能一换一，最后还能活下来的国家是赢家
        // 如果你国家的人口超过总人口的一半，只要不内斗，最后肯定是赢家
        // 因为最坏的情况是所有国家联合起来对付你
        // 所以，每次遍历，都让该国家出征（作为计数器），遇到自己人就相加，其他人就相减灭亡就换下一个国家出征
        // 从第一个数开始count=1，遇到相同的就加1，遇到不同的就减1，减到0就重新换个数开始计数，总能找到最多的那个
        int count = 0, cur = 0;
        for (int i =0; i < nums.length; i++) {
            if (cur != nums[i]) {
                if (count == 0) {
                    cur = nums[i];
                    count = 1;
                }
                else {
                    count -= 1;
                }
            }
            else {
                count += 1;
            }
        }
        System.out.println(cur);
    }

    @Question(
            question = "编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：\n" +
                    "每行的元素从左到右升序排列。\n" +
                    "每列的元素从上到下升序排列。",
            solution = "观察规律，并把顺序规律结合到比较和下标变化中，一个数大于下标怎么移，小于下标怎么移？" +
                    "如果从左上角开始移动，无法利用顺序规律，从右上角最好"
    )
    public static boolean searchMatrix(){
        int[][] matrix = {{1,2,3,4,15},{2,5,8,12,19}};
        int target = 6;
        if(matrix==null || matrix.length==0){
            return false;
        }
        int c=matrix[0].length-1;
        int r=0;
        //规律的利用，从右上角开始计算
        while(c>=0 && r<matrix.length){
            if(target == matrix[r][c]){
                return true;
            }else if(target > matrix[r][c]){
                r++;
            }else{
                c--;
            }
        }
        return false;
    }

    @Question(
            question = "最长回文子串",
            condition = "判断babad，回文子串为bab",
            solution = "重叠子问题和最优子结构，bab是回文，则xbabx也是回文" +
                    "动态规划状态方程可以记录次数，但无法记录每次的字母" +
                    "一个新思路：字母之间作差，得到一个数字数组，然后统计数组中为0的元素则为bb型，如果两个差值相加为0则为aba型" +
                    "问题：使用sum[i]数组保存从0到i的差值和，如果和为零，则是回文，这是有问题的，如aaabaa会判断错误。" +
                    "还有abcdefghfedcba这样的也会错误，因为0到i中间不能保证都是回文的，也就是缺了状态！" +
                    "一个误区：状态是数字，因为以前用dp都是存的从0到i的某个统计如是否相同或距离" +
                    "这次是判断回文数字，状态就不好设置了" +
                    "状态：子问题是什么？如果aba是回文，则xabax是回文，那么可以把状态设为dp[i,j]表示从i到j的子串是否为回文" +
                    "dp[i][j]=true | false, 表示arr[i][j]是否为回文的状态" +
                    "状态转移：如果arr[i-1] = arr[j+1]相同，则dp[i-1][j+1]=true，否则为false" +
                    "那么初始状态呢？当有一个字母的时候是否为回文，是的！所以dp[i][0]==true" +
                    "一个问题: 为什么i和j的顺序调了就不对了？" +
                    "以前i和j都是从1到len，所以先后无所谓，这次是去上三角，所以i和j的顺序一定不能反；" +
                    "状态转移中，有状态的判断，状态的判断要先找到，才能找到状态的转移！"
    )
    public static String getLongestString(String s){
        String result = "";
        if(s.length()==1 || s==""){
            return s;
        }
        boolean[][] dp = new boolean[s.length()][s.length()];
        char[] arr = s.toCharArray();

        for(int i=0; i<s.length(); i++){
            dp[i][0] = true;
            dp[0][i] = true;
            dp[i][i] = true;
        }

        int maxLen = -1;
        int index = 0;
        for(int j=1; j<arr.length; j++){
            for(int i=1; i<j; i++){
                if(arr[i]==arr[j]){         // 先判断是否相同,如果相同才比较,不相同,必定不是回文, 网状态上靠
                    if(j-i<3){              // 最小子问题,解决aba或bb为true!,是一切的基础
                        dp[i][j] = true;
                    }else{
                        dp[i][j] = dp[i+1][j-1];        // 状态转移，如果内部是回文，则我当前也是回文，而是写在条件上
                    }
                }else{
                    dp[i][j] = false;
                }
//                判断完是否是回文后,要比较找长度
                if(dp[i][j]){
                    if(maxLen < (j-i)){
                        maxLen = j-i;
                        result = s.substring(i, j+maxLen);
                    }
                }
            }
        }
        return result;
    }

    @Question(
            question = "给定一个整数数组 nums 和一个目标值 target，" +
                    "请你在该数组中找出和为目标值的那 两个 整数，" +
                    "并返回他们的数组下标。",
            solution = "hashmap, key是还需要多少的值，如果还需要的值正好等于nums[i],表示和成立" +
                    "value记录下标，更新key可以找多次"
    )
    public static void twoNumber(){
        HashMap<Integer, Integer> hash = new HashMap<>();
        int[] nums = {10,2,7,11,13};
        int target = 9;
        for(int i=0; i<nums.length; i++){
            if(hash.containsKey(nums[i])){
                System.out.println(hash.get(nums[i])+" "+i);
            }else{
                int key = target-nums[i];
                hash.put(key, i);
            }
        }
    }

    @Question(
            question = "最长公共字符",
            condition = "给定的字符长度是不一样的",
            solution = "动态规划：状态dp[i][j]表示arr[i][j]时刻最长的长度" +
                    "状态方程：字母相同就dp[i][j]=dp[i-1][j-1]+1; 不相同就dp[i][j]=0;" +
                    "初始状态：dp[i][j]=0;"
    )
    @Test
    public static void test1(){
        String s1 = "hijsh";
        String s2 = "fish";
        System.out.println(getMaxLengthString(s1, s2));
    }
    public static int getMaxLengthString(String s1, String s2){
        int N = s1.length();
        int M = s2.length();
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        int[][] dp = new int[N][M];
        if(s1.charAt(0)==s2.charAt(0)){
            dp[0][0] = 1;
        }else{
            dp[0][0] = 0;
        }
        for(int i=1; i<N; i++){
            for(int j=1; j<M; j++){
                if(arr1[i] == arr2[j]){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else{
                    dp[i][j] = 0;
                }
            }
        }
        return dp[N-1][M-1];
    }

    @Question(
            question = "最长公共子序列",
            condition = "长度不固定，只要相同即可",
            solution = "动态规划："
    )
    @Test
    public static void test2(){
        String s1 = "hisbh";
        String s2 = "fbibsaaaaah";
        System.out.println(getMaxLengthSequence(s1, s2));
    }
    public static int getMaxLengthSequence(String s1, String s2){
        int N = s1.length();
        int M = s2.length();
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        int[][] dp = new int[N][M];
        if(arr1[0]==arr2[0]){
            dp[0][0] = 1;
        }else{
            dp[0][0] = 0;
        }
        for(int i=1; i<N; i++){
            for(int j=1; j<M; j++){
                if(arr1[i]==arr2[j]){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else{
                    dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
                }
            }
        }
        return dp[N-1][M-1];
    }

    @Question(
            question = "字符串转换最小步数",
            condition = "ALGORITHM 变为 ALTRUISTIC 中间有字符替换等，要求算最小的替换",
            solution = "A L G O R   I   T H M" +
                    "   A L   T R U I S T I C" +
                    "可以看到这样排列然后替换步数最小，先确保字符相同的位，然后就是字母变空格，空格变字母，字母变字母" +
                    "我们需要记录前面一次数组替换，这样直接使用动态规划" +
                    "状态：当前i时替换成功后的最少转换次数" +
                    "状态转移：分三种情况，字母变空格：本质是插入一个字母，前一个状态应该是插入字母的那个状态+1，dp[i][j]=dp[i][j-1]+1" +
                    "同理，空格变字母，是针对i了，dp[i][j]=dp[i-1][j]+1" +
                    "而字母变字母，需要对两个之前的状态都取才行，dp[i][j]=dp[i-1][j-1]+1"
    )
    @Test
    public static void test3() {
        String s1 = "ALGORITHM";
        String s2 = "ALTRUISTIC";

        System.out.println(getMinTransfer(s1,s2));
    }
    public static int tripleMin(int x, int y, int z){
        return x < y ? x < z ? x : z : y < z ? y : z;
    }

    public static int getMinTransfer(String s1, String s2){
        int len = s1.length()+s2.length();
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        int[][] dp = new int[len][len];
//        dp[0][0] = 0;  初始状态有误
//        dp[0][1] = 0;
//        dp[1][0] = 0;

        for(int i=1; i<s1.length(); i++){
            dp[i][0] = i;
        }
        for(int j=1; j<s2.length(); j++){
            dp[0][j] = j;
        }

        for(int i=1; i<s1.length(); i++){
            for(int j=1; j<s2.length(); j++){
                if(arr1[i]==arr2[j]){
                    dp[i][j] = dp[i-1][j-1];
                }else{
                    dp[i][j] = 1+ tripleMin(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[s1.length()-1][s2.length()-1];
    }


    @Question(
            question = "动态规划 dynamic programming",
            condition = "https://www.youtube.com/watch?v=P8Xa2BitN3I" +
                    "计算从起点到终点的路径有多少条",
            solution = "可以使用dfs，但是需要重新计算每个路径，导致很多重复递归" +
                    "使用动态规划，建立一个路径记录，如果已更新则直接用，如果未更新再计算路径"
    )
    @Test
    public static void test4() {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int[][] maze = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int i1 = 0; i1 < M; i1++) {
                maze[i][i1] = scanner.nextInt();
            }
        }
        int[][] paths = new int[N][M];
        for (int i = 0; i < N; i++) {
            Arrays.fill(paths[i], 0);
        }
        System.out.println(getHowManyPaths(maze,0,0,N-1,M-1,paths));
    }
    public static int getHowManyPaths(int[][] maze, int x, int y, int X, int Y, int[][] paths){
        if(x>X || y>Y || maze[x][y]==0){
            return 0;
        }
        if(x==X && y==Y){
            return 1;
        }
        if(paths[x][y]==0){
            paths[x][y] = getHowManyPaths(maze, x+1, y, X,Y,paths)+getHowManyPaths(maze,x,y+1,X,Y,paths);
        }
        return paths[x][y];
    }
    /*
4 4
1 1 1 1
1 0 1 1
1 1 0 1
1 1 1 1

8 8
1 1 1 1 1 1 1 1
1 1 0 1 1 1 0 1
1 1 1 1 0 1 1 1
0 1 0 1 1 0 1 1
1 1 0 1 1 1 1 1
1 1 1 0 0 1 0 1
1 0 1 1 1 0 1 1
1 1 1 1 1 1 1 1
 */

}

