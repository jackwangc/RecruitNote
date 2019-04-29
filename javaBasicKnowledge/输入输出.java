import com.sun.java_cup.internal.runtime.Scanner;

//
class test{
    // 单行输入
    public static void main1(String[] args) {
        Scanner sc = new Scanner(System.in);
        //获取输入的整数序列
        String str = sc.nextLine();
        String[] strings = str.split(" ");
        //转为整数数组
        int[] ints = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            ints[i] = Integer.parseInt(strings[i]);
        }
        System.out.println(getTargetMax(ints));
    
    }
    // 多行输入

    public static void main2(String[] args) {
        Scanner sc = new Scanner(System.in);
        //获取输入的整数序列
        String str = sc.nextLine();
        String[] strings = str.split(" ");
        //转为整数数组
        int[] ints = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            ints[i] = Integer.parseInt(strings[i]);
        }
        int k = sc.nextInt();
        System.out.println(getTopK(ints, k));

    }

    public static void main3(String[] args){
        Scanner sc=new Scanner(System.in);
        ArrayList<string> a=new ArrayList<string>();
        while(sc.hasNext()){
            int aa=sc.nextInt();
            int bb=sc.nextInt();
        }
    }
    public static void main4(){
        // 1. 实例化 Scanner
        Scanner sc = new Scanner(System.in);
        // 2. 读取第一行--方法一
        String str1 = sc.next(); // next 已空格为结束标记
        int str2 = sc.nextInt(); // 读取第二个字符
        String str3 = sc.nextLine(); // 读取第一行的回车转到第二行
        // 2.1 读取第二行--方法二
        String str4 = sc.nextLine(); // 读取了这一行的所有数据
        string[] arrS = str4.split(" "); // 将读取数据分割为字符串数组
        // 3. 循环读取后面的行数
        while(sc.hasNextLine()){   // 以行数为结束标记
            int n = sc.nextInt();
        }

        while(sc.hasNextInt()){    // 以行和空格为结束标记
            int m = sc.nextInt();
        }                          // 输入非 int 类型结束输入
        
        // 4. 向数组中插入数据
        int m =0;
        int[] arr = new int[10];
        while(sc.hasNextInt()){
            arr[m] = sc.nextInt();
        }
        sc.close();
    }

}
