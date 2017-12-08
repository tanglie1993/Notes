/**
 * Created by pc on 2017/12/8.
 */
public class CounterDemo {

    static class Counter{
        private int count;

        synchronized void increment(){
            count++;
        }

        synchronized int getCount() {
            return count;
        }
    }

    public static void main(String[] args){
        Counter counter = new Counter();
        new Thread(() -> {
            for(int i = 0; i < 10000; i++){
                counter.increment();
            }
            System.out.println(""+counter.getCount());
        }).start();
        new Thread(() -> {
            for(int i = 0; i < 10000; i++){
                counter.increment();
            }
            System.out.println(""+counter.getCount());
        }).start();
        new Thread(() -> {
            for(int i = 0; i < 10000; i++){
                counter.increment();
            }
            System.out.println(""+counter.getCount());
        }).start();
    }

}
