import java.util.Queue;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * 输入一棵二叉树，求该树的最大深度。
 * 从根结点到叶结点依次经过的结点（含根、叶结点）形成树的一条路径，最长路径的长度为树的深度。
 * 考察二叉树的层次遍历
 * 对层次遍历进行改造,bfs
 */

public class Solution {
    // 递归写法
    public int TreeDepth(TreeNode root) {
        if (root == null){
            return 0;
        }
        // 左子树高度
        int left = TreeDepth(root.left);
        // 右子树高度
        int right = TreeDepth(root.right);
        return Math.max(left, right) + 1;
    }
    // 非递归写法
    // bfs
    public int TreeDepth2(TreeNode root) {
        if (root == null){
            return 0;
        }
        Queue<TreeNode> queue = new Queue<TreeNode>();
        queue.add(root);
        int depth = 0;
        while(queue.size()!=0){
            int size = queue.size();
            depth++;
            for (int i = 0; i < size; i++) { // 只在换层时增加深度  // 最小深度判断
                TreeNode node = queue.poll();
                if (node.left != null){
                    queue.offer(node.left);
                }
                if (node.right != null){
                    queue.offer(node.right);
                }
                // if (node.right == null && node.left == null){
                //  当遇到第一个结点的左右结点都为 空时 为最小深度
                // }
            }
        }
        return depth;
    }
    /**
     * 逆向思维，二叉树的最小深度
     * dfs
     * 先找出递归结束条件
     */
    // 递归写法
     public int solution(TreeNode treeNode) {
        // 特殊判断
        if (treeNode == null){
            return 0;
        }
        // 结束条件
        if (treeNode.left == null && treeNode.right == null){
            return 1;
        }
        int left = solution(treeNode.left);
        int right = solution(treeNode.right);
        if (treeNode.right ==  null || treeNode.left ==null){
            return left + right + 1;
        }
        return Math.min(left, right)+1;

     }
     // 非递归写法
     // dfs
     // 遇到叶子结点 输出当前栈的深度
     public int solution2(TreeNode treeNode) {
         
     }
}



public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;
    }
}
