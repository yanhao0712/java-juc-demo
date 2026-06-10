import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCountDemo {
    static int count = 0;

    // 1. 创建 ReentrantLock
    static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                add();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                add();
            }
        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("count = " + count);
    }

    private static void add() {
        // 2. 加锁
        lock.lock();

        try {
            count++;
        } finally {
            // 3. 解锁
            lock.unlock();
        }
    }
}