import java.util.*;
public class main {
    public static void main(String[] args) {
        int[] arr = new int[]{8,4,5,7,1,3,6,2,12,7,9};
        int[] result = mergeSort(arr);
        System.out.println(result);
        System.out.println("result");
    }
    // 7. 归并排序
    public static int[] mergeSort(int[] arr) {
        if (arr.length < 2){
            return arr;
        }
        int middle = (int)Math.floor(arr.length / 2);
        int[] left = Arrays.copyOfRange(arr, 0, middle);
        int[] right = Arrays.copyOfRange(arr,middle,arr.length);
        return merge(mergeSort(left),mergeSort(right));
    }
    //
    private static int[] merge(int[] left, int[] right){
        int[] result = new int[left.length + right.length];
        int i = 0;
        while (left.length > 0 && right.length > 0) {
            if (left[0] <= right[0]){
                result[i++] = left[0]; 
               
                left = Arrays.copyOfRange(left,1,left.length);
            }else{
                result[i++] = right[0];
                right = Arrays.copyOfRange(right,1,right.length);
            }
        }
        while (left.length > 0) {  // 将剩余元素放进结果中
            result[i++] = left[0];
            left = Arrays.copyOfRange(left,1,left.length);
        } 
        while (right.length > 0) {
            result[i++] = right[0];
            right = Arrays.copyOfRange(right,1,right.length);
        }
        System.out.println(Arrays.toString(result));
        return result;
    }
}