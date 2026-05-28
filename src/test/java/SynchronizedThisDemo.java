public class SynchronizedThisDemo {
    public static void main(String[] args) throws InterruptedException {

        Counter counter1 = new Counter();
        Counter counter2 = new Counter();
        Thread t1 = new Thread(()->{
            for(int i=0;i<1000000;i++) {
                counter1.add();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t2 = new Thread(()->{
            for(int i=0;i<1000000;i++) {
                counter2.minus();
            }
        });
        t1.start();

        t2.start();
        t1.join();
        t2.join();
        System.out.println(counter1.getCount());
        System.out.println(counter2.getCount());

    }

}


class Counter{

    int count = 0;



    public synchronized void add(){
        count++;
    }

    public synchronized void minus(){
        count--;
    }
    public int getCount(){
        return count;
    }

}
