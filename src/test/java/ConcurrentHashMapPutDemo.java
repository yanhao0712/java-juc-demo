import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapPutDemo {
    static ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                map.put(i, "t1-" + i);
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 5000; i < 10000; i++) {
                map.put(i, "t2-" + i);
            }
        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("size = " + map.size());
    }
}