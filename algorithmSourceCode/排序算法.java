import java.util.Arrays;

/*
 * @Author: jackwang
 * @Date: 2019-01-04 19:54:19
 * @LastEditTime: 2019-01-14 19:20:58
 * @decription: 经典排序算法总结
 */

class ArraySort {
    
    // 1. 冒泡排序
    public int[] bubbleSort(int[] array) {
        // 如果想要不改变输入元素
        // int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        int len = array.length;
        for (int i = 0; i < len; i++){
            boolean flag = true; // 优化标记 当一次排序没有发生元素交换时，排序已经完成
            for (int j = 0; j < len - i; j++){
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    flag = false; // 优化标记
                } 
            }
            // 判断优化
            if (flag) {
                break;
            }
        }
        return array;  
    }
    
    // 2. 选择排序
    public int[] selectSort(int[] array) {
        // 每次选择比较出最小值放在最前面
        for (int i = 0; i < array.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[min] > array[j]){
                    min = j;
                }
            }
            
            if (i!=min) {
                int temp = array[i];
                array[i] = array[min];
                array[min] = temp;
            }
        }
        return arr;
    }

    // 3. 插入排序
    public int[] insertSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            int j = i;
            while (j > 0 && temp < array[j - 1]){
                arr[j] = arr[j - 1];
                j--;
            }
            if(j!=i){
                arr[j] = temp;
            }
        }
        return array;
    }

    public void insertSort2(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int value = arr[i];
            int j = 0;//插入的位置
            for (j = i-1; j >= 0; j--) {
                if (arr[j] > value) {
                    arr[j+1] = arr[j];//移动数据
                } else {
                    break;
                }
            }
            arr[j+1] = value; //插入数据
        }
    }

    // 4. 希尔排序  希尔排序是插入排序的改进
    public int[] ShellSort(int[] arr) {
        int gap = 1;
        while (gap < arr.length){
            gap = gap * 3 + 1;
        }
        while (gap > 0){
            for (int i = gap; i < arr.length; i++){
                int tmp = arr[i];
                int j = i - gap;
                while (j >= 0 && arr[j] > tmp) {
                    arr[j + gap] = arr[j];
                    j -= gap;
                }
                arr[j + gap] = tmp;
            }
            gap = (int)Math.floor(gap / 3);
        }
        return arr;
    }
    // 5. 快速排序
    public int[] quickSort(int[] arr) {
        return quickSort(arr, 0, arr.length - 1);  
    }
    // 递归
    private int[] quickSort(int[] arr, int left, int right){
        if (left < right){
            int partitionIndex = partition(arr, left, right);  // 寻找基准值的交换位置
            quickSort(arr, left, partitionIndex - 1);
            quickSort(arr, partition + 1, right);
        }
        return arr;
    }
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
    // 另外一种判断基准位置的方法
    private int partition2 (int[] arr, int left, int right) {
        int i = left, j = right;
        int tmp = arr[left]; // 基准数
        // 重复步骤，直到左右两个指针相遇
        while (i != j) {
            // 从右往左扫描，找到一个小于基准值的元素
            while (i < j && arr[j] > tmp) {
                j--;
            }
            // 从左往右找到一个大于基准值的元素
            while (i < j && arr[i] < tmp){
                i++;
            }
            // 交换这两个元素的位置
            if (i < j) {
                swap(arr,i,j);
            }
        }
        // 再将基准值与左侧最右边的元素交换
        arr[left] = a[i];
        arr[i] = tmp;
        return i;
    }
    // 6. 堆排序
    // 堆排序的思路
    // 将待排序序列构造成一个大顶堆，此时，整个序列的最大值就是堆顶的根节点。
    // 将其与末尾元素进行交换，此时末尾就为最大值。
    // 然后将剩余n-1个元素重新构造成一个堆，这样会得到n个元素的次小值。如此反复执行，便能得到一个有序序列了
    public int[] heapSort(int[] arr) {
        int len = arr.length;
        buildMaxHeap(arr, len); // 构建大顶堆
        for (int i = len - 1; i > 0; i--) {
            swap(arr, 0 , i); // 交换顶堆头部元素到末尾
            len--;
            heapify(arr, 0, len); // 重新构造大顶堆
        }
        return arr; 
    }
    // 构建大顶堆
    // 堆 具有以下性质的完全二叉树 大顶堆，每个节点的值大于或等于其左右孩子节点的值
    // 小顶堆 每个节点的值小于或等于其左右孩子结点的值
    private void buildMaxHeap(int[] arr, int len) {
        for (int i = (int)Math.floor(len/2); i >= 0; i--) {  // (int)Math.floor(len/2) 最后一个非叶子节点；i-- 寻找下一个非叶子节点
            heapify(arr, i, len); // 第一次被排除掉 从 i-1 开始
        }
    }
    // 堆调整
    private void heapify(int[] arr, int i, int len) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;
        // 比较单个节点序列 寻找节点最大值的下标
        if (left < len && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < len && arr[right] > arr[largest]) {
            largest = right;
        }
        // 如果最大值不是父节点，交换，然后继续调整堆
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, largest, len); // 交换会导致子节点结构混乱，调整子节点
        }
    }
    // 7. 归并排序
    public int[] mergeSort(int[] arr) {
        if (arr.length < 2){
            return arr;
        }
        int middle = (int)Math.floor(arr.length / 2);
        int[] left = Arrays.copyOfRange(arr, 0, middle);
        int[] right = Arrays.copyOfRange(arr,middle,arr.length);
        return merge(mergeSort(left),mergeSort(right));
    }
    // 
    private int[] merge(int[] left, int[] right){
        int[] result = new int[left.length + right.length];
        int i = 0;
        while (left.length > 0 && right.length > 0) {
            if (left[0] <= right[0]){
                result[i++] = left[0]; // 将比较结果移动到结果数组中
                left = Arrays.copyOfRange(left,1,left.length);
            }else{
                result[i++] = right[0];
                right = Arrays.copyOfRange(right,1,right.length);
            }
        }
        while (left.length > 0) {  // 将剩余元素放进结果中,当有一边为0时，进入这个循环
            result[i++] = left[0];
            left = Arrays.copyOfRange(left,1,left.length);
        } 
        while (right.length > 0) {
            result[i++] = right[0];
            right = Arrays.copyOfRange(right,1,right.length);
        }
        return result;
    }
    // 归并排序，好理解的版本
    public void mergeSort2(int[] arr){
        // 1. 数组长度
        int len = arr.length;
        // 2. 分枝
        sort(arr, 0, len - 1);        
    }
    private void sort(int[] arr, int left, int right){
        if (left < right) {
            int mid = left + (right - left) >> 1;
            sort(arr, left, mid);
            sort(arr, mid, right);
            // 3. 合并         
            merge(data, left, mid + 1, right);
        }
    }
    public static void merge(int[] data, int left, int center, int right) {
        // 临时数组
        int[] tmpArr = new int[data.length];
        // 右数组第一个元素索引
        int mid = center + 1;
        // third 记录临时数组的索引
        int third = left;
        // 缓存左数组第一个元素的索引
        int tmp = left;
        while (left <= center && mid <= right) {
        // 从两个数组中取出最小的放入临时数组
            if (data[left] <= data[mid]) {
                tmpArr[third++] = data[left++];
            } else {
                tmpArr[third++] = data[mid++];
            }
        }
        // 剩余部分依次放入临时数组（实际上两个 while 只会执行其中一个）
        while (mid <= right) {
            tmpArr[third++] = data[mid++];
        }
        while (left <= center) {
            tmpArr[third++] = data[left++];
        }
        // 将临时数组中的内容拷贝回原数组中
        // （原 left-right 范围的内容被复制回原数组）
        while (tmp <= right) {
            data[tmp] = tmpArr[tmp++];
        }       
    }

    // 交换函数  java 中不能使用 交换函数，因为 java 的参数传递 传递的是值，没有传递地址
    // public void swap(num1, num2) {
    //   
    // }
    // 交换函数 只能将数组一并传入
    public void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
