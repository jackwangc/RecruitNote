/**
 * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。
 * 要求不能创建任何新的结点，只能调整树中结点指针的指向。
 * 问题总结
 * 二叉搜索树定义，排好序的二叉树
 * 二叉树遍历：中序遍历
 */


public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}

public class Solution {

    // 定义一个结果树
    TreeNode treeNode;
    public TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null){
            return null;
        }

        Convert(pRootOfTree.left);
        // 进行操作
        if (treeNode == null){
            treeNode = pRootOfTree;
        }else{
            // 指针变换
            treeNode.right = pRootOfTree;
            pRootOfTree.left = treeNode;
            treeNode = pRootOfTree;
        }

        Convert(pRootOfTree.right);
    }
}