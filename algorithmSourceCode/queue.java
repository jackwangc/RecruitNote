import java.util.List;

import sun.security.krb5.internal.KDCOptions;

/**
 * 简单队列
 */
class queue{
    
    private List<Integer> data;

    private int p_start;

    public queue() {
        data = new ArrayList<Integer>();
        p_start = 0;
    }
    
    public boolean enQueue(int x){
        data.add(x);
        return true;
    }

    public boolean deQueue() {
        if (isEmpty()){
            return false;
        }
        p_start++;
        return true;
    }

    public int Front() {
        return data.get(p_start);
    }
    public boolean isEmpty() {
        return p_start >= data.size();
    }

}

/**
 * circularQueue 循环队列
 */
class circularQueue {
    private int[] data; // 数组 存储队列元素
    private int head; // 头指针
    private int tail; // 尾指针
    private int size; // 队列大小
    // 构造函数
    public circularQueue(int k) {
        data = new int[k];
        head = -1;
        tail = -1;
        size = k;
    }
    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        if (isEmpty()) {
            head = 0;
        }
        tail = (tail + 1) % size;
        data[tail] = value;
        return true;
    }
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        head = (head + 1) % size;
        if (head == tail) {
            head = -1;
            tail = -1;
            return true;
        }
        return true;
    }
    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return data[head];
    }
    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        return data[tail];
    }
    public boolean isEmpty() {
        return head == -1;
    }
    public boolean isFull() {
        return ((tail+1)%size) == head;
    }

    
}
/**
 * 内置队列用法
 */
class InnerQueue {
    public void Test() {
        Deque<Integer> integerDeque = new LinkedList<>();
        // 尾部入队，区别在于如果失败了
        // add方法会抛出一个IllegalStateException异常，而offer方法返回false
        boolean a = integerDeque.offer(122);
        integerDeque.add(122);
        // 头部出队,区别在于如果失败了
        // remove方法抛出一个NoSuchElementException异常，而poll方法返回false
        int head = integerDeque.poll();//返回第一个元素，并在队列中删除
        head = integerDeque.remove();//返回第一个元素，并在队列中删除
        // 头部出队，区别在于如果失败了
        // element方法抛出一个NoSuchElementException异常，而peek方法返回null。
        head = integerDeque.peek();//返回第一个元素，不删除
        head = integerDeque.element();//返回第一个元素，不删除
    }
    
}
// bfs 伪代码 1
// int BFS(Node root, Node target) {
//     Queue<Node> queue;  // store all nodes which are waiting to be processed
//     int step = 0;       // number of steps neeeded from root to current node
//     // initialize
//     add root to queue;
//     // BFS
//     while (queue is not empty) {
//         step = step + 1;
//         // iterate the nodes which are already in the queue
//         int size = queue.size();
//         for (int i = 0; i < size; ++i) {
//             Node cur = the first node in queue;
//             return step if cur is target;
//             for (Node next : the neighbors of cur) {
//                 add next to queue;
//             }
//             remove the first node from queue;
//         }
//     }
//     return -1;          // there is no path from root to target
// }
// bfs 伪代码2
// int BFS(Node root, Node target) {
//     Queue<Node> queue;  // store all nodes which are waiting to be processed
//     Set<Node> used;     // store all the used nodes
//     int step = 0;       // number of steps neeeded from root to current node
//     // initialize
//     add root to queue;
//     add root to used;
//     // BFS
//     while (queue is not empty) {
//         step = step + 1;
//         // iterate the nodes which are already in the queue
//         int size = queue.size();
//         for (int i = 0; i < size; ++i) {
//             Node cur = the first node in queue;
//             return step if cur is target;
//             for (Node next : the neighbors of cur) {
//                 if (next is not in used) {
//                     add next to queue;
//                     add next to used;
//                 }
//             }
//             remove the first node from queue;
//         }
//     }
//     return -1;          // there is no path from root to target
// }