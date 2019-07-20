/**
 * 参考链接 [https://mp.weixin.qq.com/s/UD5S-MTF4ic6NZmW4ZZaxQ]
 * udp编程
 * 为UDP是无连接的不可靠传输，所以接收方需要在发送方发送数据之前就启动，
 * 否则会接收不到数据，也就是说必须先运行UDPSocketReceive再运行UDPSocketSend。
* 发送方UDP
*/
public class UDPSocketSend {
   public static void main(String[] args) throws IOException {

       System.out.println("Sender Start...");

       //1.创建socket服务
       DatagramSocket ds = new DatagramSocket();

       //2.封装数据
       String str = "Did you recite words today";
       byte[] bytes = str.getBytes();
       //地址
       InetAddress address =InetAddress.getByName("192.168.31.137");
       //参数：数据、长度、地址、端口
       DatagramPacket dp = new DatagramPacket(bytes,bytes.length,address,6666);

       //3.发送数据包
       ds.send(dp);

       //4.关闭socket服务
       ds.close();
   }
}
/**
* 接收方UDP
*/
public class UDPSocketReceive{
    public static void main(String[] args) throws IOException {
 
        System.out.println("Receiver Start...");
 
        //1.创建udp的socket服务,并声明端口号
        DatagramSocket ds = new DatagramSocket(6666);
 
        //2.创建接收数据的数据包
        byte[] bytes = new byte[1024];
        DatagramPacket dp = new DatagramPacket(bytes,bytes.length);
 
        //3.将数据接收到数据包中，为阻塞式方法
        ds.receive(dp);
 
        //4.解析数据
        InetAddress address = dp.getAddress();//发送方IP
        int port = dp.getPort();//发送方端口
        String content = new String(dp.getData(),0,dp.getLength());
        System.out.println("address:"+address+"---port:"+port+"---content:"+content);
 
        //关闭服务
        ds.close();
    }
 }
/**
 * tcp 编程
 * TCP基于client-server是面向连接的可靠传输，上个人对TCP协议进行封装显然大多数开发者是很难实现的，
 * 所以Java也为开发者提供了基于TCP的Socket，不
 * 同于UDP，TCP Socket分为Socket和ServerSocket对应着client和server
 */
 //客户端
public class TCPClient {
   public static void main(String[] args) throws IOException {

       //1.创建TCP客户端Socket服务
       Socket client = new Socket();
       //2.与服务端进行连接
       InetSocketAddress address = new InetSocketAddress("192.168.31.137",10000);
       client.connect(address);
       //3.连接成功后获取客户端Socket输出流
       OutputStream outputStream = client.getOutputStream();
       //4.通过输出流往服务端写入数据
       outputStream.write("hello server".getBytes());
       //5.关闭流
       client.close();
   }
}

// 服务端
public class TCPServer {
    public static void main(String[] args) throws IOException {
        //1.创建服务端Socket并明确端口号
        ServerSocket serverSocket = new ServerSocket(10000);
        //2.获取到客户端的Socket
        Socket socket = serverSocket.accept();
        //3.通过客户端的Socket获取到输入流
        InputStream inputStream = socket.getInputStream();
        //4.通过输入流获取到客户端传递的数据
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while ((line = bufferedReader.readLine())!=null){
            System.out.println(line);
        }
 
        //5.关闭流
        socket.close();
        serverSocket.close();
    }
}