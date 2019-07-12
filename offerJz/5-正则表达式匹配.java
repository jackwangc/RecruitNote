/**
 * 题目描述
 * 请实现一个函数用来匹配包括'.'和'*'的正则表达式。
 * 模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（包含0次）。
 * 在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但是与"aa.a"和"ab*a"均不匹配
 */
public class Solution {
    /**
     * 思路
     * 正则匹配
     * 1.循环字符串
     * 1.1 第二个字符不是 *
     * 1.1.1 正则第一个字符和字符串不匹配，后移两个字符，继续匹配
     * 1.1.2 正则第一个字符和字符串匹配，
     * 1.1.2.1 正则后移两个 ，字符串不变 ("a*"=0次)
     * 1.1.2.2 字符串后移一个字符，正则后移两个 ("a*"=1次)
     * 1.1.2.3. 模式不变，字符移1 ("a*"= 多次)
     * 1.2 第二个字符时 *
     * 1.2.1 直接判断下一个
     * @param str
     * @param pattern
     * @return
     */
    public boolean match(char[] str, char[] pattern)
    {
        // 特殊情况判断
        if (str.length == 0 || pattern.length == 0){
            return false;
        }
        // 采用递归函数

    }
    // 构造递归函数,参数的选择
    // 1. 数据输入
    // 2. 选择下标
    public boolean matchCore(char[] str, int strIndex, char[] pattern, int patternIndex) {
        // 分枝具体操作
        
        //有效性检验：str到尾，pattern到尾，匹配成功
        if (strIndex == str.length && patternIndex == pattern.length) {
            return true;
        }
        //pattern先到尾，匹配失败
        if (strIndex != str.length && patternIndex == pattern.length) {
            return false;
        }
        // 分枝

        //模式第2个是*，且字符串第1个跟模式第1个匹配,分3种匹配模式；如不匹配，模式后移2位
        if (patternIndex + 1 < pattern.length && pattern[patternIndex + 1] == '*') {
            if ((strIndex != str.length && pattern[patternIndex] == str[strIndex]) || (pattern[patternIndex] == '.' && strIndex != str.length)) {
                return matchCore(str, strIndex, pattern, patternIndex + 2)//模式后移2，视为x*匹配0个字符
                        || matchCore(str, strIndex + 1, pattern, patternIndex + 2)//视为模式匹配1个字符
                        || matchCore(str, strIndex + 1, pattern, patternIndex);//*匹配1个，再匹配str中的下一个
            } else {
                return matchCore(str, strIndex, pattern, patternIndex + 2);
            }
        }
        //模式第2个不是*，且字符串第1个跟模式第1个匹配，则都后移1位，否则直接返回false
        if ((strIndex != str.length && pattern[patternIndex] == str[strIndex]) || (pattern[patternIndex] == '.' && strIndex != str.length)) {
            return matchCore(str, strIndex + 1, pattern, patternIndex + 1);
        }
        return false;
        }   

}