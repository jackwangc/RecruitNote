import javax.swing.tree.TreeNode;

/**
 * 请实现一个函数，用来判断一颗二叉树是不是对称的。
 * 注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。
 */
public class Solution {
    // 1. 顺序遍历该二叉树
    // 2. 遍历该二叉树的镜像
    boolean isSymmetrical(TreeLinkNode pRoot)
    {
        if (pRoot == null) {
            return true;
        }

   }
   private boolean comRoot(TreeLinkNode left, TreeLinkNode right){
        if(left == null) return right==null;
        if(right == null) return false;
        if(left.val != right.val) return false;
        return comRoot(left.right, right.left) && comRoot(left.left, right.right);
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