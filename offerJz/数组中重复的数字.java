/**
 * 在一个长度为n的数组里的所有数字都在0到n-1的范围内。 
 * 数组中某些数字是重复的，但不知道有几个数字是重复的。
 * 也不知道每个数字重复几次。请找出数组中任意一个重复的数字。
 */
public class Solution {
    // 1. 修改数组，不建立新的数组
    public int solution(int[] arr,int length) {
        if(arr==null||length<=0){
            return -1;
        }
        for(int i = 0; i < length ; i++){
            while(arr[i] != i){  // 一直交换直到当前
                if(arr[i] == arr[arr[i]]){
                    return arr;
                }
                swap(arr, i, arr[i]);
            }
        }
        return -1;
    }
    // 2. 不修改数组
    // 2.1 建立新的数组
    public boolean solution2(int[] numbers,int length){
        boolean[] k = new boolean[length];
        for (int i = 0; i < k.length; i++) {
            if (k[numbers[i]] == true) {
                duplication[0] = numbers[i];
                return true;
            }
            k[numbers[i]] = true;
        }
        return false;
    }
    // 2.2 二分查找

    // 交换函数
    public void swap(int[] arr,int i ,int j){
        int flag = arr[i];
        arr[i] = arr[j];
        arr[j] = flag;
    }
}
