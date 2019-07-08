/**
 * 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。
 */
public class Solution
{ 
    // 双指针法 
    // 1. 利用数学公式 
    // x+y+z = 2(x+y) z 相遇点到环交点的位置 x 起始点到环交点的位置 y 环交点到相遇点的位置
    public ListNode solution(ListNode node){
        if (node == null || node.next == null)
            return null;
        ListNode slow = node,fast = node;
        // 第一个循环
        do {
            fast = fast.next.next;
            slow = slow.next;
        }while (slow != fast);
        // 第二个循环
        fast = node;
        while (slow != fast){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;

    }
}

public class ListNode {
    int val;
    ListNode next = null;
    ListNode(int val) {
        this.val = val;
    }
}