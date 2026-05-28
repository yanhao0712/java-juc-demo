public class SleepNotReleaseLockDemo {
    static final Object  lock = new Object();
    public static void main(String[] args) throws InterruptedException {
          Thread t1 =    new Thread(()->{
            synchronized(lock){
                System.out.println("t1得到锁，开始睡觉" );
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("苏醒");
            }
        });
          Thread t2 =    new Thread(()->{
              synchronized(lock){
                  System.out.println("t2拿到锁");
              }
          });
          t1.start();
          t2.start();
          t1.join();
          t2.join();


    }
}
