import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.*;

/**
 * Created by pc on 2017/12/5.
 */
public class CallableDemo {

    static class MyCallable implements Callable<String> {

        private int input;

        public MyCallable(int input){
            this.input = input;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            return ""+ input + " " + System.currentTimeMillis();
        }
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        List<Future<String>> futures = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            futures.add(pool.submit(new MyCallable(i)));
        }
        for(int i = 0; i < 10; i++){
            try {
                System.out.println(futures.get(i).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
