public class interrupt {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTerminal terminal = new TwoPhaseTerminal();
        terminal.start();
        Thread.sleep(3500);
        terminal.stop();
    }

}

class TwoPhaseTerminal {
    private Thread monitor;

    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (current.isInterrupted()) {
                    System.out.println("被打断了");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    System.out.println("监控");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    current.interrupt();
                }
            }
        },"monitor");
        monitor.start();
    }


     public void stop() {
        monitor.interrupt();
    }
}