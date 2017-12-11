import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by pc on 2017/12/10.
 */
class File {
    int value;
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
}

class Writer implements Runnable {

    private final File file;

    public Writer(File file){
        this.file = file;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++){
            file.lock.writeLock().lock();
            file.value = new Random().nextInt();
            System.out.println("Writer write " + file.value);
            file.lock.writeLock().unlock();
            try {
                Thread.sleep(new Random().nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Reader implements Runnable {

    private final File file;

    public Reader(File file){
        this.file = file;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++){
            file.lock.readLock().lock();
            System.out.println("Reader read " + file.value);
            file.lock.readLock().unlock();
            try {
                Thread.sleep(new Random().nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ReadWriteLockDemo {

    public static void main(String[] args) {
        File file = new File();
        new Thread(new Writer(file)).start();
        new Thread(new Reader(file)).start();
        new Thread(new Reader(file)).start();
        new Thread(new Reader(file)).start();
        new Thread(new Reader(file)).start();
        new Thread(new Reader(file)).start();
    }
}
