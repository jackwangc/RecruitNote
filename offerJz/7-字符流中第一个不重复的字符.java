/**
 * 请实现一个函数用来找出字符流中第一个只出现一次的字符。
 * 例如，当从字符流中只读出前两个字符"go"时，第一个只出现一次的字符是"g"。
 * 当从该字符流中读出前六个字符“google"时，第一个只出现一次的字符是"l"。
 */
public class Solution {
    /**
     * 利用字符串的 acsill 码
     */
    //Insert one char from stringstream
    private int[] occurence = new int[256]; // 存储数组
    private int index;  // 位置
    
    public Solution(){
        for(int i=0;i<256;i++){
            occurence[i] = -1;
        }
        index = 0;
    }
    void Insert(char ch)
    {
        if(occurence[ch] == -1)
            occurence[ch] = index; // 第一次插入，为下标值
        else if(occurence[ch] >= 0)
            occurence[ch] = -2; // 第二次插入，标识，删除
        
        index++;
    }
  //return the first appearence once char in current stringstream
    char FirstAppearingOnce()
    {
        char ch = '\0';
        int minIndex = Integer.MAX_VALUE;
        // 找到数组中的最小值，就是第一次出现的位置，下标i 就是该字符的acill 码
        for(int i=0;i<256;i++){
            if(occurence[i] >=0 && occurence[i]<minIndex){
                ch = (char)i;
                minIndex = occurence[i];
            }
        }
        if(ch == '\0')
            return '#';
        return ch;
    }
}