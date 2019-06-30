# set

1. Set
   1. HashSet
      1. LinkedHashSet 
   2. TreeSet 

## 1. HashSet

> 底层数据结构是哈希表+红黑树

1. 实现Set接口

2. 不保证迭代顺序

3. 允许元素为null

4. 底层实际上是一个HashMap实例
   1. Set集合如果添加的元素相同时，是根本没有插入的(仅修改了一个无用的value值)！从源码(HashMap)中也看出来，==和equals()方法都有使用！

5. 非同步

6. 初始容量非常影响迭代性能

## 2. TreeSet

> 底层数据结构是红黑树，保证元素的排序方式

1. 实现NavigableSet接口

2. 可以实现排序功能

3. 底层实际上是一个TreeMap实例

4. 非同步

## 3. LinkedHashSet

> 底层数据结构由哈希表+双向链表组成

1. 迭代是有序的

2. 允许为null

3. 底层实际上是一个HashMap+双向链表实例(其实就是LinkedHashMap)…

4. 非同步

5. 性能比HashSet差一丢丢，因为要维护一个双向链表

6. 初始容量与迭代无关，LinkedHashSet迭代的是双向链表

## 使用

```java
// 初始化
Set<String> set = new HashSet<String>();

// 容量
int n = set.size();

// 增加元素
set.add("a");

// 判断是否为空
boolean a = set.isEmpty();

// 是否含有元素
boolean a = set.contains(Object c);

// 转换为数组

String[] a = new String[set.size()];
set.toArray(a);

// 移除
set.remove("a");

// 清空
set.clear();

// 遍历
for(String a:set){
   System.out.println(a);
}
```







