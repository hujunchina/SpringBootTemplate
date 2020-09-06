package com.hujunchina.client.datastruct;

import java.util.Stack;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/6 8:48 下午
 * @Version 1.0
 */
public class NodeReverse {
    public static void main(String[] args) {
        reverseList();
    }

    static class Node{
        int val;
        Node next;
        public Node(int val){this.val = val; this.next=null;}
    }
    public static void reverseList(){
        Node node = new Node(0);
        Node head = node;
        for(int i=1; i<10; i++){
            node.next = new Node(i);
            node = node.next;
        }
        System.out.println(head.val);
        Node tail = null;
        Node cur = head;
        while(head.next!=null){
            head = head.next;
            cur.next = tail;
            tail = cur;
            cur = head;
        }
        System.out.println(head.val);
    }

    //    全排列
    static Stack<Integer> res = new Stack<>();
    public static int allSort(boolean[] isIn, int n){
        if(isIn[n]==false){
            isIn[n] = true;
            res.push(n);
            allSort(isIn, n);
            isIn[n] = false;
        }
        for(int i=1; i<n;i++){
            System.out.println(res.pop());
        }
        return 1;
    }
}
