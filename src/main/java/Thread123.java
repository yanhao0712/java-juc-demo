public class Thread123 {
    public static void main(String[] args) {
        Thread t = new Thread() {
            public void run() {
                System.out.println("123");
                try {
                    Thread.sleep(30);
                    System.out.println("1234");
                } catch (InterruptedException e) {
                    System.out.println(e);
                }

            }
        };
        System.out.println("main1");

        t.start();
        try{
            t.join(40);
            System.out.println("main2");
        }catch(InterruptedException e){
            System.out.println(e);
        }
    }
}
