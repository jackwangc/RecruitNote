import java.util.ArrayList;
import java.util.List;

/**
 * stack 栈 用动态数组实现
 */
 class stack {
    private List<Integer> data;
    public stack(){
        data = new ArrayList<>();
    }
    public void push(int x) {
        data.add(x);
    }
    public boolean isEmpty() {
        return data.isEmpty();
    }
    public int top() {
        return data.get(data.size()-1);
    }
    public boolean pop() {
        if(isEmpty()){
            return false;
        }
        data.remove(data.size()-1);
        return true;
    }
}

// dfs 1
// boolean DFS(Node cur, Node target, Set<Node> visited) {
//     return true if cur is target;
//     for (next : each neighbor of cur) {
//         if (next is not in visited) {
//             add next to visted;
//             return true if DFS(next, target, visited) == true;
//         }
//     }
//     return false;
// }

// dfs 2
// boolean DFS(int root, int target) {
//     Set<Node> visited;
//     Stack<Node> s;
//     add root to s;
//     while (s is not empty) {
//         Node cur = the top element in s;
//         return true if cur is target;
//         for (Node next : the neighbors of cur) {
//             if (next is not in visited) {
//                 add next to s;
//                 add next to visited;
//             }
//         }
//         remove cur from s;
//     }
//     return false;
// }