// version 3: DFS (not recommended) 
// 递归版dfs 如果递归深度太高 会出现堆栈溢出（递归采用系统隐式栈）
public class Solution {
    private int m;
    private int n;
    public int numIslands(char[][] grid) {
        m = grid.length;
        if (m==0) return 0;
        n = grid[0].length;
        if (n==0) return 0;
        
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') { //相当于 not visited
                    ans++;
                    dfs(grid,i,j);
                }  
            }
        }
        return ans;
    }
    public void dfs(char[][] grid,int i,int j) {
        if ( i < 0 || i >= m || j < 0 || j >= n)
            return;
        if (grid[i][j] == '1') {
            grid[i][j] = '2'; //相当于模板里的set visited 集合
            dfs(grid,i-1,j); 
            dfs(grid,i+1,j);
            dfs(grid,i,j-1);
            dfs(grid,i,j+1);
        }
    }
}

