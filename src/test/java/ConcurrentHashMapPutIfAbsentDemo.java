import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapPutIfAbsentDemo {
    static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            // 1. 如果 count 不存在，放入 1
            Integer oldValue = map.putIfAbsent("count", 1);

            if (oldValue == null) {
                System.out.println(Thread.currentThread().getName() + " 初始化成功");
            } else {
                System.out.println(Thread.currentThread().getName() + " 初始化失败，已有值 = " + oldValue);
            }
        };

        Thread t1 = new Thread(task, "t1");
        Thread t2 = new Thread(task, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(map);
    }
}