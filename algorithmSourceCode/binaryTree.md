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

## 红黑树

### 预备知识

1. 平衡二叉树（avl树）：左右两个子树的高度差的绝对值不超过 1
2. 二叉搜索树（bst）
   1. 查找的最大次数是二叉树的高度 
   2. 若左子树不为空，则左子树上所有节点的值都小于他跟节点上的值；
   3. 若右子树不为空，则右子树上所有节点的值都大于他的根节点的值
   4. 缺点：当插入的数据有序时会退化成为链表
3. 平衡查找树：保证树的平衡降低树的高度

### 特性

1. 根节点是黑色
2. 节点是红色或黑色
3. 每个叶子节点都是黑色的空节点
4. 每个红色节点的两个子节点都是黑色
   1. 从根节点到叶子节点的所有路径上都包含有相同数目的黑色节点
   2. 从根到叶子节点的最长路径不会超过最短路径的两倍
5. 从任一节点到其每个叶子的所有路径都包含相同数目的黑色节点。

### 插入

> 插入改变红黑树的结构，需要进行调整。调整的方式：旋转（右旋转和左旋转），变色。

1. 变色：把红色节点变成黑色，把黑色节点变成红色
2. 旋转
```java
// 对x左旋
//      x               y
//     / \             / \
//    a   y    ->     x   c
//       / \         / \
//      b   c       a   b
// 对y右旋 
//      x               y
//     / \             / \
//    a   y    <-     x   c
//       / \         / \
//      b   c       a   b 
```

### 参考

1. [小灰漫画红黑树](https://mp.weixin.qq.com/s/jz1ajDUygZ7sXLQFHyfjWA)
2. [深入理解红黑树原理-详细](https://mp.weixin.qq.com/s/hGHJonK999TAVJakPDNAkg)
3. [理解红黑树](https://www.jianshu.com/p/e136ec79235c)

## b 树

### b- 树
> b- 树是一种多路搜索树，他的每个节点可以拥有多于两个孩子节点。M 路的 B 树最多拥有 M 个孩子节点

1. 之所以设计 b 树是为了降低树的高度，但当设计成无限多路时，会退化成数组
2. b 树一般用在文件系统的索引
   1. 用 b 树不用数组，数组无法一次性加载进内存
   2. 一般树，需要进行多次查找
   3. b 树可以一次载入一个节点进内存
   4. 在内存中红黑树的效率更高，涉及到磁盘操作时，b 树更优
3. 特性，b树的添加重组就是根据几点特性来进行的
   1. 每个结点最多 m 个子结点。
   2. 除了根结点和叶子结点外，每个结点最少有 `m/2`（向上取整）个子结点。
   3. 如果根结点不是叶子结点，那根结点至少包含两个子结点。
   4. 所有的叶子结点都位于同一层。
   5. 每个结点都包含 k 个元素（关键字），这里`m/2≤k<m`，这里 m 2向下取整。
   6. 每个元素（关键字）字左结点的值，都小于或等于该元素（关键字）。右结点的值都大于或等于该元素（关键字）
4. B-Tree的查询效率并不比平衡二叉树高。但是查询所经过的结点数量要少很多，也就意味着要少很多次的磁盘IO，这对性能的提升是很大的。
5. 数据库中，b 树存储时 key-data 形式，key 是主键，data 是具体的数据

### b+ 树

> b+ 树是在 b 树的基础上进行改造，他的数据都在叶子结点，叶子结点之间加了指针形成链表

1. 之所以设计 b+ 树，是因为数据库需要查询多条数据，用 b 树需要进行遍历，
   1. b+ 树由于有链表结构，只需要找到首尾，通过链表就能把所有数据取出来 

2. 如果只选一个数据，那确实是hash更快。但是数据库中经常会选择多条，这时候由于B+树索引有序，并且又有链表相连，它的查询效率比hash就快很多了。
3. 而且数据库中的索引一般是在磁盘上，数据量大的情况可能无法一次装入内存，B+树的设计可以允许数据分批加载，同时树的高度较低，提高查找效率
4. 特性
   1. 所有的非叶子节点只存储关键字信息。
   2. 所有卫星数据（具体数据）都存在叶子结点中。
   3. 所有的叶子结点中包含了全部元素的信息。
   4. 所有叶子节点之间都有一个链指针。
5. 对比
   1. 所以在同样高度的B-Tree和B+Tree中，B-Tree查找某个关键字的效率更高
   2. 在找大于某个关键字或者小于某个关键字的数据的时候，B+Tree只需要找到该关键字然后沿着链表遍历就可以了，而B-Tree还需要遍历该关键字结点的根结点去搜索。
   3. 这样同样总量的数据，B-Tree的深度会更大，增大查询时的磁盘I/O次数，进而影响查询效率。
   4. 所以在常用的关系型数据库中，都是选择B+Tree的数据结构来存储数据

---

## **常见算法题**

> 其实是对几种二叉树遍历方式的考察

1. [二叉树的深度](../offerJz/53-二叉树的深度.java)
2. [二叉树的下一个结点](../offerJz/12-二叉树的下一个结点.java)
3. [序列化二叉树](../offerJz/16-序列化二叉树.java)
4. [从上到下打印二叉树](../offerJz/37-从上到下打印二叉树.java)
5. [二叉树和为某一值的路径](../offerJz/39-二叉树和为某一值的路径.java)
6. 二叉树第 k 层的节点个数
7. 二叉树中叶子节点的个数
8. 判断两棵二叉树是否相同的树
9. [判断二叉树是不是平衡二叉树](../offerJz/54-平衡二叉树.java)
10. [求二叉树的镜像](../offerJz/33-二叉树的镜像.java)
11. 判断两个二叉树是否为相互镜像
12. [求二叉树中两个节点的最低公共祖先节点](../leetCode/236-二叉树的最近公共祖先.java)
13. 判断是否为二分查找树

---

## **参考链接**
1. [二叉树](https://blog.csdn.net/u012428012/article/details/79089915)
2. [b 树为什么](https://mp.weixin.qq.com/s/gaHC7MfFcsiN7N7-gOR9xw)
3. [b树介绍](https://mp.weixin.qq.com/s/3cqdS3n5BTJLLPZwj6bzvQ)




