import java.util.Stack;

/**
 * 反转链表
 */
public class Solution{
    public void solution(ListNode node){
        Stack<ListNode> stac = new Stack<>();
        while(node.next!=null){
            stac.add(node);
            node = node.next;
        }
        ListNode res = new ListNode(-1);
        ListNode nn = res;
        while(stac.empty()){
            val = stac.pop();
            nn.next = val;
            nn = nn.next;          
        }
    }

    public void solution2(ListNode node){
        ListNode head = new ListNode(-1);
        while(node!=null){
            ListNode temp = node.next;
            temp.next = head.next;
            head.next = temp;
            node = node.next;
        }
        ListNode res = head.next;
    }
}

public class ListNode {
    int val;
    ListNode next = null;
    ListNode(int val) {
        this.val = val;
    }
}