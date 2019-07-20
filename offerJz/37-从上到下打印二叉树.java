/**
 * 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
 * 二叉树的层次遍历
 */
import java.util.ArrayList;
import java.util.Queue;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}

public class Solution {
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        
        Queue<TreeNode> nodeQueue = new Queue<TreeNode>();
        ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return null;
        }
        nodeQueue.add(root);
        while (nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            // 操作
            result.add(node.val);
            if (node.left != null) {
                nodeQueue.add(node.left);
            }
            if (node.right != null) {
                nodeQueue.add(node.right);
            }

        }
    }
}