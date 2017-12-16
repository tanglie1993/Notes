//: concurrency/PipedIO.java
// Using pipes for inter-task I/O
import java.util.concurrent.*;
import java.io.*;
import java.util.*;

class Sender implements Runnable {
    private Random rand = new Random(47);
    private BlockingQueue<Character> queue = new LinkedBlockingQueue<>();

    public BlockingQueue<Character> getQueue() {
        return queue;
    }

    public void run() {
        try {
            while(true)
                for(char c = 'A'; c <= 'z'; c++) {
                    queue.put(c);
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                }
        } catch(InterruptedException e) {
            System.out.println(e + " Sender sleep interrupted");
        }
    }
}

class Receiver implements Runnable {
    private BlockingQueue<Character> queue;
    public Receiver(Sender sender) throws IOException {
        queue = sender.getQueue();
    }
    public void run() {
        try {
            while(true) {
                // Blocks until characters are there:
                System.out.println("Read: " + queue.take() + ", ");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class PipedIO {
    public static void main(String[] args) throws Exception {
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender);
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(sender);
        exec.execute(receiver);
        TimeUnit.SECONDS.sleep(4);
        exec.shutdownNow();
    }
} /* Output: (65% match)
Read: A, Read: B, Read: C, Read: D, Read: E, Read: F, Read: G, Read: H, Read: I, Read: J, Read: K, Read: L, Read: M, java.lang.InterruptedException: sleep interrupted Sender sleep interrupted
java.io.InterruptedIOException Receiver read exception
*///:~
