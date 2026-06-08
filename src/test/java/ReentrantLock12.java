import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock12 {
    public static void main(String[] args) {
        AwaitSignal as = new AwaitSignal(5);
        Condition a = as.newCondition();
        Condition b = as.newCondition();
        Condition c = as.newCondition();
        new Thread(()->{
            as.print("a",a,b);
        }).start();
        new Thread(()->{
            as.print("b",b,c);
        }).start();
        new Thread(()->{
            as.print("c",c,a);
        }).start();
        as.lock();
        try {
            Thread.sleep(1000);
            System.out.println("开始");
            a.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            as.unlock();
        }
    }
}

class AwaitSignal extends ReentrantLock {
    private int loopNumber;

    public AwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }
    public void print(String str, Condition current,Condition next){
        for(int i=0;i<loopNumber;i++){
            lock();
            try {
                current.await();
                System.out.print(str);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                unlock();
            }
        }
    }
}