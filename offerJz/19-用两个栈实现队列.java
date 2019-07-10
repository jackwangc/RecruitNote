import java.util.Stack;

/**
 * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型
 */
public class Solution{

    Stack<Integer> stack1 = new Stack<>();
    Stack<Integer> stack2 = new Stack<>();

    public int pop() {
        while(stack2.isEmpty()){
            while(stack1.isEmpty()){
                int n = stack1.pop();
                stack2.push(n);
            }
        }       
        a = stack2.pop();
        return a;
    }

    public void push(int n){
        stack1.push(n);
    }
}