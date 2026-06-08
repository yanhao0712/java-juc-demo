public class WaitNotify1 {
    public static void main(String[] args) throws InterruptedException {
        Flag f = new Flag(1,5);

        new Thread(() -> {
            f.print(2,1,"a");
        },"a").start();
        new Thread(() -> {
            f.print(3,2,"b");
        },"b").start();
        new Thread(() -> {
            f.print(1,3,"c");
        },"c").start();

    }
}

class Flag{
    private int Flag;    //现在的标记
    private int LoopNumber;

    public Flag(int flag, int loopNumber){
        this.Flag = flag;
        this.LoopNumber = loopNumber;
    }

    public void print(int nextFlag, int WaitFlag,String str){
        synchronized (this) {
            for (int i = 0; i < LoopNumber; i++) {
                while (Flag != WaitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                System.out.print(str);
                Flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}