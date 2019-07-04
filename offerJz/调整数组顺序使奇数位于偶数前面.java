/**
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，
 * 使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分，
 * 并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 */

 public class Solution{
    // 考察排序稳定性 
    // 1. 插入排序法
    public void solution(int[] arr){
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            int temp = arr[i];
            int j = i;
            while( j > 0 && temp < array[j - 1]){
                arr[j] = arr[j - 1];
                j--;
            }
            if(j!=i){
                arr[j] = temp;
            }
        }
    }
    // 2. 插入法解决问题
    public void solution2(int[] arr){
        int len = arr.length;
        int k = 0;
        for(int i = 0; i < len; i++){
            int j = i;          
            if(arr[j]%2==1){
                while(j>k){
                    swap(arr,j,k);
                    j--;
                }
                k++;
            }
        }
    }
    // 3. 指针法 无法保证顺序一致
    public void solution3(int[] arr){
        int len = arr.length;
        int low = 0;
        int high = len - 1;
        while(low < high){
            while(low < high && arr[low] % 2 == 1){
                low++;
            }
            while(low < high && arr[low] % 2 == 0){
                high--;
            }
            if(low < high){
                swap(arr,low,high);
            }
        }
    }
    // swap 函数
    public void swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = arr[i];
    }
 }