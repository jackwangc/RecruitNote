# java 字符串

> string 类型是不可变的当创建后就存在于内存中，与 c++ 相反。string 不是 java 的基础类型

## 常识

1. String 由char 数组构成
2. 字符串常量池存储在方法区内 
3. 常量池主要存储在方法区中，当一个字符串被创建的时候，首先会去常量池中查找，如果找到了就返回对改字符串的引用，如果没找到就创建这个字符串并塞到常量池中
4. String 为什么不可变 
   1. 不可变，多线程安全
   2. 节省空间，只有当字符串是不可变的，字符串池才有可能实现。字符串池的实现可以在运行时节约很多heap空间。
   3. 因为字符串是不可变的，所以在它创建的时候hashcode就被缓存了，不需要重新计算。这就使得字符串很适合作为Map中的键，字符串的处理速度要快过其它的键对象。这就是HashMap中的键往往都使用字符串。
   4. 缺点：制造垃圾
5. 字符串常量池有助于为Java运行时节省大量空间，虽然创建字符串时需要更多的时间，为了让数据不冲突进行共享

6. 存储
   1. 对象的引用存储在栈中
   2. `String s1 = "china"; ` 在常量池中存储
   3. `String ss1 = new String("china"); `先去常量池中查找是否已经有了 ”china” 对象，如果没有则在常量池中创建一个此字符串对象，然后堆中再创建一个常量池中此 ”china” 对象的拷贝对象。

## 常用操作

1. 字符串查找
   1. `indexOf`:`int size = str.indexOf("a");` 返回字符串中首次出现索引的地方
   2. `lastIndexOf` 返回最后一次出现索引的地方
2. 获取指定索引位置的字符 `char mychar =  str.charAt(5)`
3. 获取子字符串
   1. `substring(int beginIndex)`,`String substr = str.substring(3);`
   2. `substring(int beginIndex,  int endIndex)` `String substr = str.substring(0,3);`
4. 去除空格  `trim()` 只删除头尾的空白字符串
5. 字符串替换 `String newstr = str.replace("a", "A")`
6. 判断字符串是否相等 `equals(String otherstr)`
7. 遍历字符串
   
    ```java
    // 1
    String str = "asdfghjkl";
    for(int i=0;i<str.length();i++){
        char ch = str.charAt(i);
    }
    // 2
    char[] c=s.toCharArray();
    for(char cc:c){
        ...//cc直接用了
    }
    // 3
    for(int i=0;i<str.length();i++){
        String subStr = str.substring(i, i+1)
    }
    ```
8. 数组相关，用 length；集合相关，用 size()；字符串相关，用 length()
9. 字符串加法
    
    ```java
    // 两者的区别
    String a = "a";
    String b = "b";
    String c = a + b;  // String c = new StringBuffer().append(a).append(b).toString(); 在堆内存中新建

    String s1="a"+"b"+"c"; // java 中常量优化机制，编译时 s1 已经成为 abc 在常量池中查找创建，s2 不需要再创建
    String s2="abc";

    ```

## stringBuffer, string, stringBuilder

1. 区别
   1. String 内容不可变，StringBuffer 和StringBuilder 内容可变；因为 String 是由一个由 final 修饰的 char[] 数组实现的
   2. StringBuilder 非线程安全（单线程使用），String 与 StringBuffer 线程安全（多线程使用）；
   3. 如果程序不是多线程的，那么使用 StringBuilder 效率高于 StringBuffer。
2. String 在修改时不会改变对象自身; StringBuffer 在修改时会改变对象自身
3. 初始化
   1. `String s = “abc”;`
   2. `StringBuffer sb1 = new StringBuffer(“123”);`

4. StringBuffer 常用方法有：append 方法、insert 方法、deleteCharAt 方法、reverse 方法
5. 如果要操作少量的数据用 String；多线程操作字符串缓冲区下操作大量数据 StringBuffer；单线程操作字符串缓冲区下操作大量数据 StringBuilder
6. 原理
   1. 默认长度为16
   2. 也是 char 数组，扩容是复制数据到新的数组
   3. 扩容：原来的容量+2

## char

1. char 表示字符，定义时用单引号 \`\`, `char c = 'a'`
2. String 表示字符串，定义时用双引号 "",`String s = "abc"`
3. char 是基本数据类型；String 是一个类，可以调用方法。
4. char to Int
   1. `int b = '4' - '0'` 利用ascll 码排列实现
   2. `int b = 'a'`
   3. `char a = (char)12`

## ascll

> 标准128，扩展256

1. 常见ASCII码的大小规则：`0~9<A~Z<a~z`
2. 数字比字母要小。如 “7”<“F”；
3. 数字0比数字9要小，并按0到9顺序递增。如 `“3”<“8” `；
4. 字母A比字母Z要小，并按A到Z顺序递增。如 `“A”<“Z”` ；
5. 同个字母的大写字母比小写字母要小32。如 `“A”<“a”` 。
6. 个常见字母的ASCII码大小： `“A”为65；“a”为97；“0”为 48 `。

## 常见操作

```java

String test = "adcda";
String test1 = " abcda ";
// String 常用操作 
// 1. 获取指定索引位置的字符
char specialChar = test.charAt(3);
// 2. 获取字符串中首次出现索引的地方
int position = test.indexOf("a");
// 3. 去除头尾空白字符串
String testT = test1.trim();
// 4. 判断字符串是否相等用 equals 比较的是值
System.out.println(testT.equals(test))
// 5. 获取子字符串
String test2 = test.substring(3);  // substring(int beginIndex)
String test3 = test.substring(1,4);  // substring(int beginIndex,int endIndex)
// 6. 遍历字符串
char[] c = test.toCharArray(); // 将字符串转换为 char 数组
for(char cc:c){              // 这种方法适用于数组结构的数据
    System.out.println(cc);
}
// 7. 拼接字符串
String cons = test.concat(test1);  // 将字符串拼接起来 创建了一个新的对象
// 8. 删除
String test  = ("chaojimali");
test = test.replace("chaoji",""); // 两个参数均为字符串 可以替换和删除

str1=str1.substring(0,idx)+str1.substring(idx+1)
// StringBuilder 常用操作 算法题中多用 StringBuilder
// 1. 创建
StringBuilder sb = new StringBuilder("1q3e5t7u");
// 2. 追加
sb.append("ww");  // append 并不会创建新的对象
// 3. 反转
sb.reverse();  // 反转一个字符串：stringBuilder
String rever = new StringBuilder("hda").reverse().toString(); // 如何反转一个给定字符串返回 string
// 4. 删除 插入 替换
sb.delete(start,end);
sb.insert(int offset,int i); // 插入 int 参数到这个字符串的指定位置 
sb.replace(start,end,str);

// 字符转换为字符串
String s = String.valueOf('c'); // 单个字符
String ss = String.valueOf('dada') // char 数组
String sss = new String(ch) // ch 为 char 数组
```

## 题目

1. [替换空格](../offerJz/4-替换空格.java)
2. [正则表达式匹配](../offerJz/5-正则表达式匹配.java)
3. [表示数值的字符串](../offerJz/6-表示数值的字符串.java)
4. [字符流中第一个不重复的字符](../offerJz/7-字符流中第一个不重复的字符.java)
5. [字符串的排列](../offerJz/42-字符串的排列.java)
6. [把数组排成最小的数](../offerJz/47-把数组排成最小的数.java)