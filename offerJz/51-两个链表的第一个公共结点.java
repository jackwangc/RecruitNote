/**
 * 两个链表的公共结点
 */
public class Solution{
    // 找出长度差
    public void solution(ListNode node1,ListNode node2){
        int len1 = 0;
        while(node1 != null){
            node1 = node1.next;
            len1 = len1 + 1;
        }
        int len2 = 0;
        while(node2 != null){
            node2 = node2.next;
            len2 = len2 + 1;
        }
        for (int i = 0; i < len2 - len1; i++) {
                node2 = node2.next;
        }
        while(node1 !=node2){
            node1 = node1.next;
            node2 = node2.next;
        }
    }
    // 循环找出两个
    public void solution2(ListNode p1,ListNode p2){
        while(p1!=p2){
            p1 = (p1==NULL ? pHead2 : p1->next);
            p2 = (p2==NULL ? pHead1 : p2->next);
        }
        System.out.println(p1);
    }
}

public class ListNode {
    int val;
    ListNode next = null;
    ListNode(int val) {
        this.val = val;
    }
}