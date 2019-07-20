# List

> 有序(存储顺序和取出顺序一致)，可重复

1. List
   1. ArrayList
   2. LinkedList
   3. Vector 

## ArrayList

> 底层数据结构是数组。线程不安全

### 注意事项

- ArrayList是基于动态数组实现的，在增删时候，需要数组的拷贝复制。

- ArrayList的默认初始化容量是10，每次扩容时候增加原先容量的一半，也就是变为原来的1.5倍

- 删除元素时不会减少容量，若希望减少容量则调用trimToSize()

- 它不是线程安全的。它能存放null值。

- 扩容操作需要调用 Arrays.copyOf() 把原数组整个复制到新数组中

### 常用方法

```java
    // ArrayList类实现一个可增长的动态数组
    List<String> list = new ArrayList<String>();

    // 插入元素
    list.add("list1"); // 在末尾插入
    list.add("list2");

    // 打印list的大小
    System.out.println(list.size());

    // 按索引移除元素
    list.remove(0);

    // 按对象移除元素
    list.remove("list2");

    // 打印list的大小
    System.out.println(list.size());
    
    // 清空list
    list.clear();
    
    // 是否为空
    list.isEmpty();
    
    // 获取指定下标的值
    list.get(int);
  
    // 插入到指定位置
    list.add(2,"2");

```

## LinkedList

> 底层数据结构是链表。线程不安全(双向链表);队列的实现可以通过链表和数组


### 常用方法

```java
    // LinkedList类实现了链表，可初始化化为空或者已存在的集合
    LinkedList<String> list = new LinkedList<String>();
    // 插入元素
    list.add("list2");
    list.add("list3");
    // 向链表头插入数据
    list.addFirst("list1");
    // 向链表尾插入数据
    list.addLast("list4");
    for (String str : list) {
        System.out.println(str);
    }
    // 设置元素值
    list.set(0,"s");
    // 获取指定位置的值
    String s = list.get(0);
    // 查找
    boolean flag = list.contains("s");
    // 删除
    list.remove("s")
    // 获取链表头数据
    System.out.println("链表头数据:" + list.getFirst());
    // 获取链表尾数据
    System.out.println("链表尾数据:" + list.getLast());
```

#### 常见题目

1. [从头到尾打印链表](../offerJz/8-从头到尾打印链表.java)
2. [链表中环的入口节点](../offerJz/9-链表中环的入口节点.java)
3. [删除链表中的重复节点](../offerJz/10-删除链表中的重复结点.java) **重点**
4. [链表中倒数第k个节点](../offerJz/29-链表中倒数第k个结点.java)
5. [反转链表](../offerJZ/30-反转链表.java)
6. [合并两个排序的链表](../offerJz/31-合并两个排序链表.java)
7. [二叉搜索树与双向链表](../offerJz/41-二叉搜索树与双向链表.java)
8. [两个链表的第一个公共节点](../offerJz/51-两个链表的第一个公共结点.java)
9. [把字符串转换成整数]../offerJz/

## 区别

*区别* | ArrayList | LinkedList | Vector
---|---|---|---
底层实现 | 底层实现是数组 | 底层实现是双向链表[双向链表方便实现往前遍历] | 底层是数组，现在已少用，被ArrayList替代
特性 | ArrayList的默认初始化容量是10，每次扩容时候增加原先容量的一半，也就是变为原来的1.5倍</br>在增删时候，需要数组的拷贝复制(navite 方法由C/C++实现) | |Vector所有方法都是同步，有性能损失。</br>Vector初始length是10 超过length时 以100%比率增长，相比于ArrayList更多消耗内存。

## Vector

1. `synchronizedCollection /名单 方法 java.util.Collection` 从非线程安全的集合中获取线程安全集合。
2. `Vector` 同步每个单独的操作
3. 向量实际上只是可以作为数组访问的列表，因此它应该被调用 `ArrayList`
4. 全部 `get()`， `set()`方法是`synchronized`，因此您无法对同步进行细粒度控制。


## 队列

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
### 队列常考题目

1. [用两个栈实现队列](../offerJz/19-用两个栈实现队列.java)


## 2.3 栈

1. 栈（stack）又名堆栈，它是一种运算受限的线性表。其限制是仅允许在表的一端进行插入和删除运算。这一端被称为栈顶，相对地，把另一端称为栈底。它体现了后进先出（LIFO）的特点

```java
Deque<Integer> stack = new ArrayDeque<Integer>();
stack.push(12);//尾部入栈
stack.push(16);//尾部入栈
int tail = stack.pop();//尾部出栈，并删除该元素
tail = stack.peek();//尾部出栈，不删除该元素
```

### 栈常考题目

1. [包含 min 函数的栈](../offerJz/35-包含min函数的栈.java)
2. [栈的压入，弹出序列](../offerJz/36-栈的压入弹出序列.java)