/**
 * 在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。 
 * 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5
 */
public class Solution
{ 
    // 犯得错误，没有考虑头尾结点重复
    // 头节点重复，新建虚拟头结点
    // 尾结点重复，while 循环错误
    // java 引用理解
    public ListNode solution(ListNode node){
        // 结果根结点
        ListNode root = new ListNode(-1);
        root.next = node;
        // 前驱结点
        ListNode pre = node;
        // 当前不重复节点
        ListNode last = root;
        // 其实都更改了 node 的结构
        while(pre != null && pre.next != null){
            if(pre.val == pre.next.val){
                int val = pre.val;
                while(pre.next != null && pre.val == val){
                    pre = pre.next; // 改变当前 pre 结点的指向
                }
                last.next = pre; // 删除结点
            }else{ // 前后结点确定不重复
                last = pre; // 保留当前不重复结点
                pre = pre.next; // 进入下一个循环
            }
        }
        return root.next;
    }
    // 递归解法
    public ListNode solution2(ListNode node){
        if(node == null || node.next == null){
            return node;
        }
        if (node.val == node.next.val){
            ListNode pre = pre.next;
            while(pre != null && pre.val == node.val){
                pre = node.next;
            }
            return solution2(pre);
        }else{
            node.next = solution2(node.next);
            return node;
        }
    }

}

public class ListNode {
    int val;
    ListNode next = null;
    ListNode(int val) {
        this.val = val;
    }
}