import java.util.concurrent.locks.LockSupport;

public class ParkUnparkOrderDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            // TODO：先暂停
            LockSupport.park();

            System.out.println("1");
        }, "t1");

        Thread t2 = new Thread(() -> {
            LockSupport.park();
            System.out.println("2");

            // TODO：唤醒 t1
            LockSupport.unpark(t1);
        }, "t2");
        Thread t3 = new Thread(() -> {
            System.out.println("3");
            LockSupport.unpark(t2);
        },"t3");

        t2.start();
        t1.start();
        t3.start();
    }
}