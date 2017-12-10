//: concurrency/Restaurant.java
// The producer-consumer approach to task cooperation.
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Meal {
    private final int orderNum;
    public Meal(int orderNum) { this.orderNum = orderNum; }
    public String toString() { return "Meal " + orderNum; }
}

class WaitPerson implements Runnable {
    private Restaurant restaurant;
    Condition condition;
    Lock lock = new ReentrantLock();
    public WaitPerson(Restaurant r) {
        restaurant = r;
        condition = lock.newCondition();
    }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                this.lock.lock();
                while(restaurant.meal == null)
                    this.condition.await(); // ... for the chef to produce a meal
                this.lock.unlock();
                System.out.println("Waitperson got " + restaurant.meal);
                restaurant.busBoy.lock.lock();
                restaurant.busBoy.condition.signalAll();
                restaurant.busBoy.lock.unlock();
                this.lock.lock();
                while(!restaurant.isCleaned)
                    this.condition.await(); // ... for the chef to produce a meal
                this.lock.unlock();
                restaurant.chef.lock.lock();
                restaurant.meal = null;
                restaurant.chef.condition.signalAll(); // Ready for another
                restaurant.chef.lock.unlock();
            }
        } catch(InterruptedException e) {
            System.out.println("WaitPerson interrupted");
        }
    }
}

class BusBoy implements Runnable {
    private Restaurant restaurant;
    Condition condition;
    Lock lock = new ReentrantLock();
    public BusBoy(Restaurant r) {
        restaurant = r;
        condition = lock.newCondition();
    }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                this.lock.lock();
                while(restaurant.isCleaned)
                    this.condition.await(); // ... for the chef to produce a meal
                this.lock.unlock();
                System.out.println("BusBoy clean " + restaurant.meal);
                restaurant.isCleaned = true;
                restaurant.waitPerson.lock.lock();
                restaurant.waitPerson.condition.signalAll(); // Ready for another
                restaurant.waitPerson.lock.unlock();
            }
        } catch(InterruptedException e) {
            System.out.println("BusBoy interrupted");
        }
    }
}

class Chef implements Runnable {
    private Restaurant restaurant;
    Condition condition;
    Lock lock = new ReentrantLock();
    private int count = 0;
    public Chef(Restaurant r) {
        restaurant = r;
        condition = lock.newCondition();
    }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                this.lock.lock();
                while(restaurant.meal != null)
                    this.condition.await(); // ... for the meal to be taken
                this.lock.unlock();
                if(++count == 10) {
                    System.out.println("Out of food, closing");
                    restaurant.exec.shutdownNow();
                    return;
                }
                System.out.println("Order up! ");
                restaurant.waitPerson.lock.lock();
                restaurant.meal = new Meal(count);
                restaurant.isCleaned = false;
                restaurant.waitPerson.condition.signalAll();
                restaurant.waitPerson.lock.unlock();
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch(InterruptedException e) {
            System.out.println("Chef interrupted");
        }
    }
}

public class Restaurant {
    Meal meal;
    boolean isCleaned = true;
    final Lock lock = new ReentrantLock();
    ExecutorService exec = Executors.newCachedThreadPool();
    final WaitPerson waitPerson = new WaitPerson(this);
    final Chef chef = new Chef(this);
    final BusBoy busBoy = new BusBoy(this);

    public Restaurant() {
        exec.execute(chef);
        exec.execute(waitPerson);
        exec.execute(busBoy);
    }
    public static void main(String[] args) {
        new Restaurant();
    }
}