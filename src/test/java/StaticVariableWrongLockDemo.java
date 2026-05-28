public class StaticVariableWrongLockDemo {
    public static void main(String[] args) throws InterruptedException {
        CountStatic c1 = new CountStatic();
        CountStatic c2 = new CountStatic();
        Thread t1 = new Thread(()->{
            for(int i=0;i<10000;i++){
                c1.add();
            }
        });
        Thread t2 = new Thread(()->{
            for(int i=0;i<10000;i++){
                c2.add();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(CountStatic.get());
    }
}
class CountStatic{
    static int count=0;

    public synchronized static   void add(){

            count++;


    }
    public static int get(){
        return count;
    }
}
