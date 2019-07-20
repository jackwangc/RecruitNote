/**
 *  二叉树的最近公共祖先
 */
public class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
 }

class Solution {

    private TreeNode ans;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        this.ans = null;
        this.recurseTree(currentNode, p, q);
        return this.ans;
    }
    // 递归
    private boolean recurseTree(TreeNode currentNode, TreeNode p, TreeNode q) {
        if (currentNode == null)
            return false;
        // 先序遍历
        int left = this.recurseTree(currentNode.left, p, q);
        int right = this.recurseTree(currentNode.right, p, q);

        int mid = (currentNode == p || currentNode == q) ? 1 : 0;
        // 中止条件
        // 当前结点 左右子树是否同时包含有 p 或 q
        if (mid + left + right >= 2) {
            this.ans = currentNode;
        }
        // 当前结点左右子树是否包含有 p 或 q
        return (mid + left + right > 0);

    }
}