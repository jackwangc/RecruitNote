import java.util.Stack;

/**
 * 输入一个链表，按链表值从尾到头的顺序返回一个ArrayList。
 */
public class ListNode {
    int val;
    ListNode next = null;
    ListNode(int val) {
        this.val = val;
    }
}


public class Solution{
    // 递归
    public void solution(ListNode node){
        mySolution(node);
    }
    public void mySolution(ListNode node){
        ListNode next = node.next;
        if( next != null){
            mySolution(next);
            System.out.println(node.val);
        }
    }
    // 利用栈
    public void solution2(ListNode node){
        Stack stac = new Stack<>();
        while( node.next != null){
            stac.add(node.val);
            node = node.next;
        }
        while(!stac.empty()){
            System.out.println(stac.pop());
        }
    }
}