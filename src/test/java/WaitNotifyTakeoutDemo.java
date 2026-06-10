public class WaitNotifyTakeoutDemo {
    static final Object room = new Object();

    // 共享变量：外卖是否到了
    static boolean hasTakeout = false;

    public static void main(String[] args) {
        Thread xiaonan = new Thread(() -> {
            synchronized (room) {
                // TODO：如果外卖没到，就等待
                while (!hasTakeout) {
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("外卖到了，开始吃饭");
            }
        }, "小南");

        Thread delivery = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (room) {
                // TODO：修改条件
                hasTakeout= true;

                // TODO：唤醒所有等待线程
                room.notifyAll();
            }
        }, "送外卖的");

        xiaonan.start();
        delivery.start();
    }
}