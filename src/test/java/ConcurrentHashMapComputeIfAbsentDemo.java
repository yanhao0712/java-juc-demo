import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapComputeIfAbsentDemo {
    static ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            String value = cache.computeIfAbsent("user:1", key -> {
                System.out.println(Thread.currentThread().getName() + " 正在创建 value");
                return "张三";
            });

            System.out.println(Thread.currentThread().getName() + " 获取到：" + value);
        };

        Thread t1 = new Thread(task, "t1");
        Thread t2 = new Thread(task, "t2");
        Thread t3 = new Thread(task, "t3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println(cache);
    }
}