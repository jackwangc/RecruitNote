import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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
class tree {
    // 先序遍历
    public void recursionPreorderTraversal(TreeNode root) {
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

    public void recursionPreorderTraversal2(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode node = root;
        while (node !=null || stack.isEmpty()) {
            // 不断将左节点入栈，直到 cur 为空 先序遍历 打印入栈顺序
            while (node !=null){
                stack.push(node);
                System.out.println(root.val);
                node = node.left;
            }
            // 将右节点入栈  中序遍历打印出栈顺序
            if (!stack.isEmpty()){
                node = stack.pop();
                node = node.right;
            }
        }

    }

    public void recursionPreorderTraversal3(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        while (stack.isEmpty()){
            TreeNode node = stack.pop();
            System.out.println(node.val);
            if (node.right!=null){
                stack.push(node.right);
            }
            if (node.left!=null){
                stack.push(node.left);
            }
        }
    }

    // 中序遍历
    public static void inorderTraversalRec(TreeNode root){
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (stack.isEmpty()|| cur!=null){
            while(cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            if (!stack.isEmpty()) {
                cur = stack.pop();
                System.out.print(cur.val+"-");
                cur = cur.right;
            }
        }
    }
    // 后序遍历
    // 1. 游标
    public static void postorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        TreeNode lastVisit = root;
        while (node!=null || stack.isEmpty()){
            while (node!=null){
                stack.push(node);
                node = node.left;
            }
            node = stack.peek();
            if (node.right==null || node.right==lastVisit){
                System.out.print("");
                stack.pop();
                lastVisit = node;
                node = null;
            }else {
                node = node.right;
            }
        }
    }
    // 2. 双栈
    public static void postorderTraversal2(TreeNode root) {
        if(root == null) {
            return;
        }
        Stack<TreeNode> stack1 = new Stack<>(); // 保存树节点
        Stack<TreeNode> stack2 = new Stack<>(); // 保存后序遍历的结果

        stack1.add(root);
        while (!stack1.isEmpty()) {
            TreeNode temp = stack1.pop();
            stack2.push(temp); // 将弹出的元素加到stack2中 因为栈是后入先出的结构
            if (temp.left != null) { // 左子节点先入栈
                stack1.push(temp.left);
            }
            if (temp.right != null) { // 右子节点后入栈
                stack1.push(temp.right);
            }
        }
        while (!stack2.isEmpty()) {
            System.out.print(stack2.pop().val + "->");
        }
    }
    // 层次遍历
    public static void levelTraversal(TreeNode root){
        if(root == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()){
            // 利用队列先进先出 广度优先搜索
            TreeNode temp= queue.poll();  // 删除第一个元素并从队列中删除
            System.out.print("");
            if (temp.left!=null){
                queue.add(temp.left);
            }
            if (temp.right!=null){
                queue.add(temp.right);
            }
        }
    }

    public static void test(TreeNode root){
        if (root==null){
            return;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        //
        stack.push(root);
        while (stack.isEmpty()){
            while (root.left!=null){
                stack.push(root.left);
                root=root.left;
            }
            root = stack.pop();
            stack.push(root.right);

        }
    }

}

