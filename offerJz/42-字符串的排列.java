/**
 * 重点题目
 * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。
 * 例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
 * 全排列,重复，不重复；
 */
import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
public class Solution {
    /**
     * 使用递归，非递归（字典序法，后序研究）
     * 字符串拆分为字符数组
     * 1. 把第一个字符和后面交换
     * 2. 固定一个字符，后面字符串开始第一步
     * @param str
     * @return
     */
    public ArrayList<String> Permutation(String str) {
        
        char[] arr = str.toCharArray();
        ArrayList<String> re = new ArrayList<>();
        arrraySort(arr, re, 0);
        return re;
    }
    /**
     * 1. 资源
     * 2. 结果集
     * 3. 固定数
     * @param arr
     */
    public void arrraySort(char[] arr,ArrayList<String> re,int flag){ // 这种方法修改了当前数据
        if (flag == arr.length - 1){
            // 递归终止条件
            if(!re.contains(arr)){ // 判断是否重复
                re.add(new String(arr)); // 如果求个数 结果换为 int + 1
                return;
            }
        }else{
            // 交换固定操作
            // j 为 此轮要交换的数
            for(int j = flag; j < arr.length;j++){
                swap(arr, flag, j); // 交换
                arrraySort(arr, re, flag+1);
                swap(arr, flag, j); // 交换返回
            }
        }
    }

    public void swap(char[] arr,int i,int j){
        if(i!=j){
            char flag = arr[i];
            arr[i] = arr[j];
            arr[j] = flag;
        }
    }
}
/**
 * 字符串的组合
 * 输入一个字符串，输出该字符串中字符的所有组合。
 * 举个例子，如果输入 abc，它的组合有 a、b、c、ab、ac、bc、abc。
 * 全组合，重复，不重复
 */
public class Solution2{
    public ArrayList<String> Permutation(String str) {
        
        char[] arr = str.toCharArray();
        ArrayList<String> re = new ArrayList<>();
    }

    // 递归f法
    /**
     * 
     * @param arr 
     * @param re
     * @param flag 当前判断数
     */
    public void func(char[] arr,ArrayList<String> re,int flag,StringBuilder tempRe){
        // 递归中止条件
        if (flag == arr.length -1){
            if(!re.contains(arr)){ // 判断是否重复
                re.add(tempRe.toString()); // 如果求个数 结果换为 int + 1
                return;
            }
        }
        for(int i = flag; i < arr.length; i++){
            // 如果题目判断数组可以在此增加判断 数组要先排序
            // 重复 不进行操做 if(i > start && nums[i] == nums[i-1]) continue;
            tempRe.append(arr[i]); // 添加当前数
            func(arr, re, flag+1, tempRe);
            tempRe.deleteCharAt(tempRe.length()-1); // 不添加当前数
        }
    }
}
/**
 * 输入两个整数 n 和 m，从数列 1,2,3...n 中随意取几个数，使其和等于 m，要求列出所有的组合。
 * n 有可能为数组
 */
public class Solution3{
    public void solution(int[] candidates, int target) {
        //组合过程集合
        List<Integer> cans = new ArrayList<Integer>();
        Arrays.sort(candidates);
        if (target < candidates[0])
            return ans;
    
        backtrack(candidates, target, 0, 0, cans);
    
        //由于candidates存在重复数字，ans一定会存在重复集合，利用set集合特性去重
        Set<List<Integer>> set = new HashSet<>(ans);
        ans = new ArrayList<>(set);
        return ans;
    }

    public void backTrack(int[] candidates, int target, int index, List<Integer> cans) {
        // 中止条件
        if(target == 0){
            cans.add(new ArrayList<Integer>(cans));
        }
        for (int i = index; i < candidates.length; i++) {
            if (candidates[i] <= target){
                // 加上数值
                cans.add(candidates[i]);
                backTrack(candidates, target - candidates[i], index, cans);
                // 减去数值
                cans.remove(cans.size() - 1);
            }else{
                return;
            }
        }
    }
    

}
// 排列组合问题
// 排列组合的数学公式
// 解释：
// 排列：p = n! / (n-m)!  从N个元素取M个进行排列。
// 组合：c = n! / (n-m)! * m!  从N个元素取M个进行组合，不进行排列。
// 如何快速写出一个递归
// 参考链接：https://leetcode.com/problems/permutations/discuss/18239/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partioning)
