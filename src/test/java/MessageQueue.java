

import java.util.LinkedList;

class MessageQueue {
    private LinkedList<Message> list = new LinkedList<>();
    private int capacity;
    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    public Message take() {
        synchronized (list) {
            while (list.isEmpty()) {
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Message message = list.removeFirst();
            list.notifyAll();
            return message;
        }

    }

    public void put(Message message) {
        synchronized (list) {
            while (list.size() >= capacity) {
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            list.addLast(message);
            list.notifyAll();
        }

    }
}


class Message{
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }
    public int getId() {
        return id;
    }
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}