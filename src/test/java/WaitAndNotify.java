public class WaitAndNotify {
    static boolean hasCigarette = false;

    static  boolean hasTakeout = false;

    public static void main(String[] args) throws InterruptedException {
        Object object1 = new Object();

            Thread t1 = new Thread(() -> {
                synchronized (object1) {
                    while(!hasCigarette){
                        try {
                            System.out.println("no1");
                            object1.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("yes1");
                }
            });
            Thread t2 = new Thread(() -> {
                synchronized (object1) {
                    while(!hasTakeout){
                        System.out.println("no2");
                        try {
                            object1.wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    System.out.println("yes2");
                }
            });
            t1.start();
            t2.start();
            Thread.sleep(2000);
            Thread t3 = new Thread(() -> {
                synchronized (object1)
                {
                    hasTakeout = true;
                    object1.notifyAll();
                }
            });
            t3.start();
        }
    }

