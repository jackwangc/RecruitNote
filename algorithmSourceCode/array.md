# 数组常用操作

```java
// 1. 一维数组初始化
int[] array = new int[3]; // 如果初始化时没有赋值，int[] 为0；char[] 为"" ; boolean 为 false; 对象类型的数组为 null;double[] 为 0.0
array[0] = 1;

int[] array = new int[] {1,2,3,4};
int[] array = { 1,2,3,4}

// 2. 二维数组初始化
// 二维数组本质还是一个一维数组，将数组元素引用到另外一个数组
int[][] array1 = new int[10][10];
int array1[][] = new int[10][10];
int array[][] = {{1,2,3},{1,2,3}};
int array[][] = new int[][]{{1,2}{1,2}};
int[][] array = new int[3][] // 不定长二维数组，只需要获取第一个数组的长度即可
array[0] = new int[1];
// 3. 获取数组长度
int len = array.length;
// 4. 动态数组初始化
ArrayList al = new ArrayList();
al.add("a"); // 添加元素
al.size(); // 大小
al.remove(1); // 移除指定位置的元素
// 5. 遍历数组
for(int i : array){
    System.out.println(i)
}
// 6. 数组排序
Arrays.sort(array); // 自带排序；jdk 对于基本类型的排序采用了快排，对于对象数组的采用了改进的归并排序

// 7. 数组常用 API
// 7.1 输出数组
int[] array = { 1, 2, 3 };
System.out.println(Arrays.toString(array)); // 不进行转换输出的时地址
// 7.2 equals
array.equals(b)  // 比较的内存地址是否相同 Arrays.equals(a,b) 比较的是值是否相同
boolean b = Arrays.asList(stringArray).contains("c");  // 转换为List 
int[] combinedIntArray = ArrayUtils.addAll(intArray, intArray2); // 合并
ArrayUtils.reverse(intArray); // 翻转
int[] removed = ArrayUtils.removeElement(intArray, 3); // 移除

```
[排序算法](algorithmSourceCode/排序算法.java)