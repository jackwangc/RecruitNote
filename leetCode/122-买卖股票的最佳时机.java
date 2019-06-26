public class Solution {
    // 简单贪心
    
    // 自己代码
    public int maxProfit(int[] prices) {
        
        int len = prices.length;
        if(len==1||len==0){
            return 0;
        }
        int flag =prices[0];
        int result = 0;
        for (int i = 1; i < len; i++) {
            if(flag>prices[i]){  // 第一天价格比第二天价格大
                flag = prices[i]; // 第二天再买
            }else{ // 第一天价格比第二天价格小
                result = prices[i]-flag+result;
                flag = prices[i];
            }
        }
        return result;
    }

    // 答案
    public int maxProfit2(int[] prices){
        int profit = 0;
        for (int i = 0; i < price.length-1; i++) {
            int diff = prices[i+1] - prices[i];
            if(diff>0){
                profit += diff;
            }
        }
        return profit;
    }

    // 改进意见
    // 访问i+1元素 避免数组越界 ，可以使长度 -1；
}