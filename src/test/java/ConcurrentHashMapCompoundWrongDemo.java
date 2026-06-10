import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapCompoundWrongDemo {
    static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            // 1. 判断 key 是否不存在
            if (!map.containsKey("count")) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 2. 放入数据
                map.put("count", 1);

                System.out.println(Thread.currentThread().getName() + " 执行了初始化");
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