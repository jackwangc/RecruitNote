import java.util.*;
class Solution {
    public void moveZeroes(int[] nums) {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        int n = nums.length;
        for (var i = 0;i<n;i++){
            if (nums[i]!=0){
                arr.add(nums[i]);
            }
        }
        for( var j = arr.length(); j < n;j++ ){
            arr.add(0);
        }
    }
}