/**
 * 输入一个链表，输出该链表中倒数第k个结点。
 */
public class Solution{
    public void solution(ListNode node,int k) {
        ListNode nodeSlow = node;
        ListNode nodeFast = node;
        int flag = 0;
        while(node.next != null){
            if(flag>k){
                nodeSlow = nodeSlow.next;
            }
            flag++;
            nodeFast = nodeFast.next;
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