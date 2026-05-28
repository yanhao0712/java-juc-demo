import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Runnable1 {
    public static void main(String[] args) {
        Runnable r =()->{
            System.out.println(Thread.currentThread().getName());


        };
        Thread t = new Thread(r,"t2");
        t.start();
    }
}








