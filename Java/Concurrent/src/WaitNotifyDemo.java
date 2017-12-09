import java.util.Objects;

/**
 * Created by pc on 2017/12/9.
 */
public class WaitNotifyDemo {

    public static void main(String args[]) {
        final Object lock = new Object();
        Thread thread = new Thread() {
            @Override
            public void run() {
                synchronized(lock) {
                    try {
                        lock.wait(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("111");
                }

            }
        };
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock){
            lock.notify();
        }
    }
}
