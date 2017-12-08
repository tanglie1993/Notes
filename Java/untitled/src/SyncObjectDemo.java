import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by pc on 2017/12/8.
 */
public class SyncObjectDemo {

    static class SyncOnSameObject{
        static final Object lock = new Object();

        private void sync1(){
            synchronized (lock){
                for(int i = 0; i < 10; i++){
                    System.out.println("sync1 " + i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void sync2(){
            synchronized (lock){
                for(int i = 0; i < 10; i++){
                    System.out.println("sync2 " + i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void sync3(){
            synchronized (lock){
                for(int i = 0; i < 10; i++){
                    System.out.println("sync3 " + i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class SyncOnDifferentObject{
        static final Object lock1 = new Object();
        static final Object lock2 = new Object();
        static final Object lock3 = new Object();

        private void sync1(){
            synchronized (lock1){
                for(int i = 0; i < 10; i++){
                    System.out.println("SyncOnDifferentObject sync1 " + i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void sync2(){
            synchronized (lock2){
                for(int i = 0; i < 10; i++){
                    System.out.println("SyncOnDifferentObject sync2 " + i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void sync3(){
            synchronized (lock3){
                for(int i = 0; i < 10; i++){
                    System.out.println("SyncOnDifferentObject sync3 " + i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class SyncReentrantLock{
        static final ReentrantLock lock = new ReentrantLock();

        private void sync1(){
            lock.lock();
            for(int i = 0; i < 10; i++){
                System.out.println("SyncReentrantLock sync1 " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.unlock();
        }

        private void sync2(){
            lock.lock();
            for(int i = 0; i < 10; i++){
                System.out.println("SyncReentrantLock sync2 " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.unlock();
        }

        private void sync3(){
            lock.lock();
            for(int i = 0; i < 10; i++){
                System.out.println("SyncReentrantLock sync3 " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SyncOnSameObject syncOnSameObject = new SyncOnSameObject();
        new Thread(syncOnSameObject::sync1).start();
        new Thread(syncOnSameObject::sync2).start();
        new Thread(syncOnSameObject::sync3).start();
        SyncOnDifferentObject syncOnDifferentObject = new SyncOnDifferentObject();
        new Thread(syncOnDifferentObject::sync1).start();
        new Thread(syncOnDifferentObject::sync2).start();
        new Thread(syncOnDifferentObject::sync3).start();
        SyncReentrantLock syncReentrantLock = new SyncReentrantLock();
        new Thread(syncReentrantLock::sync1).start();
        new Thread(syncReentrantLock::sync2).start();
        new Thread(syncReentrantLock::sync3).start();
    }
}
