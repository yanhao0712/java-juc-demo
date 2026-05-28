import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.LockSupport;

public class Test3232 {
    public static void main(String[] args) throws  InterruptedException {
        Park12();
    }


    private static void Park12() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("parking");
            LockSupport.park();
            Thread.interrupted();
            System.out.println("parked");
            System.out.println(Thread.currentThread().getName());
            LockSupport.park();
            System.out.println("parked");
        }, "t1");
        t1.start();


        Thread.sleep(1000);
        t1.interrupt();
    }
}
