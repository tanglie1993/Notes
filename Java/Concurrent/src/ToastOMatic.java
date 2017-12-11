//: concurrency/ToastOMatic.java
// A toaster that uses queues.
import java.util.concurrent.*;
import java.util.*;

class Toast {
    private boolean isJammed;
    private boolean isButtered;
    private final int id;
    public Toast(int idn) { id = idn; }
    public void butter() { isButtered = true; }
    public void jam() { isJammed = true; }
    public int getId() { return id; }

    public boolean isJammed() {
        return isJammed;
    }

    public boolean isButtered() {
        return isButtered;
    }

    @Override
    public String toString() {
        return "Toast{" +
                "isJammed=" + isJammed +
                ", isButtered=" + isButtered +
                ", id=" + id +
                '}';
    }
}

class ToastQueue extends LinkedBlockingQueue<Toast> {}

class Toaster implements Runnable {
    private ToastQueue toastQueue;
    private int count = 0;
    private Random rand = new Random();
    public Toaster(ToastQueue tq) { toastQueue = tq; }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(
                        100 + rand.nextInt(500));
                // Make toast
                Toast t = new Toast(count++);
                System.out.println(t);
                // Insert into queue
                toastQueue.put(t);
            }
        } catch(InterruptedException e) {
            System.out.println("Toaster interrupted");
        }
        System.out.println("Toaster off");
    }
}

// Apply butter to toast:
class Butterer implements Runnable {
    private ToastQueue before, after;
    public Butterer(ToastQueue before, ToastQueue after) {
        this.before = before;
        this.after = after;
    }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                // Blocks until next piece of toast is available:
                Toast t = before.take();
                t.butter();
                System.out.println(t);
                after.put(t);
            }
        } catch(InterruptedException e) {
            System.out.println("Butterer interrupted");
        }
        System.out.println("Butterer off");
    }
}

// Apply jam to buttered toast:
class Jammer implements Runnable {
    private ToastQueue before, after;
    public Jammer(ToastQueue before, ToastQueue after) {
        this.before = before;
        this.after = after;
    }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                // Blocks until next piece of toast is available:
                Toast t = before.take();
                t.jam();
                System.out.println(t);
                after.put(t);
            }
        } catch(InterruptedException e) {
            System.out.println("Jammer interrupted");
        }
        System.out.println("Jammer off");
    }
}

class Dispatcher implements Runnable {
    private ToastQueue toDispatchQueue, toButterQueue, toJamQueue, finishedQueue;
    private Random random = new Random();
    public Dispatcher(ToastQueue toDispatchQueue, ToastQueue toButterQueue, ToastQueue toJamQueue, ToastQueue finishedQueue) {
        this.toDispatchQueue = toDispatchQueue;
        this.toButterQueue = toButterQueue;
        this.toJamQueue = toJamQueue;
        this.finishedQueue = finishedQueue;
    }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                Toast t = toDispatchQueue.take();
                if(!t.isButtered() && !t.isJammed()){
                    if(random.nextBoolean()){
                        toButterQueue.add(t);
                    }else{
                        toJamQueue.add(t);
                    }
                }else if(!t.isButtered()){
                    toButterQueue.add(t);
                }else if(!t.isJammed()){
                    toJamQueue.add(t);
                }else{
                    finishedQueue.add(t);
                }
            }
        } catch(InterruptedException e) {
            System.out.println("Dispatcher interrupted");
        }
        System.out.println("Dispatcher off");
    }
}

// Consume the toast:
class Eater implements Runnable {
    private ToastQueue finishedQueue;
    private int counter = 0;
    public Eater(ToastQueue finished) {
        finishedQueue = finished;
    }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                // Blocks until next piece of toast is available:
                Toast t = finishedQueue.take();
                // Verify that the toast is coming in order,
                // and that all pieces are getting jammed:
                System.out.println("Chomp! " + t);
            }
        } catch(InterruptedException e) {
            System.out.println("Eater interrupted");
        }
        System.out.println("Eater off");
    }
}

public class ToastOMatic {
    public static void main(String[] args) throws Exception {
        ToastQueue toDispatchQueue = new ToastQueue(),
                toButterQueue = new ToastQueue(),
                toJamQueue = new ToastQueue(),
                finishedQueue = new ToastQueue();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Toaster(toDispatchQueue));
        exec.execute(new Butterer(toButterQueue, toDispatchQueue));
        exec.execute(new Jammer(toJamQueue, toDispatchQueue));
        exec.execute(new Dispatcher(toDispatchQueue, toButterQueue, toJamQueue, finishedQueue));
        exec.execute(new Eater(finishedQueue));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
} /* (Execute to see output) *///:~
