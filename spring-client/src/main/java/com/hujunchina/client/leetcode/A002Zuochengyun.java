package com.hujunchina.client.leetcode;

import com.hujunchina.common.Question;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/6 10:39 下午
 * @Version 1.0
 */
public class A002Zuochengyun {
    @Question(
            question = "生成窗口最大值数组",
            condition = "有一个整型数组arr和一个大小为w的窗口从数组的最左边滑到最右边，" +
                    "窗口每次向右边滑一个位置",
            solution = "先找出第一个窗口的最大值，后面移动窗口只要比较最大值和新元素即可" +
                    "但是这样有问题：窗口移动了，最大值可能没有了，要动态比较最大值" +
                    "一个不行就计算两个，试了很久，无法保证第二个移动掉" +
                    "正解：利用双端队列来实现窗口最大值的更新。" +
                    "首先生成双端队列qmax，qmax中存放数组arr中的下标" +
                    "qmax的first引用指向链表头，last引用指向双端队列尾部" +
                    "当addLast时，节点的prev和next链接起来，在右边增加新节点" +
                    "当qmax大小正好为窗口大小时，出队first指向的节点，并输出" +
                    "当qmax大小小于窗口时，读取元素并比较与last指向节点的大小" +
                    "利用双端队列后插入前取出特点，把队列长度控制为窗口大小，变相模拟了窗口移动"
    )
    List<Integer> list = Arrays.asList(100,13,12,1,7,36,4,5);;
    private int windowSize = 4;
    public Integer[] getMaxWindowNum(){
        LinkedList<Integer> qmax = new LinkedList<>();
        Integer[] res = new Integer[list.size()-windowSize+1];
        int index=0;
        for(int i=0; i<list.size();i++){
            while(!qmax.isEmpty() && list.get(qmax.peekLast()) <= list.get(i)){
                qmax.pollLast();
            }
            qmax.addLast(i);
            if(qmax.peekFirst()==i-windowSize){
                qmax.pollFirst();
            }
            if(i>=windowSize-1){
                res[index++] = qmax.peekFirst();
                System.out.println(list.get(qmax.peekFirst()));
            }
        }
        return res;
    }

    @Question(
            question = "单调栈结构",
            condition = "给定一个不含有重复值的数组arr，找到每个i左边和右边离i最近且值比arr[i]小的下标" +
                    "返回所有位置相应的信息，不存在用-1表示" +
                    "进阶问题：给定一个可能含有重复值的数组arr，找到i左边和右边离i最近且比arr[i]小的位置" +
                    "如果arr长度为N，实现原问题和进阶问题的解法，时间复杂度都达到O(N)。",
            solution = "直接弹出比较，数据会丢失无法比较！" +
                    "抓住左右，而不是最大最小，更不是排序，只要左边或右边有比我小的就满足条件了" +
                    "利用栈的特点，如果数组当前元素比栈顶元素小则找到右边，直接弹出输出，循环延迟一下" +
                    "如果比栈顶元素大则压入栈，并找到当前元素left为栈顶元素下标" +
                    "其实入栈的时候就立刻知道left了，只是不确定right，比较找最小就是找right" +
                    "栈无序的，要想原序输出，还要使用一个数组。"
    )
    private Stack<Node> stack = new Stack<>();;
    private Integer[] arr = {10,9,8,7,6,5,4};
    private Node[] res = new Node[arr.length];

    class Node{
        Integer val;
        int left;
        int right;
        int index;
        public Node(Integer val, int index, int left, int right){
            this.index = index;
            this.left = left;
            this.right = right;
            this.val=val;
        }
    }
    public void originQuestion(){
        for(int i=0; i<arr.length; i++){
            if(stack.empty()){
                stack.push(new Node(arr[i], i, -1, -1));
            }else{
                if(stack.peek().val < arr[i]){
                    stack.push(new Node(arr[i], i, stack.peek().index, -1));
                }else{
                    Node node = stack.pop();
                    node.right = i;
                    i = i-1;
                    res[node.index]=node;
//                    System.out.format("%d : {%d, %d}%n", node.val, node.left, node.right);
                }
            }
        }
        while(!stack.empty()){
            Node node = stack.pop();
            res[node.index]=node;
//            System.out.format("%d : {%d, %d}%n", node.val, node.left, node.right);
        }
        for(Node node : res){
            System.out.format("%d,%d : {%d, %d}%n", node.index, node.val, node.left, node.right);
        }
    }

    @Question(
            question = "求矩阵中最大某区域（全1）",
            condition = "以某点位坐标，共八个方向上均可作为新连接点",
            solution = "DFS找到最大区域，通过状态改变来模拟所有可能情况" +
                    "国外思路确实清晰，找到最大矩阵区域，然后找四周"
    )
    public static int getBiggestRegion(int[][] matrix){
        int maxRegion = 0;
//        boolean visited[][] = new boolean[matrix.length][matrix[0].length];
        for(int row = 0; row < matrix.length; row++){
            for(int column=0; column < matrix[row].length; column++){
                if(matrix[row][column] == 1){
                    int size = getBiggestSize(matrix, row, column);
                    maxRegion = Math.max(maxRegion, size);
                }
            }
        }

        return maxRegion;
    }
    public static int getBiggestSize(int[][] matrix, int row, int column){
        if(row<0 || column<0 || row>=matrix.length || column>=matrix[row].length){
            return 0;
        }
        if(matrix[row][column]==0){
            return 0;
        }
//        访问过得要置零，因为不需要再分支了，所以不需要再恢复。
        matrix[row][column]=0;
        int size = 1;
        for (int i = row-1; i <= row+1; i++) {
            for (int i1 = column-1; i1 <= column+1; i1++) {
                if(i!=row || i1!=column){  //skip myself
                    size += getBiggestSize(matrix, i, i1);
                }
            }
        }
        return size;
    }
}
