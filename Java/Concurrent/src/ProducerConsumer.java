import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pc on 2017/12/10.
 */
class Storage {

    private List<Object> storage;

    public Storage(){
        storage = new LinkedList<>();
    }

    public boolean isFull() {
        return storage.size() >= 10;
    }

    public void add(Object o) {
        storage.add(o);
        System.out.println(""+storage.size());
    }

    public boolean isEmpty() {
        return storage.size() == 0;
    }

    public void remove() {
        storage.remove(0);
        System.out.println(""+storage.size());
    }
}

class Producer implements Runnable {

    private final Storage storage;

    public Producer(Storage storage){
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            for(int i = 0; i < 100; i++){
                System.out.println("Producer " + i);
                synchronized (storage){
                    while(storage.isFull()) {
                        storage.wait();
                    }
                }
                synchronized (storage){
                    while (!storage.isFull()){
                        storage.add(new Object());
                    }
                    storage.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Producer InterruptedException " + e);
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {

    private final Storage storage;

    public Consumer(Storage storage){
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            for(int i = 0; i < 100; i++){
                System.out.println("Consumer " + i);
                synchronized (storage){
                    while(storage.isEmpty()) {
                        storage.wait();
                    }
                }
                synchronized (storage){
                    while (!storage.isEmpty()){
                        storage.remove();
                    }
                    storage.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Consumer InterruptedException " + e);
            e.printStackTrace();
        }
    }
}

public class ProducerConsumer {

    public static void main(String[] args) {
        Storage storage = new Storage();
        new Thread(new Producer(storage)).start();
        new Thread(new Consumer(storage)).start();
    }
}
