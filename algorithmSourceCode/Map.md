# Map 

> 将键映射到值的对象，一个映射不能包含重复的键，每个键最多只能映射到一个值

1. Map
   1. HashMap
      1. LinkedHashMap 
   2. TreeMap
   3. HashTable 
   
## Map

1. Map 和 Collection 的区别
   1. Map 集合存储的元素是成对出现的，Map 的键是唯一的，值是重复的
   2. Collection 集合存储的元素是单独出现的，set 是唯一的，List 是可重复的 
2. 要点
   1. Map 集合的数据结构针对键有效
   2. Collection 集合的数据结构针对值有效

3. 常用代码
    ```java
    // 1. 添加功能
    v.put(key,value);
    // 2. 删除功能
    v.clear();
    v.remove(key);
    // 3. 判断功能
    v.containsKey(key);
    v.containsValue(key);
    v.isEmpty();
    // 4. 获取功能
    v.get(key);
    // 5. 长度
    v.size();
    ```
4. 哈希表(hash)
    > 关键字 key-拼音 an , 哈希函数 f(), 哈希值 f(key)-页码

    1. 哈希表是一种根据关键码去寻找值的数据映射结构
    2. 通过把关键码值映射到表中一个位置来访问记录，以加快查找的速度
    3. 哈希函数，常用 "Times33" 算法。效率和随机性比较高
       1. `hash = hash*33+str[i];` `hash = (hash << 5) + hash `
       2. 关键字对应记录均匀分配在哈希表里
       3. 关键字极小的变化引起哈希值极大的变化 
    4. 哈希冲突：不同的关键字经过散列函数的计算得到了相同的散列地址
       1. 解决 hash 冲突的办法
       2. 开发定址法，包含一下两种，缺点：空间不足时，无法处理和插入数据，因此需要插入的数据小于空间(装载因子<1>)   
       3. 线性探测：若当前 key 与原来 key 产生相同的哈希地址，则当前 key 存在该地址之后没有存任何元素的地址中
          1. key1：hash(key)+0
          2. key2：hash(key)+1
       4. 二次探测：若当前 key 与原来 key 产生相同的哈希地址，则当前 key 存在该地址后偏移量为（1,2,3...）的二次方地址处
          1.  key1：hash(key)+0
          2.  key2：hash(key)+1^2
          3.  key3：hash(key)+2^2
       5. 链地址法，解决了空间不足的问题
          1. 在原地址新建一个空间，然后以链表结点的形式插入到该空间
    5. 哈希表性能，大多数情况下 查找和插入的复杂度达到O(1),时间主要花在计算 hash 上，当hash值全部映射到一个地址上，退化成链表，查找时间复杂度变为O(n)

## hashMap

> 数组+链表(散列表)+红黑树,当装载因子*初始容量小于散列表元素时，该散列表会再散列，扩容2倍！,当桶数满了的情况，将链表转换成红黑树。

1. 无序，允许为 null, 非同步
2. HashMap 使用第 0 个桶存放键为 null 的键值对
3. 底层由散列表实现
4. 初始容量和装载因子对 HashMap 影响很大
5. 初始容量为16，装载因子默认为0.75，最大容量 2的31次方
6. 扩容，新的值是旧的值的两倍
7. java hash 函数
   ```java
      // HashMap并不是直接拿key的哈希值来用的，它会将key的哈希值的高16位进行异或操作，
      // 使得我们将元素放入哈希表的时候增加了一定的随机性
      static final int hash(Object key) {
         int h;
         return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16); // 增加随机性较少碰撞冲突的可能性
      }
   ```
```java
/*hashMap 建立过程
*/
// 1. 存储结构-链表
    Entry(int h, K k, V v, Entry<K,V> n) {
        value = v;
        next = n;
        key = k;
        hash = h;
    }
// 3. 初始化
// 初始容量为16，装载因子默认为0.75，最大容量 2的31次方

// 2. put 操作,实际上是对拉链法工作原理的实现

// 3. 新建

// 新建一个 HashMap，默认大小为 16；
// 插入 <K1,V1> 键值对，先计算 K1 的 hashCode 为 115，使用除留余数法得到所在的桶下标 115%16=3。
// 插入 <K2,V2> 键值对，先计算 K2 的 hashCode 为 118，使用除留余数法得到所在的桶下标 118%16=6。
// 插入 <K3,V3> 键值对，先计算 K3 的 hashCode 为 118，使用除留余数法得到所在的桶下标 118%16=6，插在 <K2,V2> 前面。

// 4. get 操作
// 计算键值对所在的桶；
// 在链表上顺序查找，时间复杂度显然和链表的长度成正比
// 链表的插入是以头插法方式进行的

// 5. 扩容
// resize() 方法，扩容操作同样需要把 oldTable 的所有键值对重新插入 newTable 中，因此这一步是很费时的。
// 当传入的数组容量不是 2 的 n 次方时，自动将容量转为2的n次

```

## hashTable

HashMap 和HashTable 的区别
不同点 | HashMap | HashTable
---|---|---
数据结构 | 数组+链表+红黑树 | 数组 + 链表
继承的类不同 | AbstractMap | Dictionary
是否线程安全 | 否 | 是
性能高低 | 高 | 低
默认初始化容量 | 16 | 11
扩容方式不同 | 原始容量*2 | 原始容量*2 + 1
底层数组的容量为2的整数次幂 | 要求 | 不要求


## TreeMap

> TreeMap底层是红黑树，它方法的时间复杂度都不会太高:log(n)

1. 由于底层是红黑树，那么时间复杂度可以保证为log(n)

2. key不能为null，为null为抛出NullPointException的

3. 想要自定义比较，在构造方法中传入Comparator对象，否则使用key的自然排序来进行比较

4. TreeMap非同步的，想要同步可以使用Collections来进行封装

5. 有序
   1. TreeMap实现了NavigableMap接口，而NavigableMap接口继承着SortedMap接口，致使我们的TreeMap是有序的 
6. 与 HashMap 的区别
   1. HashMap 速度更快，查找，删除，插入的时间复杂度为 o(1)
   2. TreeMap 的结果是排好序的 


## 使用

```java
// 初始化
Map<String,String> map = new HashMap<String,String>();

// 插入
map.put("key","value");

// 获取元素
String a= map.get("key");

// 移除元素
map.remove("key");

// 清空元素
map.clear();

// 判断为空
boolean a = map.isEmpty()

// 大小
int n = map.size

// 返回 映射的 Set 视图
map.entrySet()

// 返回 键的 Set 视图
map.keySet()

// 返回 map 中值的 Collection 视图

// 循环
// 1. 最快
Map<Integer, Integer> map = new HashMap<Integer, Integer>();
 
for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
 
    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
 
}

// 2. 键
for(Integer a : map.keySet()){
   System.out.println(a);
   // int value = map.get(a); 最慢
}

// 3. 值
for(Integer value : map.values()){
   System.out.println(value);
}
```

---
**参考文章**

1. [hashMap](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247484139&idx=1&sn=bb73ac07081edabeaa199d973c3cc2b0&chksm=ebd743eadca0cafc532f298b6ab98b08205e87e37af6a6a2d33f5f2acaae245057fa01bd93f4#rd)
2. [原理和源码解析](https://www.cnblogs.com/chengxiao/p/6059914.html)









