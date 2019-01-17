# java 字符串

> string 类型是不可变的当创建后就存在于内存中，与 c++ 相反

## 常用操作

1. 字符串查找
   1. `indexOf`:`int size = str.indexOf("a");` 返回字符串中首次出现索引的地方
   2. `lastIndexOf` 返回最后一次出现索引的地方
2. 获取指定索引位置的字符 `char mychar =  str.charAt(5)`
3. 获取子字符串
   1. `substring(int beginIndex)`,`String substr = str.substring(3);`
   2. `substring(int beginIndex,  int endIndex)` `String substr = str.substring(0,3);`
4. 去除空格  `trim()`
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

## stringBuffer, string, stringBuilder

1. 区别
   1. String 内容不可变，StringBuffer 和StringBuilder 内容可变；
   2. StringBuilder 非线程安全（单线程使用），String 与 StringBuffe r线程安全（多线程使用）；
   3. 如果程序不是多线程的，那么使用 StringBuilder 效率高于 StringBuffer。
2. String 在修改时不会改变对象自身; StringBuffer 在修改时会改变对象自身
3. 初始化
   1. `String s = “abc”;`
   2. `StringBuffer sb1 = new StringBuffer(“123”);`

4. StringBuffer 常用方法有：append 方法、insert 方法、deleteCharAt 方法、reverse 方法
5. 如果要操作少量的数据用 String；多线程操作字符串缓冲区下操作大量数据 StringBuffer；单线程操作字符串缓冲区下操作大量数据 StringBuilder

## char

1. char 表示字符，定义时用单引号 \`\`, `char c = 'a'`
2. String 表示字符串，定义时用双引号 "",`String s = "abc"`
3. char 是基本数据类型；String 是一个类，可以调用方法。
4. char to Int
   1. `int b = '4' - '0'` 利用ascll 码排列实现