# 数据结构

- 数据结构
  - 按逻辑结构分类
    - 集合
      - 线性结构
        - 一维数组
        - 队列
        - 栈
    - 非线性结构
      - 树
      - 图
      - 多维数组
  - 按存储结构分类
    - 顺序存储结构
    - 链式存储结构
    - 索引存储结构
    - 散列存储结构

- 线性表
  - 顺序存储
  - 链式存储

## 1. 数组

> 相同数据类型的元素按照一定顺序排列的集合，是一块连续的内存空间

1. int[] 中的int告诉计算机这是一个整型数据，[]告诉计算机这是一个连续存储的内存地址空间，简单点说一个连续数据的存储空间就是数组
2. Java语言中，由于把二维数组看作是数组的数组，数组空间不是连续分配的
3. 数组的优点 get set 时间复杂度 都是 O(1); add remove 时间复杂度 O(n)
4. 数组不能进行添加和删除操作

```java
int[] ints = new int[10];
ints[0] = 5;//set
int a = ints[2];//get
int len = ints.length;//数组长度
int[] arr = new int[]{8,4,5,7,1,3,6,2,12,7,9}// 声明数组

```

## 2. List

### 2.1 链表

> 链表并不是一种数据结构，而是存储结构

1. 链表是一种非连续,非顺序的结构
2. 链表的优点是：add和remove操作时间上都是O(1)的；缺点是：get和set操作时间上都是O(N)的，而且需要额外的空间存储指向其他数据地址的项。
3. LinkedList 为双向链表
4. 链表是一种物理存储单元上非连续、非顺序的存储结构，数据元素的逻辑顺序是通过链表中的指针链接次序实现的。

```java
linkedList<String> linkedList = new LinkedList<>();
linkedList.add("addd");//add
linkedList.set(0,"s");//set，必须先保证 linkedList中已经有第0个元素
String s =  linkedList.get(0);//get
linkedList.contains("s");//查找
linkedList.remove("s");//删除

//以上方法也适用于ArrayList
```
#### 链表常考题目


### 2.2 队列

1. 队列是一种特殊的线性表，特殊之处在于它只允许在表的前端进行删除操作，而在表的后端进行插入操作，亦即所谓的先进先出（FIFO

```java
Deque<Integer> integerDeque = new LinkedList<>();
// 尾部入队，区别在于如果失败了
// add方法会抛出一个IllegalStateException异常，而offer方法返回false
integerDeque.offer(122);
integerDeque.add(122);
// 头部出队,区别在于如果失败了
// remove方法抛出一个NoSuchElementException异常，而poll方法返回false
int head = integerDeque.poll();//返回第一个元素，并在队列中删除
head = integerDeque.remove();//返回第一个元素，并在队列中删除
// 头部出队，区别在于如果失败了
// element方法抛出一个NoSuchElementException异常，而peek方法返回null。
head = integerDeque.peek();//返回第一个元素，不删除
head = integerDeque.element();//返回第一个元素，不删除
```
#### 队列常考题目

### 2.3 栈

1. 栈（stack）又名堆栈，它是一种运算受限的线性表。其限制是仅允许在表的一端进行插入和删除运算。这一端被称为栈顶，相对地，把另一端称为栈底。它体现了后进先出（LIFO）的特点

```java
Deque<Integer> stack = new ArrayDeque<Integer>();
stack.push(12);//尾部入栈
stack.push(16);//尾部入栈
int tail = stack.pop();//尾部出栈，并删除该元素
tail = stack.peek();//尾部出栈，不删除该元素
```

#### 栈常考题目

## 3. LIST 常用操作

1. 实现一个队列

> 单队列，元素出队后，无法再向其中添加对象

```java
// "static void main" must be defined in a public class.

class MyQueue {
    // store elements
    private List<Integer> data;
    // a pointer to indicate the start position
    private int p_start;
    // 构造函数
    public MyQueue() {
        data = new ArrayList<Integer>();
        p_start = 0;
    }
    /** Insert an element into the queue. Return true if the operation is successful. */
    public boolean enQueue(int x) {
        data.add(x);
        return true;
    };
    /** Delete an element from the queue. Return true if the operation is successful. */
    public boolean deQueue() {
        if (isEmpty() == true) {
            return false;
        }
        p_start++;
        return true;
    }
    /** Get the front item from the queue. */
    public int Front() {
        return data.get(p_start);
    }
    /** Checks whether the queue is empty or not. */
    public boolean isEmpty() {
        return p_start >= data.size();
    }
};

public class Main {
    public static void main(String[] args) {
        MyQueue q = new MyQueue();
        q.enQueue(5);
        q.enQueue(3);
        if (q.isEmpty() == false) {
            System.out.println(q.Front());
        }
        q.deQueue();
        if (q.isEmpty() == false) {
            System.out.println(q.Front());
        }
        q.deQueue();
        if (q.isEmpty() == false) {
            System.out.println(q.Front());
        }
    }
}
```

2. 实现一个循环队列

```java
class MyCircularQueue {
    private int[] data;
    private int head;
    private int tail;
    private int size;

    /** Initialize your data structure here. Set the size of the queue to be k. */
    public MyCircularQueue(int k) {
        data = new int[k];
        head = -1;
        tail = -1;
        size = k;
    }
    
    /** Insert an element into the circular queue. Return true if the operation is successful. */
    public boolean enQueue(int value) {
        if (isFull() == true) {
            return false;
        }
        if (isEmpty() == true) {
            head = 0;
        }
        tail = (tail + 1) % size;
        data[tail] = value;
        return true;
    }
    
    /** Delete an element from the circular queue. Return true if the operation is successful. */
    public boolean deQueue() {
        if (isEmpty() == true) {
            return false;
        }
        if (head == tail) {
            head = -1;
            tail = -1;
            return true;
        }
        head = (head + 1) % size;
        return true;
    }
    
    /** Get the front item from the queue. */
    public int Front() {
        if (isEmpty() == true) {
            return -1;
        }
        return data[head];
    }
    
    /** Get the last item from the queue. */
    public int Rear() {
        if (isEmpty() == true) {
            return -1;
        }
        return data[tail];
    }
    
    /** Checks whether the circular queue is empty or not. */
    public boolean isEmpty() {
        return head == -1;
    }
    
    /** Checks whether the circular queue is full or not. */
    public boolean isFull() {
        return ((tail + 1) % size) == head;
    }
}

/**
 * Your MyCircularQueue object will be instantiated and called as such:
 * MyCircularQueue obj = new MyCircularQueue(k);
 * boolean param_1 = obj.enQueue(value);
 * boolean param_2 = obj.deQueue();
 * int param_3 = obj.Front();
 * int param_4 = obj.Rear();
 * boolean param_5 = obj.isEmpty();
 * boolean param_6 = obj.isFull();
 */
```

3. 实现栈

```java
// "static void main" must be defined in a public class.
class MyStack {
    private List<Integer> data;               // store elements
    public MyStack() {
        data = new ArrayList<>();
    }
    /** Insert an element into the stack. */
    public void push(int x) {
        data.add(x);
    }
    /** Checks whether the queue is empty or not. */
    public boolean isEmpty() {
        return data.isEmpty();
    }
    /** Get the top item from the queue. */
    public int top() {
        return data.get(data.size() - 1);
    }
    /** Delete an element from the queue. Return true if the operation is successful. */
    public boolean pop() {
        if (isEmpty()) {
            return false;
        }
        data.remove(data.size() - 1);
        return true;
    }
};

public class Main {
    public static void main(String[] args) {
        MyStack s = new MyStack();
        s.push(1);
        s.push(2);
        s.push(3);
        for (int i = 0; i < 4; ++i) {
            if (!s.isEmpty()) {
                System.out.println(s.top());
            }
            System.out.println(s.pop());
        }
    }
}
```

4. java 实现广度优先搜索 - 队列 - bfs

```java
/**
 * Return the length of the shortest path between root and target node.
 */
int BFS(Node root, Node target) {
    Queue<Node> queue;  // store all nodes which are waiting to be processed
    int step = 0;       // number of steps neeeded from root to current node
    // initialize
    add root to queue;
    // BFS
    while (queue is not empty) {
        step = step + 1;
        // iterate the nodes which are already in the queue
        int size = queue.size();
        for (int i = 0; i < size; ++i) {
            Node cur = the first node in queue;
            return step if cur is target;
            for (Node next : the neighbors of cur) {
                add next to queue;
            }
            remove the first node from queue;
        }
    }
    return -1;          // there is no path from root to target
}
```

```java
/**
 * Return the length of the shortest path between root and target node.
 */
int BFS(Node root, Node target) {
    Queue<Node> queue;  // store all nodes which are waiting to be processed
    Set<Node> used;     // store all the used nodes
    int step = 0;       // number of steps neeeded from root to current node
    // initialize
    add root to queue;
    add root to used;
    // BFS
    while (queue is not empty) {
        step = step + 1;
        // iterate the nodes which are already in the queue
        int size = queue.size();
        for (int i = 0; i < size; ++i) {
            Node cur = the first node in queue;
            return step if cur is target;
            for (Node next : the neighbors of cur) {
                if (next is not in used) {
                    add next to queue;
                    add next to used;
                }
            }
            remove the first node from queue;
        }
    }
    return -1;          // there is no path from root to target
}
```

5. java 实现深度优先搜索 - 栈 - dfs

```java
/*
 * Return true if there is a path from cur to target.
 */
boolean DFS(Node cur, Node target, Set<Node> visited) {
    return true if cur is target;
    for (next : each neighbor of cur) {
        if (next is not in visited) {
            add next to visted;
            return true if DFS(next, target, visited) == true;
        }
    }
    return false;
}
```


```java
/*
 * Return true if there is a path from cur to target.
 */
boolean DFS(int root, int target) {
    Set<Node> visited;
    Stack<Node> s;
    add root to s;
    while (s is not empty) {
        Node cur = the top element in s;
        return true if cur is target;
        for (Node next : the neighbors of cur) {
            if (next is not in visited) {
                add next to s;
                add next to visited;
            }
        }
        remove cur from s;
    }
    return false;
}
```
6. 实现链表


```java
class MyLinkedList {
  private class LinkNode{
      public int val;
      public LinkNode next;
      public LinkNode(int val,LinkNode next){
          this.val = val;
          this.next = next;
      }
  }
  private LinkNode dummyHeader;//虚拟头结点
  private int size; // 链表当前存储个数
  /** Initialize your data structure here. */
  public MyLinkedList() {
      this.size = 0;
      this.dummyHeader = new LinkNode(0,null);
  }
  /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
  public int get(int index) {
      if(index < 0 || index >= size ){
          return -1;
      }else{
          LinkNode cur =  dummyHeader.next;
          for(int i = 0; i < index; i++){
              cur = cur.next;
          }
          return cur.val;
      }
  }
  /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
  public void addAtHead(int val) {
      addAtIndex(0,val);
  }
  /** Append a node of value val to the last element of the linked list. */
  public void addAtTail(int val) {
      addAtIndex(size,val);
  }
  /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
  public void addAtIndex(int index, int val) {
      if(index < 0 || index > size){
         // throw new IllegalArgumentException("index is illegal");
      }else{
            LinkNode pre =  dummyHeader;
              for(int i = 0; i< index; i++){
                   pre = pre.next;
              }
            LinkNode insertNode = new  LinkNode(val,null);
            insertNode.next = pre.next;
            pre.next =  insertNode;
          size++;
      }
  }
  /** Delete the index-th node in the linked list, if the index is valid. */
  public void deleteAtIndex(int index) {
      if(index < 0 || index >=size || size == 0){
         // throw new IllegalArgumentException("index is illegal");
          return;
      }
      LinkNode pre =  dummyHeader;
      for(int i = 0; i < index; i++){
          pre = pre.next;
      }
      LinkNode delNode = pre.next;
      pre.next = delNode.next;
      delNode = null;
      size--;
  }
}
```