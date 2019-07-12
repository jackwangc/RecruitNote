/**
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。 
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 */
public class Solution {
    /**
     * 
     * @param str
     * @return
     */
    public boolean isNumeric(char[] str) {
        if (str.length < 1)
            return false;
         
        boolean flag = scanInteger(str);
        // 如果出现小数点 
        if (index < str.length && str[index] == '.') {
            index++;
            // 小数点前后都可以没有整数，也可以都有
            flag = scanUnsignedInteger(str) || flag;
        }
        // 如果出现 e
        if (index < str.length && (str[index] == 'E' || str[index] == 'e')) {
            index++;
            // e 前面没有数字 失败
            // e 后面没有数字 失败
            flag = flag && scanInteger(str);
        }
         
        return flag && index == str.length;
         
    }
    // 扫描以+ - 开头的 0-9 的数位
    private boolean scanInteger(char[] str) {
        if (index < str.length && (str[index] == '+' || str[index] == '-') )
            index++;
        return scanUnsignedInteger(str);
         
    }
    // 扫描 0-9 的数位
    private boolean scanUnsignedInteger(char[] str) {
        int start = index;
        while (index < str.length && str[index] >= '0' && str[index] <= '9')
            index++;
        return start < index; //是否存在整数
    }

    // 正则表达式解法
    public boolean isNumeric2(char[] str) {
        String string = String.valueOf(str);
        return string.matches("[\\+\\-]?\\d*(\\.\\d+)?([eE][\\+\\-]?\\d+)?");
    }
}