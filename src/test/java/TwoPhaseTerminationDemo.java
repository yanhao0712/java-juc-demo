public class TwoPhaseTerminationDemo {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt = new TwoPhaseTermination();

        tpt.start();

        Thread.sleep(3000);

        tpt.stop();
    }
}

class TwoPhaseTermination {
    private Thread monitor;

    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();

                if (current.isInterrupted()) {
                    System.out.println("料理后事");
                    break;
                }

                try {
                    System.out.println("执行监控记录");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // sleep 被打断后，中断标记会被清除
                    // 所以这里要重新设置中断标记
                    current.interrupt();
                }
            }
        }, "monitor");

        monitor.start();
    }

    public void stop() {
        monitor.interrupt();
    }
}