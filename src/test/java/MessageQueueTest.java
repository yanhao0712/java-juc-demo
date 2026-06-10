import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

class MessageQueueTest {

    @Test
    void putThenTakeReturnsMessagesInFifoOrder() {
        MessageQueue queue = new MessageQueue(2);
        Message first = new Message(1, "first");
        Message second = new Message(2, "second");

        queue.put(first);
        queue.put(second);

        assertSame(first, queue.take());
        assertSame(second, queue.take());
    }

    @Test
    void takeBlocksUntilMessageIsAvailable() throws Exception {
        MessageQueue queue = new MessageQueue(1);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Message message = new Message(1, "hello");

        try {
            Future<Message> taken = executor.submit(queue::take);

            TimeUnit.MILLISECONDS.sleep(100);
            assertFalse(taken.isDone());

            queue.put(message);

            assertSame(message, taken.get(1, TimeUnit.SECONDS));
        } finally {
            executor.shutdownNow();
        }
    }

    @Test
    void putBlocksWhenQueueIsFullUntilMessageIsTaken() throws Exception {
        MessageQueue queue = new MessageQueue(1);
        Message first = new Message(1, "first");
        Message second = new Message(2, "second");
        ExecutorService executor = Executors.newSingleThreadExecutor();

        queue.put(first);

        try {
            Future<?> putting = executor.submit(() -> queue.put(second));

            TimeUnit.MILLISECONDS.sleep(100);
            assertFalse(putting.isDone());
            assertSame(first, queue.take());

            putting.get(1, TimeUnit.SECONDS);
            assertSame(second, queue.take());
        } finally {
            executor.shutdownNow();
        }
    }

    @Test
    void supportsMultipleProducersAndConsumers() {
        assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {
            MessageQueue queue = new MessageQueue(3);
            ExecutorService executor = Executors.newFixedThreadPool(4);
            CountDownLatch start = new CountDownLatch(1);
            List<Future<?>> tasks = new ArrayList<>();
            List<Message> consumed = new ArrayList<>();
            Object lock = new Object();

            try {
                for (int i = 0; i < 2; i++) {
                    int producerId = i;
                    tasks.add(executor.submit(() -> {
                        start.await();
                        for (int j = 0; j < 5; j++) {
                            queue.put(new Message(producerId * 10 + j, "msg-" + producerId + "-" + j));
                        }
                        return null;
                    }));
                }

                for (int i = 0; i < 2; i++) {
                    tasks.add(executor.submit(() -> {
                        start.await();
                        for (int j = 0; j < 5; j++) {
                            Message message = queue.take();
                            synchronized (lock) {
                                consumed.add(message);
                            }
                        }
                        return null;
                    }));
                }

                start.countDown();
                for (Future<?> task : tasks) {
                    task.get(2, TimeUnit.SECONDS);
                }

                assertEquals(10, consumed.size());
            } finally {
                executor.shutdownNow();
            }
        });
    }
}
