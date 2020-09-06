package com.hujunchina.client.datastruct;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/4 11:38 下午
 * @Version 1.0
 */
public class TreeNode {
    public int val;
    public boolean isLeaf = true;
    public TreeNode left = null;
    public TreeNode right = null;
    TreeNode(){}
    TreeNode(int val){this.val = val; isLeaf=false;}
    public void add(int val){
        if(isLeaf){
            this.val = val;
            isLeaf = false;
        }else{
            if(this.val > val){
                if(null == left){
                    left = new TreeNode();
                }
                left.add(val);
            }else{
                if(null == right){
                    right = new TreeNode();
                }
                right.add(val);
            }
        }
    }

    public static void print(TreeNode root){
        if(root.left != null){
            print(root.left);
        }
        System.out.format("%d,",root.val);
        if(root.right != null){
            print(root.right);
        }
    }

    public static void main(String[] args) {
        int[] tree = {1,23,4,5,6,7,8};
        TreeNode root = new TreeNode();
        for(int elem : tree){
            root.add(elem);
        }
        print(root);
    }
}
