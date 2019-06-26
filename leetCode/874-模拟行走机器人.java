import java.util.Map;

public class Solution {
    // 机器人在一个无限大小的网格上行走，从点 (0, 0) 处开始出发，面向北方。该机器人可以接收以下三种类型的命令 
    // -2：向左转 90 度
    // -1：向右转 90 度
    // 1 <= x <= 9：向前移动 x 个单位长度
    // 第 i 个障碍物位于网格点  (obstacles[i][0], obstacles[i][1])
    // 如果机器人试图走到障碍物上方，那么它将停留在障碍物的前一个网格方块上，但仍然可以继续该路线的其余部分
    public int robotSim(int[] commands, int[][] obstacles) {
        
        // 方向集合
        // di 位dx和dy 的相同下标 0：前进 1 右转 2 后退 3 左转
        //   | 前进 | 右转 | 后退 | 左转
        //  x| 0   | 1  |  0  | -1
        //  y| 1   | 0  | -1  |  0
        int[] dx = new int[]{0,1,0,-1};
        int[] dy = new int[]{1,0,-1,0};
        
        // 将障碍点存储到集合当中
        // 移位运算符 确保不会重复坐标点值
        Set<Long> obstacleSet = new HashSet();
        for (int[] obstacle: obstacles) {
            long ox = (long) obstacle[0] + 30000;
            long oy = (long) obstacle[1] + 30000;
            obstacleSet.add((ox << 16) + oy);
        }
        // di 控制方向的下标
        int x = 0, y = 0, di = 0;
        
        // 场景模拟
        int ans = 0;
        for(int cmd:commands){
            if(cmd == -2){ // 左转
                di = (di+3) % 4;
            }else if(cmd == -1){ // 右转
                di = (di+1) % 4;
            }else{
                for (int i = 0; i < cmd; i++) {
                    int nx = x + dx[di];
                    int ny = y + dy[di];
                    
                    // 判断是否有障碍物
                    long code = (((long) nx + 30000) << 16) + ((long) ny + 30000);
                    // 题目比较的是所有路径中最大的平方点
                    if (!obstacleSet.contains(code)) {
                        // 题目理解 遇到障碍物 是停止 还是跳过障碍继续走
                        x = nx;
                        y = ny;
                        // 比较坐标
                        ans = Math.max(ans, x*x + y*y);
                    }
                }
            }
        }
        return ans;  

    }

    // 题解
    // java 集合
    // java map set 用法区别 
    
    // java 移位运算符
    // 取余 取整运算符
    // 如何控制左转和右转倒退


}