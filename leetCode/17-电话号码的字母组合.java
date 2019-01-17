import java.util.ArrayList;
import java.util.Map;

/*
*电话号码的字母组合 1
*/
class Solution {
    public List<String> letterCombinations(String digits) {
        ArrayList<String> result = new ArrayList<String>();
        if (digits == null || digits.equals("")) {
            return result;
        }
        Map<Character,char[]> phone = new Map<Character,char[]>();
        phone.put('0', new char[] {});
        phone.put('1', new char[] {});
        phone.put('2', new char[] { 'a', 'b', 'c' });
        phone.put('3', new char[] { 'd', 'e', 'f' });
        phone.put('4', new char[] { 'g', 'h', 'i' });
        phone.put('5', new char[] { 'j', 'k', 'l' });
        phone.put('6', new char[] { 'm', 'n', 'o' });
        phone.put('7', new char[] { 'p', 'q', 'r', 's' });
        phone.put('8', new char[] { 't', 'u', 'v'});
        phone.put('9', new char[] { 'w', 'x', 'y', 'z' });

        StringBuilder foo = new StringBuilder();
        helper(map, digits, sb, result);

        return result;
    }
    public void helper(Map<Character, char[]> map, String digits, StringBuilder sb, ArrayList<String> result) {
        if (sb.length() == digits.length()) {
            result.add(sb.toString());
            return;
        }
        for (char c : map.get(digits.charAt(sb.length()))) {
            sb.append(c);
            helper(map, digits, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
class SolutionTwo {
    public List<String> letterCombinations(String digits) {
       
    }
    private void dfs() {
        
    }
}