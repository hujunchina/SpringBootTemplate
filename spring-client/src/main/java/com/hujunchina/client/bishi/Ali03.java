package com.hujunchina.client.bishi;

import com.hujunchina.common.Question;

import java.util.Scanner;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/6 7:02 下午
 * @Version 1.0
 */
@Question(
        question = "可跳跃的迷宫",
        condition = "一个地图n*m，包含1个起点，1个终点，其他点包括可达点和不可达点。" +
                "每一次可以：上下左右移动，或使用1点能量从（i,j)移动到（n-1-i, m-1-j)，最多可以使用5点能量" +
                "并打印路径",
        solution = "DFS走迷宫问题，模拟所有的情况，但是要把走过的路径置为零" +
                "并且飞行终点无法落在墙上"
)
public class Ali03 {
    static int ans = Integer.MAX_VALUE;
    public static void getMazePath(int path, int point, int[][] maze, int N, int M, int inX, int inY, int outX, int outY){
        //递归的出口
        if(inX==outX && inY==outY){
            ans = Math.min(ans, path);
            return;
        }
        //递归的边界
        if(inX<0 || inY<0 || inX>=N || inY>=M){
            return;
        }
        //根据递归条件，开始递归
        if(maze[inX][inY]==1){
            maze[inX][inY] = 0; //要把走过的路径置为零，不然会重复走，能量点无效使用
            //根据飞行条件，只能跳到反方向
            if(point>0 && maze[N-1-inX][M-1-inY]==1){
                getMazePath(path+1, point-1, maze, N, M,N-1-inX, M-1-inY, outX, outY);
            }
            getMazePath(path+1, point, maze, N, M, inX-1, inY, outX, outY);
            getMazePath(path+1, point, maze, N, M, inX+1, inY, outX, outY);
            getMazePath(path+1, point, maze, N, M, inX, inY-1, outX, outY);
            getMazePath(path+1, point, maze, N, M, inX, inY+1, outX, outY);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = 4;
        int M = 4;
        int inX = 0;
        int inY = 2;
        int outX = 3;
        int outY = 0;

        int[][] maze = {
                {1,1,1,1},
                {0,1,1,1},
                {0,1,0,1},
                {1,1,0,1}};

        getMazePath(0, 5, maze, N, M, inX, inY, outX, outY);
        System.out.println(ans);
    }
}
