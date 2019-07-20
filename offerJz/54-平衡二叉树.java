import javax.swing.tree.TreeNode;

/**
 * 判断一颗二叉树是不是平衡树
 */
public class Solution {
    public boolean IsBalanced_Solution(TreeNode root) {
        
        if (root == null) {
            return null;
        }
        
    }
    private int getHigh(TreeNode root){
        if (root == null) {
            return 0;
        }
        int left = getHigh(root.left);
        int right = getHigh(root.right);
        if (Math.abs(left - right) > 1) {
            res = false;
        }
        return Math.max(left, right);
    }
}