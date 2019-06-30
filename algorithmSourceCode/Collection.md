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

## Collection

1. Collection
   1. [List](List.md)
      1. ArrayList
      2. Vector
      3. LinkedList 
   2. [Set](set.md) 
      1. HashSet
      2. TreeSet
2. Collection 是集合的父类，具有如下方法
   1. 添加功能 `add(Object a)` `addAll(Collection c)`
   2. 删除功能 `clear()` `remove(Object)` `clearAll(Collection c)`
   3. 判断功能 `contains(Object c)` `containsAll(Collection c)`
   4. 获取功能 `iterator()`
      1. `hasNext()`
      2. `next()`
      3. `remove()`
   5. 长度功能 `size()`
   6. 交集功能 `retainAll(Collection c)`

