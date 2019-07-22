import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
 * 1.堆的使用 PriorityQueue
 * 2.hash 表的使用
 */
class Solution{
    public List<Integer> topKFrequent(int[] nums, int k) {
        HashMap<Integer,Integer> map = new HashMap<>();
        // 获取次数
        for (int num : nums) {
            if (map.containsKey(num)){
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        // 构造堆，默认最小堆
        PriorityQueue<Integer> pq = new PriorityQueue<>(
            // 自定义比较器
            new Comparator<Integer>() {
                public int compare(Integer a, Integer b) {
                    return map.get(a) - map.get(b);
                }
            }
        );
        // 填充堆
        for (Integer key : map.keySet()) {
            if (pq.size() < k) {
                pq.add(key);
            } else if (map.get(key) > map.get(pq.peek())) {
                pq.remove(); // 删除队首元素
                pq.add(key); // 向队列中插入数据
            }
        }
        // 输出数据
        // 取出最小堆中的元素
        List<Integer> res = new ArrayList<>();
        while (!pq.isEmpty()) {
            res.add(pq.remove());
        }
    }
}
// Queue<Integer> pq = new PriorityQueue<Integer>(11,
//                 new Comparator<Integer>() {
//                     public int compare(Integer i1, Integer i2) {
//                         return i2 - i1;  // 顺序变换 大顶堆
//                     }
//                 });
// pq.remove(); // 删除队首元素
// 