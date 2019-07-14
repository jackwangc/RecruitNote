import java.util.ArrayList;

/**
 * 堆
 * 是一棵完全二叉树，二叉树的左右下标
 * 任一结点的值是其子树所有结点的最大值或最小值.
 * 参考链接 [https://www.cnblogs.com/chengxiao/p/6129630.html]
 */

 public class Solution{
    private void solution(int[] arr) {
        // 1. 构建堆
        for(int i=arr.length/2-1;i>=0;i--){
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(arr,i,arr.length);
        }
        // 2. 调整堆
        for(int j=arr.length-1;j>0;j--){
            swap(arr,0,j);//将堆顶元素与末尾元素进行交换
            adjustHeap(arr,0,j);//重新对堆进行调整
        }

    }

    public static void adjustHeap(int []arr,int i,int length){
        int temp = arr[i];//先取出当前元素i
        for(int k = i * 2 + 1; k < length; k = k * 2 + 1){//从i结点的左子结点开始，也就是2i+1处开始
            if( k + 1 < length && arr[k] < arr[k+1]){//如果左子结点小于右子结点，k指向右子结点
                k++; // 转向右子树进行调整
            }
            if(arr[k] > temp){ // 如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
                arr[i] = arr[k]; // 交换过程 
                i = k;
            }else{
                break;
            }
        }
        arr[i] = temp;//将temp值放到最终的位置
    }
    public static void swap(int []arr,int a ,int b){
        int temp=arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
 }
 // 最小堆
 public class MinHeap <E extends Comparable<E>>{
    private Array<E> data;
    public MinHeap(int capacity){
        data = new Array<>(capacity);
    }

    public MinHeap(){
        data = new Array<>();
    }

    // 返回堆中的元素个数
    public int size(){
        return data.getSize();
    }

    // 返回一个布尔值, 表示堆中是否为空
    public boolean isEmpty(){
        return data.isEmpty();
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的父亲节点的索引
    private int parent(int index){
        return (index - 1) / 2;
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的左孩子节点的索引
    private int leftChild(int index){
        return index * 2 + 1;
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的右孩子节点的索引
    private int rightChild(int index){
        return index * 2 + 2;
    }

    // 1. 插入
    public void add(E e){
        //特性1：新插入的元素首先放在数组最后，保持完全二叉树的特性
        data.addLast(e);
        siftUp(data.getSize() - 1);
    }
    // 调整树
    public void siftUp(int i){
        // 特性2：比较插入值和其父结点的大小关系，小于父结点则用父结点替换当前值，index位置上升为父结点
        // 当上浮元素大于父亲，继续上浮。并且不能上浮到0之上
        // 直到i 等于 0 或 比 父亲节点小了
        while (i > 0 && data.get[i].compareTo(data.get(parent(i))) >0) {
            data.swap(i, parent(i));
            i = parent(i); // 这句话让i来到新的位置，使得循环可以查看新的位置是否还要大
        }
    }
    // 交换方法
    public void swap(int i, int j){
        if (i < 0 || i >= size || j < 0 || j >= size)
            throw new IllegalArgumentException("Index is illegal.");
        E temp = data[i];
        data[i] = data[j];
        data[j] = data[i];
    }
    // 2. 删除
    // 将最后一个元素填充到堆顶，然后不断下沉这个元素

    // 找到堆顶元素
    public E findMin(int k) {
        return data.get(k);
    }

    public void delete(int k) {
        E ret = findMin(k);
        swap(k, data.size() - 1);
        arr.removeLast();
    }
    // k 元素位置下沉,只需要向左下沉，不需要和右边比较
    private void siftDown(int k){
        while(leftChild(k) < data.getSize()){
            int j = leftChild(k); // 在此轮循环中,data[k]和data[j]交换位置
            if( j + 1 < data.getSize() &&
                data.get(j + 1).compareTo(data.get(j)) < 0 )
                j ++;
           // data[j] 是 leftChild 和 rightChild 中的最小值
            if(data.get(k).compareTo(data.get(j)) >= 0 )
               break;

            data.swap(k, j);
            k = j;
        }
    }
}