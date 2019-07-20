import javax.swing.tree.TreeNode;

/**
 * 操作给定的二叉树，将其变换为源二叉树的镜像
 * 考察先序遍历
 */
public class Solution {
    public void Mirror(TreeNode root) {
        // 先序遍历
        // 1. 递归写法
        if (root != null){
            TreeNode tempNode = root.right;
            root.right = root.left;
            root.left = tempNode;
            if (root.left != null) {
                Mirror(root.left);
            }
            if (root.right != null) {
                Mirror(root.right);
            }
        }
    }
}
public class TreeLinkNode {
    int val;
    TreeLinkNode left = null;
    TreeLinkNode right = null;
    TreeLinkNode next = null;

    TreeLinkNode(int val) {
        this.val = val;
    }
}