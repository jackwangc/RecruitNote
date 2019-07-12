import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,
 * 并返回它的位置, 如果没有则返回 -1（需要区分大小写）
 */
public class Solution {
    /**
     * 对 hash 表的使用，并不一定非得键值对应
     * @param str
     * @return
     */
    public int FirstNotRepeatingChar(String str) {
        LinkedHashMap<Character,Integer> hash = new LinkedHashMap<>(256);
        
        int len = str.length();
        // 第一循环向 hash 里增加数据
        for (int i = 0; i < len; i++) {
            char cha = str.charAt(i);
            if (hash.containsKey(cha)){
                int l = hash.get(cha)+1;
                hash.replace(cha, l);
            }else{
                hash.put(cha, 1);
            }
        }
        // hash 表的循环
        for (Map.Entry<Character,Integer> var : hash.entrySet()) {
            if(var.getValue()==1){
                char a = var.getKey();
            }
        }
        // 返回结果
        for (int i = 0; i < len; i++) {
            char cha = str.charAt(i);
            if(hash.get(cha)==1){
                return i;
            }

        }
        return -1;
    }
    /**
     * 输入两个字符串，从第一个字符串中删除出在第二个字符串中出现过的所有字符
     */
    public void solution(String str1,String str2){
        /**
         * 思路
         * 字符床不能修改，转换为数组或者 stringBuffer
         * 1. hash 表
         * 2. 利用数组建立简单hash表
         */
        int len = str2.length();
        String[] arr = new String[256];
         
        for (int i = 0; i < len; i++) {
             arr[i] = String.valueOf(str2.charAt(i)); 
        }
        int len2 = str1.length();
        for (int i = 0; i < len2; i++) {
            String st = String.valueOf(str1.charAt(i));
            for(int j = 0; j < len; j++){
                if(st==arr[j]){
                    str1 = str1.replace(st, "");
                }
            }
        }

    }
    public void solution2(String str1,String str2){
        
        for(int i=0;i<str2.length();i++){
			//int idx=str1.indexOf(str2.charAt(i));
			int idx=0;
			while((idx=str1.indexOf(str2.charAt(i)))!=-1){
				//str1=str1.replace(String.valueOf(str2.charAt(i)), "");
				str1=str1.substring(0,idx)+str1.substring(idx+1);
			}

        }
    }
}