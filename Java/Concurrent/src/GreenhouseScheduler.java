import java.util.concurrent.*;
import java.util.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class GreenhouseScheduler {
    private volatile boolean light = false;
    private volatile boolean water = false;
    private String thermostat = "Day";
    public synchronized String getThermostat() {
        return thermostat;
    }
    public synchronized void setThermostat(String value) {
        thermostat = value;
    }

     DelayQueue<DelayedTask1> queue = new DelayQueue<>();
    private ExecutorService executor = Executors.newCachedThreadPool();

    class LightOn extends DelayedTask1 {

        public LightOn(int delayInMilliseconds) {
            super(delayInMilliseconds);
        }

        public void run() {
            // Put hardware control code here to
            // physically turn on the light.
            System.out.println("Turning on lights");
            light = true;
        }
    }
    class LightOff extends DelayedTask1 {
        public LightOff(int delayInMilliseconds) {
            super(delayInMilliseconds);
        }

        public void run() {
            // Put hardware control code here to
            // physically turn off the light.
            System.out.println("Turning off lights");
            light = false;
        }
    }
    class WaterOn extends DelayedTask1 {

        public WaterOn(int delayInMilliseconds) {
            super(delayInMilliseconds);
        }

        public void run() {
            // Put hardware control code here.
            System.out.println("Turning greenhouse water on");
            water = true;
        }
    }
    class WaterOff extends DelayedTask1 {

        public WaterOff(int delayInMilliseconds) {
            super(delayInMilliseconds);
        }

        public void run() {
            // Put hardware control code here.
            System.out.println("Turning greenhouse water off");
            water = false;
        }
    }
    class ThermostatNight extends DelayedTask1 {

        public ThermostatNight(int delayInMilliseconds) {
            super(delayInMilliseconds);
        }

        public void run() {
            // Put hardware control code here.
            System.out.println("Thermostat to night setting");
            setThermostat("Night");
        }
    }
    class ThermostatDay extends DelayedTask1 {

        public ThermostatDay(int delayInMilliseconds) {
            super(delayInMilliseconds);
        }

        public void run() {
            // Put hardware control code here.
            System.out.println("Thermostat to day setting");
            setThermostat("Day");
        }
    }
    class Bell extends DelayedTask1 {

        public Bell(int delayInMilliseconds) {
            super(delayInMilliseconds);
        }

        public void run() { System.out.println("Bing!"); }
    }
    class Terminate extends DelayedTask1 {
        public Terminate(int delayInMilliseconds) {
            super(delayInMilliseconds);
        }
        public void run() {
            System.out.println("Terminating");
            executor.shutdownNow();
            // Must start a separate task to do this job,
            // since the scheduler has been shut down:
            new Thread() {
                public void run() {
                    for(DataPoint d : data)
                        System.out.println(d);
                }
            }.start();
        }
    }
    // New feature: data collection
    static class DataPoint {
        final Calendar time;
        final float temperature;
        final float humidity;
        public DataPoint(Calendar d, float temp, float hum) {
            time = d;
            temperature = temp;
            humidity = hum;
        }
        public String toString() {
            return time.getTime() +
                    String.format(
                            " temperature: %1$.1f humidity: %2$.2f",
                            temperature, humidity);
        }
    }
    private Calendar lastTime = Calendar.getInstance();
    { // Adjust date to the half hour
        lastTime.set(Calendar.MINUTE, 30);
        lastTime.set(Calendar.SECOND, 00);
    }
    private float lastTemp = 65.0f;
    private int tempDirection = +1;
    private float lastHumidity = 50.0f;
    private int humidityDirection = +1;
    private Random rand = new Random(47);
    List<DataPoint> data = Collections.synchronizedList(
            new ArrayList<DataPoint>());
    class CollectData extends DelayedTask1 {

        public CollectData(int delayInMilliseconds) {
            super(delayInMilliseconds);
        }

        public void run() {
            System.out.println("Collecting data");
            synchronized(GreenhouseScheduler.this) {
                // Pretend the interval is longer than it is:
                lastTime.set(Calendar.MINUTE,
                        lastTime.get(Calendar.MINUTE) + 30);
                // One in 5 chances of reversing the direction:
                if(rand.nextInt(5) == 4)
                    tempDirection = -tempDirection;
                // Store previous value:
                lastTemp = lastTemp +
                        tempDirection * (1.0f + rand.nextFloat());
                if(rand.nextInt(5) == 4)
                    humidityDirection = -humidityDirection;
                lastHumidity = lastHumidity +
                        humidityDirection * rand.nextFloat();
                // Calendar must be cloned, otherwise all
                // DataPoints hold references to the same lastTime.
                // For a basic object like Calendar, clone() is OK.
                data.add(new DataPoint((Calendar)lastTime.clone(),
                        lastTemp, lastHumidity));
            }
        }
    }

    void add(DelayedTask1 delayed) {
        queue.add(delayed);
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        GreenhouseScheduler gh = new GreenhouseScheduler();
//        gh.schedule(gh.new Terminate(), 5000);

        for(int i = 0; i <= 5000; i += 1000){
            gh.add(gh.new Bell(i));
        }
        for(int i = 0; i <= 5000; i += 2000){
            gh.add(gh.new ThermostatNight(i));
        }
        for(int i = 0; i <= 5000; i += 200){
            gh.add(gh.new LightOn(i));
        }
        for(int i = 0; i <= 5000; i += 400){
            gh.add(gh.new LightOff(i));
        }
        for(int i = 0; i <= 5000; i += 600){
            gh.add(gh.new WaterOn(i));
        }
        for(int i = 0; i <= 5000; i += 800){
            gh.add(gh.new WaterOff(i));
        }
        for(int i = 0; i <= 5000; i += 1400){
            gh.add(gh.new ThermostatDay(i));
        }
        for(int i = 0; i <= 500; i += 500){
            gh.add(gh.new CollectData(i));
        }
        gh.add(gh.new Terminate(5000));
        exec.execute(new DelayedTaskConsumer1(gh.queue));
    }
} /* (Execute to see output) *///:~

class DelayedTask1 implements Runnable, Delayed {
    private static int counter = 0;
    private final int id = counter++;
    private final int delta;
    private final long trigger;
    protected static List<DelayedTask1> sequence =
            new ArrayList<>();
    public DelayedTask1(int delayInMilliseconds) {
        delta = delayInMilliseconds;
        trigger = System.nanoTime() +
                NANOSECONDS.convert(delta, MILLISECONDS);
        sequence.add(this);
    }
    public long getDelay(TimeUnit unit) {
        return unit.convert(
                trigger - System.nanoTime(), NANOSECONDS);
    }
    public int compareTo(Delayed arg) {
        DelayedTask1 that = (DelayedTask1)arg;
        if(trigger < that.trigger) return -1;
        if(trigger > that.trigger) return 1;
        return 0;
    }
    public void run() { System.out.println(this + " "); }
    public String toString() {
        return String.format("[%1$-4d]", delta) +
                " Task " + id;
    }
    public String summary() {
        return "(" + id + ":" + delta + ")";
    }
    public static class EndSentinel extends DelayedTask1 {
        private ExecutorService exec;
        public EndSentinel(int delay, ExecutorService e) {
            super(delay);
            exec = e;
        }
        public void run() {
            for(DelayedTask1 pt : sequence) {
                System.out.println(pt.summary() + " ");
            }
            System.out.println();
            System.out.println(this + " Calling shutdownNow()");
            exec.shutdownNow();
        }
    }
}

class DelayedTaskConsumer1 implements Runnable {
    private DelayQueue<DelayedTask1> q;
    public DelayedTaskConsumer1(DelayQueue<DelayedTask1> q) {
        this.q = q;
    }
    public void run() {
        try {
            while(!Thread.interrupted())
                q.take().run(); // Run task with the current thread
        } catch(InterruptedException e) {
            // Acceptable way to exit
        }
        System.out.println("Finished DelayedTaskConsumer");
    }
}
