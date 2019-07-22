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

> java 中采用的是 TimSort 排序，结合了插入排序，归并排序，二分搜索等算法。1. 对于简单对象的数据，长度在一定范围内，采用快速排序。2.超过长度，采用归并排序。3. 复杂对象数组，长度在一定范围内，使用折半插入排序。4.长度超过，采用归并排序。
> java 中采用归并排序比较多，因为归并排序比较次数最少，并且归并排序是稳定的。原因：对于Java来说，进行比较可能比较耗时（使用Comparator）；但移动元素属于引用赋值，不是庞大对象的拷贝。（因为对于一个hashcode计算复杂的对象来说，移动的成本远低于比较的成本）

### 排序

1. 比较类排序：冒泡排序，快速排序；插入排序，希尔排序；选择排序，堆排序；归并排序
2. 非比较类排序：桶排序，计数排序，基数排序
3. 内排序：操作都在内存中完成，大部分都是内排序
4. 外排序：数据放在磁盘当中，排序通过磁盘和内存的数据传输进行，归并排序和计数排序，桶排序，基数排序。
5. [排序算法实现](排序算法.java)
6. [参考链接](https://www.cnblogs.com/guoyaohua/p/8600214.html)

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

#### 堆排序

> 快速排序比堆排序的效率高很多,因为在堆排序中，用于比较的时间开销比较大。
> 时间开销大的原因：在堆排序（小根堆）的时候，每次总是将最小的元素移除，然后将最后的元素放到堆顶，再让其自我调整。这样一来，有很多比较将是被浪费的，因为被拿到堆顶的那个元素几乎肯定是很大的，而

1. 堆简介
2. 堆的实现
   1. 堆是一棵完全二叉树
   2. 任一结点的值是其子树所有结点的最大值或最小值
   3. 最大值时，称为大顶堆
   4. 最小值时，称为小顶堆
3. [代码实现](heap.java)
4. [图解堆排序](https://www.cnblogs.com/chengxiao/p/6129630.html)

```java
    public static void adjustHeap(int []arr,int i,int length){
        int temp = arr[i];//先取出当前元素i
        for(int k = i * 2 + 1; k < length; k = k*2 + 1){//从i结点的左子结点开始，也就是2i+1处开始
            if(k + 1 < length && arr[k]<arr[k + 1]){//如果左子结点小于右子结点，k指向右子结点
                k++;
            }
            if(arr[k] > temp){//如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
                arr[i] = arr[k];
                i = k;
            }else{
                break;
            }
        }
        arr[i] = temp;//将temp值放到最终的位置
    }
```

#### 线性查找算法 bfprt topk

> 从 n 个元素中找出第 k 大的元素，top(k)，最坏复杂度 为o(n)

1. 将n个元素每5个一组，分成n/5(上界)组。

2. 取出每一组的中位数，任意排序方法，比如插入排序。

3. 递归的调用selection算法查找上一步中所有中位数的中位数，设为 x，偶数个中位数的情况下设定为选取中间小的一个。

4. 用x来分割数组，设小于等于x的个数为k，大于 x 的个数即为 n-k。

5. 若i==k，返回x；若i < k，在小于 x 的元素中递归查找第i小的元素；若 i > k，在大于x的元素中递归查找第i-k小的元素。

6. 终止条件：n=1时，返回的即是i小元素。

```java
/*
* 快排无法保证随机性
*/

// 1. 找出中位数的中位数，返回中位数的位置
public int getArrayMid(int[] a, int l, int r){
    if (l==r){
        return l;
    }
    int i = 1;
    for(; i <= r - l - 5; i += 5 ){ // 将 n 个元素每五个一组
        // 对当前五个数进行排序
        inserSort(a,i,i+4);// 对当前五个数组排序
        swap(a,l + (i - l)/5,i+2); // 将中位数放在前几位上
    }
    if (i < r - l){ // 剩下的不能被5整除的余数
        insertSort(a, i, r - 1);
        swap(a, l + (i - 1)/5, (i + r - 1)/2); // 将最后一组数的中位数
    }
    return getArrayMid(a,l,l + (i - l)/5); // 返回中位数的中位数 id
}
// 2. 插入排序
public void insertSort(int[] arr, int l, int r){
    if (r > l){
        int i = l + 1;
        for(;i < r ;i++){
            int temp = arr[i]
            int flag = i -1;
            while(temp < arr[flag] && flag > l){
                arr[flag + 1] = a[flag];
                flag--;
            }
            a[flag + 1] = temp;
        }
    }
    
}
// 4. 快排基准函数
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
// 3. 快排
public void quickSearch(int[] arr, int l , int r, int k){
    if (l == r){
        return arr[l];
    }
    int mid = getArrayMid(arr, l, r);
    int mid_new = partition (array , left, right, mid );// 根据中位数划分数组，是中位数在最终的位置上
    if (mid_new == k-1){
        return arr[mid_new];
    }
    else if(mid_new < k - 1){
        return quickSearch(arr, mid_new + 1, r, k);
    }else{
        return quickSearch(arr,l,  min_new - 1, k);
    }
}

public void swap(int[] arr, int i, int j){
    int flag = arr[j];
    arr[j] = arr[i];
    arr[i] = arr[j];
}
```




### 二分查找

```java
// 用于排好序的数组

public int solution(int[] arr,int key){
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

1. [二维数组的查找](../offerJz/1-二维数组中的查找.java)
2. [数组中的重复数字](../offerJz/2-数组中重复的数字.java)
3. [构建乘积数组](../offerJZ/3-构建乘积数组.java)
4. [调整数组顺序使奇数位于偶数前面](../offerJz/28-调整数组顺序使奇数位于偶数前面.java)
5. [顺时针打印矩阵](../offerJz/34-顺时针打印矩阵.java)
6. [连续子数组的最大和](../offerJz/45-连续子数组的最大和.java)
7. [把数组排成最小的数](../offerJz/47-把数组排成最小的数.java)
8. [数组中第 k 个最大元素](../leetCode/215-数组中第k个最大的元素.java)
9. [前k个高频元素]()