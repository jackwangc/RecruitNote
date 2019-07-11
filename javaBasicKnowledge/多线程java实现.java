import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.interrupted;

public class Main {
    public static void main(String[] args) {
        // 1. 创建线程的三种方式
        // 继承 thread 类
        // 实现 Runnable 接口，实现 callable 接口
        // 尽量选择 runnable , java 不支持多继承，继承整个类开销过大
        // 线程的几种状态 1. 新建 2. 可运行 3. 阻塞 4. 无限期等待 5. 限期等待 6. 死亡
        A aa = new A("jack");
        // 2. 基础线程机制
        ExecutorService executorService = Executors.newCachedThreadPool(); // CachedThreadPool：一个任务创建一个线程；FixedThreadPool：所有任务只能使用固定大小的线程
        Thread aat = new Thread(aa);
        executorService.execute(aat);
        A bb = new A("rose");
        Thread bbt = new Thread(bb);
        // 守护线程，守护线程是程序运行时在后台提供服务的线程，main() 属于非守护线程。
        // 所有非守护线程结束时，程序也就终止，同时会杀死所有守护线程
        bbt.setDaemon(true); // 设置守护线程 必须在线程运行开始前
        bbt.start();
        // 线程中断操作
        bbt.interrupt(); // 会设置线程中断标记，返回 true ,结束线程中的无限循环

        executorService.execute(() ->{
            // 匿名内部线程
            try {
                Thread.sleep(100);
                System.out.println("th run");
            } catch (InterruptedException e) {
                // 抛出异常，提前结束线程，但不能中断 I/O 阻塞和 synchronized 锁阻塞
                e.printStackTrace();
            }
        });

        System.out.println("th en");

        // 互斥同步，
        // Java 提供了两种锁机制来控制多个线程对共享资源的互斥访问，第一个是 JVM 实现的 synchronized，而另一个是 JDK 实现的 ReentrantLock。
        SyncTest test = new SyncTest();
        // synchronized 当一个线程进入执行时，另外一个线程必须等待当前线程执行完毕
        executorService.execute(() -> test.func());
        executorService.execute(() -> test.func());

        // executor 结束线程
        executorService.shutdownNow();
    }
    public static class A implements Runnable{
        String a;
        public A(String a){
            this.a = a;
        }
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    // 会休眠当前正在执行的线程
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.a+":"+i);
                // 声明了当前线程已经完成了生命周期中最重要的部分，可以切换给其它线程来执行
                Thread.yield();
            }
            // run 方法执行无限循环
//            while (!interrupted()){
//
//            }
        }
    }
    public static class SyncTest{
        // synchronized
        // 同步的概念：当一个线程进入同步语句块时，另一个线程就必须等待
        public void func(){
            // 1. 同步一个代码块，作用于同一个对象
            synchronized (this){
                for (int i = 0; i < 10; i++) {
                    System.out.println(i+"sync");
                }
            }
        }
        // 2. 同步一个方法,作用于同一个对象
        public synchronized void func2(){

        }
        public void func3(){
            // 3. 同步一个类，也就是说两个线程调用同一个类的不同对象上的这种同步语句，也会进行同步
            synchronized (SyncTest.class){

            }
        }

        // 4. 同步一个静态方法,作用于整个类
        public synchronized static void func4(){

        }

        // ReentrantLock
        // 1. 实例化一个锁
        private Lock lock = new ReentrantLock();
        // 2. 运用锁
        public void func5(){
            lock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.print(i+" ");
                }
            } finally {
                lock.unlock(); // 释放锁，避免发生死锁
            }
        }

        // 比较
//        1. 锁的实现
//        synchronized 是 JVM 实现的，而 ReentrantLock 是 JDK 实现的。
//        2. 性能
//        新版本 Java 对 synchronized 进行了很多优化，例如自旋锁等，synchronized 与 ReentrantLock 大致相同。
//        3. 等待可中断
//        当持有锁的线程长期不释放锁的时候，正在等待的线程可以选择放弃等待，改为处理其他事情。
//        ReentrantLock 可中断，而 synchronized 不行。
//        4. 公平锁
//        公平锁是指多个线程在等待同一个锁时，必须按照申请锁的时间顺序来依次获得锁。
//        synchronized 中的锁是非公平的，ReentrantLock 默认情况下也是非公平的，但是也可以是公平的。
//        5. 锁绑定多个条件
//        一个 ReentrantLock 可以同时绑定多个 Condition 对象。
//
//        使用选择
//        除非需要使用 ReentrantLock 的高级功能，否则优先使用 synchronized。
//        这是因为 synchronized 是 JVM 实现的一种锁机制，JVM 原生地支持它，
//        而 ReentrantLock 不是所有的 JDK 版本都支持。并且使用 synchronized 不用担心没有释放锁而导致死锁问题，因为 JVM 会确保锁的释放

    }
    // 线程之间的协作
    public class JoinExample {
        // 1. 继承 thread 创建线程，直接实例化为线程，调用 thread 的 join()方法
        private class test1 extends Thread {
            @Override
            public void run() {
                System.out.printf("a");
            }
        }
        private class test2 extends Thread {
            private test1 a;
            test2(test1 a){
                this.a = a;
            }

            @Override
            public void run() {
                try {
                    // 将另外一个线程加入到当前线程，当前线程挂起，直到目标线程结束
                    a.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // 2. wait(),notify(),notify() 它们都属于 Object 的一部分，而不属于 Thread
        // wait() 和 sleep() 的区别
        // wait() 是 Object 的方法，而 sleep() 是 Thread 的静态方法；
        // wait() 会释放锁，sleep() 不会。
        public synchronized void before(){
            System.out.printf("before");
            // 唤醒挂起的线程
            notifyAll();
        }
        public synchronized void after(){
            try {
                // 挂起当前线程，执行下一个线程
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("after");
        }

        // 3. java.utl.concurrent 类库
        // Condition 上调用 await() 方法使线程等待，其它线程调用 signal() 或 signalAll() 方法唤醒等待的线程
        private Lock lock = new ReentrantLock(); // lock 获取 condition
        private Condition condition = lock.newCondition(); // condition 类实现线程之间的协调

        public void before2(){
            lock.lock();
            try {
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
        public void after2(){
            lock.lock();
            try {
                // await 可以指定等待的条件
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
    // J.U.C -AQS
    public void Aqs(){
        // java.util.concurrent（J.U.C）大大提高了并发性能，AQS 被认为是 J.U.C 的核心。
        // 1. CountDownLatch
        // 用来控制一个线程等待多个线程
        final int totalThread = 10;
        CountDownLatch countDownLatch = new CountDownLatch(totalThread); // 维护了一个计数器 cnt，
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < totalThread; i++) {
            executorService.execute(() -> {
                System.out.print("run..");
                countDownLatch.countDown(); //每次调用 countDown() 方法会让计数器的值减 1
            });
        }
        try {
            countDownLatch.await(); // 减到 0 的时候，那些因为调用 await() 方法而在等待的线程就会被唤醒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
        executorService.shutdown();
        // 2. CyclicBarrier
        // 用来控制多个线程互相等待，只有当多个线程都到达时，这些线程才会继续执行
        final int totalThread2 = 10;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(totalThread2);
        try {
            cyclicBarrier.await(); // 程执行 await() 方法之后计数器会减 1，并进行等待，直到计数器为 0，所有调用 await() 方法而在等待的线程才能继续执行。
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        // 3. Semaphore
        // Semaphore 类似于操作系统中的信号量，可以控制对互斥资源的访问线程数。
        final int clientCount = 3; // 单次访问进程数
        final int totalRequestCount = 10; // 访问总数
        Semaphore semaphore = new Semaphore(clientCount);

        // 其他组件
        // FutureTask 可用于异步获取执行结果或取消执行任务的场景。当一个计算任务需要执行很长时间，
        // 那么就可以用 FutureTask 来封装这个任务，主线程在完成自己的任务之后再去获取结果
        // BlockingQueue 提供了阻塞的 take() 和 put() 方法：如果队列为空 take() 将阻塞，直到队列中有内容；如果队列为满 put() 将阻塞，直到队列有空闲位置
        // ForkJoin 主要用于并行计算中，和 MapReduce 原理类似，都是把大的计算任务拆分成多个小任务并行计算

    }
}





