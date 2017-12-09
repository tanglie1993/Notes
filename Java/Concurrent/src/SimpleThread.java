//: concurrency/SimpleThread.java
// Inheriting directly from the Thread class.

public class SimpleThread extends Thread {
    private int countDown = 5;
    private static int threadCount = 0;
    public SimpleThread() {
        // Store the thread name:
        super(Integer.toString(++threadCount));
        setDaemon(true);
        start();
    }
    public String toString() {
        return "#" + getName() + "(" + countDown + "), ";
    }
    public void run() {
        while(true) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(this);
            if(--countDown == 0)
                return;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < 5; i++)
            new SimpleThread();
        Thread.sleep(400);
        System.out.println("quit");
    }
}