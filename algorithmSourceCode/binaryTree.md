# 二叉树

## 基础

1. 满二叉树：一棵深度为k，且有 $2^{k+1}-1$个节点的二叉树
2. 完全二叉树：由满二叉树而引出来的。对于深度为K的，有n个结点的二叉树，当且仅当其每一个结点都与深度为K的满二叉树中编号从1至n的结点一一对应时称之为完全二叉树。
   1. 所有的叶结点都出现在第k层或k-l层（层次最大的两层）
   2. 对任一结点，如果其右子树的最大层次为L，则其左子树的最大层次为L或L+l。  
3. 遍历节点，1. 一个结点左孩子为空，右孩子不为空，则该节点一定不是完全二叉树；2. 一个节点左孩子（该孩子没有子节点）不为空，右孩子为空；或者左右孩子都为空；这样树才是完全二叉树。
4. 平衡二叉树：
5. 由二叉树的先序序列和中序序列可以唯一地确定一颗二叉树；
6. 由二叉树的后序序列和中序序列可以唯一地确定一颗二叉树；
7. 由二叉树的层序序列和中序序列可以唯一地确定一棵二叉树；
8. 但，由二叉树的先序序列和后序序列无法唯一地确定一棵二叉树。
## 性质

1. 在二叉树的第 i 层至多有 $2^(i －1)$个结点。(i>=1)
2. 深度为 k 的二叉树至多有 $2^(k-1)$个结点(k >=1)
3. 对任何一棵二叉树T, 如果其叶结点数为n0, 度为2的结点数为 n2,则n0＝n2＋1.(度，结点包含的子结点数)
4. 完全二叉树的深度 $h = log_{2}k+1$

## 二叉树

### 二叉树的定义

```
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode() {

    }
    public TreeNode(int val) {
        this.val = val;
    }
}
```

### 二叉树的遍历

```java

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
            TreeNode temp= queue.poll(); // 利用队列先进先出 广度优先搜索
            System.out.print("");
            if (temp.left!=null){
                queue.add(temp.left);
            }
            if (temp.right!=null){
                queue.add(temp.right);
            }
        }
    }


}

```