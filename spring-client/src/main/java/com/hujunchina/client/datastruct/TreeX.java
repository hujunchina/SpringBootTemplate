package com.hujunchina.client.datastruct;

import com.hujunchina.common.Question;

import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/6 10:50 下午
 * @Version 1.0
 */
@Question(
        question = "分别用递归和非递归方式实现二叉树先序、中序和后序遍历",
        condition = "使用数组建树",
        solution = "建树过程可以使用数组来赋值，数组本身有树的关系"
)
public class TreeX {
    static class Node{
        public int val;
        public Node left;
        public Node right;
        public Node(int val){
            this.val = val;
            left = null;
            right = null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return val == node.val &&
                    Objects.equals(left, node.left) &&
                    Objects.equals(right, node.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(val, left, right);
        }
    }

    public static void preOrder(Node root){
        if(root==null){
            return;
        }
        System.out.print(root.val+",");
        preOrder(root.left);
        preOrder(root.right);
    }

    public static void inOrder(Node root){
        if(root==null){
            return;
        }
        inOrder(root.left);
        System.out.print(root.val+",");
        inOrder(root.right);
    }

    public static void postOrder(Node root){
        if(root==null){
            return;
        }
        postOrder(root.left);
        postOrder(root.right);
        System.out.print(root.val+",");
    }

    @Question(solution = "思路:先序遍历先根节点,这里如何解决先左后右访问和回溯右子树两个问题." +
            "先左后右:把左右子节点是否存在放在一个if判断里面,就有了先后入栈关系." +
            "回溯:需要考虑多层左节点是否访问,不能用单一的tmp来保存,会丢失右子树的,需要一个tmp栈来保存")
    public static void preOrder2(Node root){
        Stack<Node> stack = new Stack<>();
        Stack<Node> tmpStack = new Stack<>();
        stack.push(root);
        tmpStack.push(root);
        boolean isVisited = false;
        while(!stack.empty()){
            Node node = stack.pop();
            if(!isVisited){
                System.out.print(node.val+",");
            }
            if(node.left!=null && !isVisited){
                stack.push(node.left);
                tmpStack.push(node.left);
//                isVisited = false;  这个开关不需要
            }else if(node.right!=null){
                stack.push(node.right);
                tmpStack.push(node.right);
                isVisited = false;      //当加入右节点时,可把右节点当成一个新的根节点继续操作,子树,所以要重新设置
            }else{
                if(!tmpStack.empty()){
                    stack.push(tmpStack.pop());
                    isVisited = true;   // tmp栈弹出来的都是访问过的节点,所以要true
                }
            }
        }
    }

    public static void inOrder2(Node root){
        Stack<Node> stack = new Stack<>();
        Stack<Node> tmpStack = new Stack<>();
        stack.push(root);
        boolean isVisited = false;
        while(!stack.empty()){
            Node node = stack.peek();
            if(node.left!=null && !isVisited){
                stack.push(node.left);
            }else if(node.right!=null){
                System.out.print(stack.pop().val+",");
                stack.push(node.right);
                isVisited = false;      //当加入右节点时,可把右节点当成一个新的根节点继续操作,子树,所以要重新设置
            }else{
                isVisited = true;       // 当输出该节点时,这个节点就是栈中下个节点的左孩子,所以要设true,防止父节点出栈时再次访问左子节点
                System.out.print(stack.pop().val+",");
            }
        }
    }

    public static void postOrder2(Node root){
        Stack<Node> stack = new Stack<>();
        Stack<Node> visitedStack = new Stack<>();
        stack.push(root);
        boolean isVisited = false;
        while(!stack.empty()){
            Node node = stack.peek();
            if(!visitedStack.empty() && visitedStack.peek() == node.right){
                visitedStack.pop();
                visitedStack.push(stack.peek());     // 一定要及时弹出元素,并压入新元素
                System.out.print(stack.pop().val+",");
                isVisited = true;
                continue;
            }
            if(node.left!=null && !isVisited){
                stack.push(node.left);
            }else if(node.right!=null){     // 右边如果访问过了,则左边一定访问过了!
                stack.push(node.right);
                isVisited = false;
            }else{
                isVisited = true;
                visitedStack.push(stack.peek());
                System.out.print(stack.pop().val+",");
            }
        }
    }

    @Question(solution = "新解答, 先让右进栈后让左进栈,避免左右问题")
    public static void preOrderUnRecur(Node head) {
        System.out.print("pre-order: ");
        if (head != null) {
            Stack<Node> stack = new Stack<Node>();
            stack.add(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                System.out.print(head.val + " ");
                if (head.right != null) {
                    stack.push(head.right);
                }
                if (head.left != null) {
                    stack.push(head.left);
                }
            }
        }
        System.out.println();
    }

    public static void inOrderUnRecur(Node head) {
        System.out.print("in-order: ");
        if (head != null) {
            Stack<Node> stack = new Stack<Node>();
            while (!stack.isEmpty() || head != null) {
                if (head != null) {
                    stack.push(head);
                    head = head.left;  // 左半边当作链表处理
                } else {
                    head = stack.pop();
                    System.out.print(head.val + " ");
                    head = head.right;
                }
            }
        }
        System.out.println();
    }

    public static void posOrderUnRecur1(Node head) {
        System.out.print("pos-order: ");
        if (head != null) {
            Stack<Node> s1 = new Stack<Node>();
            Stack<Node> s2 = new Stack<Node>();
            s1.push(head);
            while (!s1.isEmpty()) {  // 真是先序遍历反过来,只是最后再输出
                head = s1.pop();
                s2.push(head);
                if (head.left != null) {
                    s1.push(head.left);
                }
                if (head.right != null) {
                    s1.push(head.right);
                }
            }
            while (!s2.isEmpty()) {
                System.out.print(s2.pop().val + " ");
            }
        }
        System.out.println();
    }

    public static Node createTree(Node root, int[] valArr, int index){
        if(index>valArr.length-1){
            return null;
        }
        if(root == null){
            root = new Node(valArr[index]);
        }
        root.left = createTree(root.left, valArr, index*2);
        root.right = createTree(root.right, valArr, index*2+1);
        return root;
    }
    public static void main(String[] args) {
        int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Node root = null;
        root = createTree(root, arr, 1);
        System.out.println(root.val);
        System.out.println(root.right.val);
        preOrder(root);
        System.out.println();
        inOrder(root);
        System.out.println();
        postOrder(root);
        System.out.println();
        preOrder2(root);
        System.out.println();
        inOrder2(root);
        System.out.println();
        postOrder2(root);
        System.out.println();
        preOrderUnRecur(root);
        Scanner s = new Scanner(System.in);
        s.nextInt();
    }
}
