public class TransferDemo {
    public static void main(String[] args) {
        Account a = new Account(10000);
        Account b = new Account(10000);

            Thread t1 = new Thread(()->{
                for (int i = 1; i < 5000; i++) {
                    a.transfer(b, 1);
                }
            });
            Thread t2 = new Thread(()->{
                for (int i = 1; i < 5000; i++) {
                    b.transfer(a, 1);
                }
            });
            t1.start();

            t2.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(a.getMoney());
        System.out.println(b.getMoney());
        System.out.println(a.getMoney()+b.getMoney());
    }

    }



class Account {
    private int money;
    public Account(int money) {
        this.money = money;
    }
    public int getMoney() {
        return money;
    }
    public void transfer(Account target,int amount){
        synchronized (Account.class) {
            money -= amount;
            target.money += amount;
        }
    }
}
