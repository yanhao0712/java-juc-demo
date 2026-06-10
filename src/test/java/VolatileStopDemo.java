public class VolatileStopDemo {
    // TODO：定义 volatile 标志位
    static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (running) {
                // 模拟工作
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("线程停止");
        }, "worker");

        t.start();

        Thread.sleep(2000);

        // TODO：修改标志位，让线程停止
        running = false;
    }
}