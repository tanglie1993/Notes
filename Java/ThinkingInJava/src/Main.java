import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    static class YieldThread extends Thread {

        final int name;

        public YieldThread(int name){
            this.name = name;
        }

        @Override
        public void run() {
            for(int i = 0; i < 3; i++){
                System.out.println(""+ name + ": " + i);
                Thread.yield();
            }
        }
    }
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        System.out.println("-------newFixedThreadPool----------");
        for(int i = 0; i < 10; i++){
            pool.submit(new YieldThread(i));
        }
        pool = Executors.newSingleThreadExecutor();
        System.out.println("-------newSingleThreadExecutor----------");
        for(int i = 0; i < 10; i++){
            pool.submit(new YieldThread(i));
        }
        pool = Executors.newCachedThreadPool();
        System.out.println("-------newCachedThreadPool----------");
        for(int i = 0; i < 10; i++){
            pool.submit(new YieldThread(i));
        }
    }
}
