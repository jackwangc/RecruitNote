/**
 * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，
 * 例如，如果输入如下4 X 4矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 
 * 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
 */

 public class Solution{
     public void solution(int[][] arr) {
         // 定位点，左上，右下角的两个坐标
         int leftRow = 0,leftCol = 0;
         int rightRow = arr[0].length - 1,rightCol = arr.length - 1;
         while(leftRow < rightRow && leftCol < rightCol){
            // 打印1
            for(int i = 0; i <= rightRow ;i++){
                System.out.println(arr[leftRow][i]);
            }
            // 打印2
            for (int i = 0; i <= rightCol; i++) {
                System.out.println(arr[rightRow][i]);
            }
            // 打印3
            if (leftRow != rightRow){
                for (int i = rightcol - 1; i > leftCol; i--) {
                    System.out.println(arr[rightCol][i]);
                }
            }
            // 打印4
            if (leftCol != rightCol){
                for(int i = rightRow - 1; i > leftRow; i--){
                    System.out.println(arr[i][leftRow]);
                }
            }
            // 左右顶点坐标变换
            leftRow++;
            leftCol++;
            rightRow--;
            rightCol--;
         }
     }
 }