/**
 * 如:{6,-3,-2,7,-15,1,2,2},连续子向量的最大和为8(从第0个开始,到第3个为止)。
 * 给一个数组，返回它的最大连续子序列的和
 */
public class Solution{
    // 该方法只用于连续
    // 动态规划
    public void solutin(int[] arr) {
        if(arr == null || arr.length == 0){
            return;
        }
        // 最大的子数组和 结果
        int result = Integer.MIN_VALUE;
        // 所有累加子数组和
        int sum = 0;
        for (int val : arr) {
            // 小于 0 舍弃累加子数组和
            sum = sum < 0 ? val : sum + val;
            result = Math.max(result,sum);
        }
    }
}