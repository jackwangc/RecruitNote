class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode() {

    }
    public TreeNode(int val) {
        this.val = val;
    }
}
class test{
    public  void recursionPreorderTraversal(TreeNode root) {
        if (root != null) {
            // 根左右
            // 1. 找到打印出根的值
            System.out.println(root.val);
            // 2. 找到左节点 对其进行根左右 不用多余判断左右节点是否为空 下一个循环会自动判断
            recursionPreorderTraversal(root.left);
            // 3. 找到右节点，对其进行根左右
            recursionPreorderTraversal(root.right);
        }
    }
    public  void recursionPreorderTraversal2(TreeNode root) {
        // 非递归操作
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        
    }

}

