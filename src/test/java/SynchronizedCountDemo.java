public class SynchronizedCountDemo {
    static int count = 0;

    // 1. 定义锁对象
    static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                // TODO：加锁保护 count++
                synchronized(SynchronizedCountDemo.lock) {
                    count++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                // TODO：加锁保护 count++
                synchronized(SynchronizedCountDemo.lock) {
                    count++;
                }
            }
        }, "t2");

        t1.start();
        t2.start();

        // TODO：等待两个线程结束
        t1.join();
        t2.join();
        System.out.println(count);
    }
}