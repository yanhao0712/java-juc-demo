import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Test12{
    public static void main(String[] args) {
        FutureTask<String> ft = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(5000);
                System.out.println("end");
                return Thread.currentThread().getName();

            }

        });
        Thread t = new Thread(ft);
        t.start();
    }
}
