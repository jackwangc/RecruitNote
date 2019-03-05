# 集合

> 集合与数组的出现是为了解决方便存储操作多个对象的问题。常见的容器有 StringBuffered,数组。

## 数组和集合的区别

1. 长度的区别

    1. 数组的长度固定

    2. 集合的长度可变

2. 内容不容

    1. 数组存储的是同一种类型的元素

    2. 集合可以存储不同类型的元素

3. 元素的数据类型

    1. 数组可以存储基本数据类型,也可以存储引用类型

    2. 集合只能存储引用类型(你存储的是简单的int，它会自动装箱成Integer)

## List

> 有序(存储顺序和取出顺序一致)，可重复

### ArrayList

> 底层数据结构是数组。线程不安全

#### 注意事项

- ArrayList是基于动态数组实现的，在增删时候，需要数组的拷贝复制。

- ArrayList的默认初始化容量是10，每次扩容时候增加原先容量的一半，也就是变为原来的1.5倍

- 删除元素时不会减少容量，若希望减少容量则调用trimToSize()

- 它不是线程安全的。它能存放null值。

#### 常用方法

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

### LinkedList

> 底层数据结构是链表。线程不安全(双向链表);队列的实现可以通过链表和数组


#### 常用方法

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
    // 获取链表头数据
    System.out.println("链表头数据:" + list.getFirst());
    // 获取链表尾数据
    System.out.println("链表尾数据:" + list.getLast());
```
### 区别

*区别* | ArrayList | LinkedList | Vector
---|---|---|---
底层实现 | 底层实现是数组 | 底层实现是双向链表[双向链表方便实现往前遍历] | 底层是数组，现在已少用，被ArrayList替代
特性 | ArrayList的默认初始化容量是10，每次扩容时候增加原先容量的一半，也就是变为原来的1.5倍</br>在增删时候，需要数组的拷贝复制(navite 方法由C/C++实现) | |Vector所有方法都是同步，有性能损失。</br>Vector初始length是10 超过length时 以100%比率增长，相比于ArrayList更多消耗内存。

## Set

> 元素不可重复

### TreeSet

> 底层数据结构是红黑树(是一个自平衡二叉树)

### HashSet

> 底层数据结构是哈希表(是一个元素为链表的数组)

## Iterator(迭代器)

- hasNext()
- next()
- remove()