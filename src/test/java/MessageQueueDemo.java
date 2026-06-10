import java.util.LinkedList;

public class MessageQueueDemo {
    static class MessageQueue {
        private final LinkedList<String> list = new LinkedList<>();
        private final int capacity;

        public MessageQueue(int capacity) {
            this.capacity = capacity;
        }

        public String take() {
            synchronized (list) {
                while (list.isEmpty()) {
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                String message = list.removeFirst();

                list.notifyAll();

                return message;
            }
        }

        public void put(String message) {
            synchronized (list) {
                while (list.size() == capacity) {
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                list.addLast(message);

                list.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(2);

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                queue.put("消息-" + i);
                System.out.println("生产：" + "消息-" + i);
            }
        }, "producer");

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                String msg = queue.take();
                System.out.println("消费：" + msg);
            }
        }, "consumer");

        producer.start();
        consumer.start();
    }
}