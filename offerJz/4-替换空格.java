/**
 * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串
 */
public class Solution {
    // 1. 查询空格数
    // 2，从后往前填充数据
    public String replaceSpace(StringBuffer str) {
        int len = str.length();
        int spaceNum = 0;
        for (int i = 0; i < len; i++) {
            if(str.charAt(i)==' '){
                spaceNum++;
            }
        }
        // 扩容的数目要判断好
        int newLen = len + spaceNum*2;
        // 设置 stringBuffer 的长度
        str.setLength(newLen);
        // 要注意数组的 get 和 set,主要是下标的选取。数组下标，从0到i-1;
        for(int i = len-1 ; i >= 0 && newLen > i; i--){
            if(str.charAt(i) == ' '){
                str.setCharAt(newLen--, '0');
                str.setCharAt(newLen--, '2');
                str.setCharAt(newLen--, '%');               
            }else{
                str.setCharAt(newLen--, str.charAt(i));
            }
        }
        return str.toString();
    }
}
// 字符串用法
// 如何遍历获取 stringBuffer charAt
// 更改 stringBuffer 长度 setLength
// string 
// stringBuffer 装 string
/*
问题1：替换字符串，是在原来的字符串上做替换，还是新开辟一个字符串做替换！
问题2：在当前字符串替换，怎么替换才更有效率（不考虑java里现有的replace方法）。
      从前往后替换，后面的字符要不断往后移动，要多次移动，所以效率低下
      从后往前，先计算需要多少空间，然后从后往前移动，则每个字符只为移动一次，这样效率更高一点。
问题3： for 循环里的 i++ ++i 是一样的作用；
*/