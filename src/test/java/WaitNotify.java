public class WaitNotify {
    static Object lock = new Object();
    static boolean flag = false;
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized(lock) {
                while(!flag) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("1");
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized(lock) {
                System.out.println("2");
                flag = true;
                lock.notify();
            }

        }, "t2");
        t1.start();
        t2.start();
    }
}

