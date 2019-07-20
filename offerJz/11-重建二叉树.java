/**
 * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。
 */

public class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
}
 
public class Solution {
    public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        TreeNode root = reConstructBTree(pre,0,pre.length-1, in ,0,in.length-1);
        return root;
    }
    // 递归
    public TreeNode reConstructBTree(int [] pre,int preStart,int preEnd, int [] in ,int inStart,int inEnd){
        // 初始节点
        if(preStart>preEnd||inStart>inEnd)
            return null;
        // 新建当前结点
        TreeNode root = new TreeNode(pre[preStart]);
        for(int i= inStart; i<=inEnd ; i++){
            if (in[i]==pre[preStart]){ // 结点相等的地方 是 左右字树的分割位置
                // 新建下一轮的结点
                root.left = reConstructBTree( pre,preStart+1,preStart+i-inStart,in,inStart,i-1);
                root.right = reConstructBTree( pre,i+preStart-inStart+1, preEnd,in,i+1,inEnd);
            }
            
        }
        return root;
    }
}