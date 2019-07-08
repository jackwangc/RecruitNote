/**
 * linkedList
 */
class linkedList {

    private class LinkNode{
        public int val;
        public LinkNode next;
        public LinkNode(int val,LinkNode next){
            this.val = val;
            this.next = next;
        }
    }

    private LinkNode dummyHeader;
    private int size;
    /** Initialize your data structure here. */
    public linkedList() {
        this.size = 0;
        this.dummyHeader = new LinkNode(0, null); 
    }
    
    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if (index < 0 && index >=size){
            return -1;
        }else{
            LinkNode cur = dummyHeader.next;
            for (int i = 0; i < index; i++) {
                cur = cur.next;
            }
            return cur.val;
        }
    }
    
    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        addAtIndex(0, val);
    }
    
    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        addAtIndex(size, val);
    }
    
    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if(index < 0 || index > 0){

        }else{
            LinkNode pre = dummyHeader;
            for (int i = 0; i < index; i++) {
                pre = pre.next;
            }
            LinkNode insertNode = new LinkNode(val, null);
            insertNode.next = pre.next;
            pre.next = insertNode;
            size++;
        }
    }
    
    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if(index < 0 || index > 0){

        }else{
            LinkNode pre = dummyHeader;
            for (int i = 0; i < index; i++) {
                pre = pre.next;
            }
            LinkNode dLinkNode = pre.next;
            pre.next = dLinkNode.next;
            dLinkNode = null;
            size--;
        }
    }
    // 头插法
    public void LinkedListInsertToHead(ListNode listNode){
        ListNode head = new ListNode(-1);
        while (listNode!=null) {  // 1. memo = 1,
            ListNode memo = listNode.next; // 生产新节点
            listNode.next = head.next; // 新节点指向原头节点的下个节点
            head.next = listNode; // 头部节点指向新节点
            listNode = memo;  
        }
    }
}
