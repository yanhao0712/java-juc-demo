import java.util.concurrent.locks.ReentrantLock;

public class TryLockDemo {
    static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("t1 获取锁，开始执行");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            // 1. 尝试获取锁，不等待
            boolean success = lock.tryLock();

            if (!success) {
                System.out.println("t2 获取锁失败，直接返回");
                return;
            }

            try {
                System.out.println("t2 获取锁成功，开始执行");
            } finally {
                lock.unlock();
            }
        }, "t2");

        t1.start();

        Thread.sleep(500);

        t2.start();
    }
}