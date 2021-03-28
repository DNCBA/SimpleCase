package com.mhc.algorithm.jiuzhang.tree;

import java.util.*;

public class TreeSearch {


    public static void main(String[] args) {
        HashSet<Integer> hashSet = new HashSet<>();

        System.out.println(hashSet.add(1));
        System.out.println(hashSet.add(1));


    }



    public List<Integer> preorderTraversalA(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        helper(root, result);
        return result;
    }

    private void helper(TreeNode root, List<Integer> result) {
        if (null == root) {
            return;
        }
        result.add(root.val);
        helper(root.left, result);
        helper(root.right, result);
    }


    public List<Integer> preorderTraversalB(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (null == root) {
            return result;
        }

        List<Integer> left = preorderTraversalB(root.left);
        List<Integer> right = preorderTraversalB(root.right);

        result.add(root.val);
        result.addAll(left);
        result.addAll(right);

        return result;
    }


    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (null == root) {
            return null;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> temp = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                temp.add(node.val);
                if (null != node.left) {
                    queue.add(node.left);
                }
                if (null != node.right) {
                    queue.add(node.right);
                }
            }
            result.add(temp);
        }
        return result;
    }


}
