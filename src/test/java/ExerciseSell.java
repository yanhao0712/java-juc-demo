import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ExerciseSell {
    public static void main(String[] args) throws InterruptedException {
        TicketWindow window = new TicketWindow(10000);
        List<Integer> amountList =   Collections.synchronizedList(new ArrayList<>());
        List<Thread> threadList = new ArrayList<>();
        for (int i = 1; i < 2000; i++) {
            Thread thread = new Thread(()->{
            int amount = window.sell(RandomAmount());
            amountList.add(amount);
            },"t1");
            threadList.add(thread);
            thread.start();
        }
        for(Thread thread:threadList){
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(window.getCount());
        System.out.println(amountList.stream().mapToInt(i->i).sum());

    }


    static Random random = new Random();

    public static int RandomAmount() {
        return random.nextInt(5) + 1;
    }


}


class TicketWindow{
    private int count;

    public TicketWindow(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }

    public synchronized int  sell(int count){
        if(this.count>=count){
            this.count-=count;
            return count;
        }else {
            return 0;
        }
    }
}





