import java.util.concurrent.locks.LockSupport;

public class Park1 {
    static Thread a;
    static Thread b;
    static Thread c;
    public static void main(String[] args) {
        ParkUnPark pun = new ParkUnPark(5);
        a = new Thread(()->{
            pun.print("a",b);
        });
        b = new Thread(()->{
            pun.print("b",c);
        });
        c = new Thread(()->{
            pun.print("c",a);
        });
        a.start();
        b.start();
        c.start();
        LockSupport.unpark(a);
    }
}


class ParkUnPark{
   private int LoopNumber;

    public ParkUnPark(int loopNumber) {
        LoopNumber = loopNumber;
    }

    public void print(String str, Thread nextThread){
       for(int i=0;i<LoopNumber;i++){
           LockSupport.park();
           System.out.print(str);
           LockSupport.unpark(nextThread);
       }
   }

}