# 数组常用操作

> 相同数据类型的元素按照一定顺序排列的集合，是一块连续的内存空间

## 基础知识

1. int[] 中的int告诉计算机这是一个整型数据，[]告诉计算机这是一个连续存储的内存地址空间，简单点说一个连续数据的存储空间就是数组
2. Java语言中，由于把二维数组看作是数组的数组，数组空间不是连续分配的
3. 数组的优点 get set 时间复杂度 都是 O(1); add remove 时间复杂度 O(n)
4. 数组不能进行添加和删除操作

## 常用操作

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
## 排序算法

### 排序

1. 比较类排序：冒泡排序，快速排序；插入排序，希尔排序；选择排序，堆排序；归并排序
2. 非比较类排序：桶排序，计数排序，基数排序

![排序](../picture/排序算法.png)

### 常考排序

#### 快排

```java
// 先找基准值，再分治
// 基准数左边的数小于基准数  基准数右边的数大于基准数
// 找到比基准数小的数 置换到左边
    private int partition (int[] arr, int left, int right) {
        int pivot = left;  // 基准数
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] < arr[pivot]) {
                swap(arr, i, index);
                index++; // 记录基准数
            }
        }
        swap(arr, pivot, index - 1);
        return index - 1; // 将基准数放在中间的位置
    }
```

#### 归并

```java
// 先分治，再合并
int mid = (int)Math.floor(arr.length/2);
// int mid = start + (end - start) / 2;
// int mid = start + (end - start >> 1);
// 临时存储数组
int[] array2 = new int[arr.length];

    // 合并函数
    private static void merge(int[] array1,int low,int mid,int high){
        int i = low, j = mid + 1, // 左右两个数组下标
        int k = low; // 临时数组下标

        while (i <= mid && j <= high) {
            if (array1[i] <= array1[j])
                array2[k++] = array1[i++];
            else
                array2[k++] = array1[j++];
        }
        // 数组剩下
        while (i <= mid)
            array2[k++] = array1[i++];

        while (j <= high)
            array2[k++] = array1[j++];

        for (i = low; i <= high; i++) {
            array1[i] = array2[i];
        }    
    }

```

### 排序算法 Java 实现

[排序算法](排序算法.java)

### 二分查找

```java
// 用于排好序的数组

public int solution(int[] arr,int target){
    int high = arr.length - 1;
    int low = 0;
    while(low <= high){
        int mid = low + (high - low)/2
        if(a[mid] > key){
            high = mid - 1;
        }else if(a[mid] < key){
            low = mid + 1;
        }else{
            return mid;
        }
    }
}

```

## 数组-剑指offer

1. [二维数组的查找](../offerJZ/1-二维数组中的查找.java)
2. [数组中的重复数字](../offerJZ/数组中重复的数字.java)
3. [构建乘积数组](../offerJZ/构建乘积数组.java)
4. [调整数组顺序使奇数位于偶数前面](../offerJZ/调整数组顺序使奇数位于偶数前面.java)
5. [顺时针打印矩阵](../offerJZ/顺时针打印矩阵.java)
6. [连续子数组的最大和](../offerJZ/连续子数组的最大和.java)
7. [把数组排成最小的数]()