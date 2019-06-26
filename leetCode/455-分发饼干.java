import java.util.Arrays;

public class Solution {

    // 假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。
    // 对每个孩子 i ，都有一个胃口值 gi ，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j ，都有一个尺寸 sj 。
    // 如果 sj >= gi ，我们可以将这个饼干 j 分配给孩子 i ，这个孩子会得到满足。
    // 你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
    public int findContentChildren(int[] g, int[] s) {
        // 贪心策略
        // 用最小的饼干去匹配最小的胃口
        Arrays.sort(g); // 从小到大排序  g 是孩子胃口集合 s 是饼干集合 
        Arrays.sort(s); 
        while(i < g.length && j < s.length){
            if(g[i]<s[j]){
                i++;
            }
            j++;
        }
        return i;
    }

    // 题解
    // for while 用法区别
    // 1. 对于两个数组下标的增加 规则增加 双重 for 循环
    // 2. 不规则增加改变 while

}