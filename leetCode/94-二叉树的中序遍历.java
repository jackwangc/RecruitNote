public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Inorder in ArrayList which contains node values.
     */
    public ArrayList<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        ArrayList<Integer> result = new ArrayList<>();
        
        while (root != null) {
            stack.push(root);
            root = root.left;
        } // 将所有左节点压入栈
    
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();  // 取出栈中最后一个节点 不删除
            result.add(node.val); // 传入结果数组中
            
            if (node.right == null) {   // 判断该节点是否有右节点
                node = stack.pop();   // 没有右节点 该节点出栈
                while (!stack.isEmpty() && stack.peek().right == node) {
                    node = stack.pop();
                }
            } else {
                node = node.right;  // 存在右节点
                while (node != null) {
                    stack.push(node); // 将该节点的所有左节点压栈
                    node = node.left;
                }
            }
        }
        return result;
    }
}


public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Inorder in ArrayList which contains node values.
     */
    public ArrayList<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        ArrayList<Integer> result = new ArrayList<Integer>();
        TreeNode curt = root;
        while (curt != null || !stack.empty()) {
            while (curt != null) {
                stack.add(curt);
                curt = curt.left;
            }
            curt = stack.pop();
            result.add(curt.val);
            curt = curt.right;
        }
        return result;
    }
}