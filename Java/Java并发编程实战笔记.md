## 第一章
略。

## 第二章 线程安全性

### 什么是线程安全性
- 当多个线程访问某个类时，不管运行时环境采用何种调度方式或者这些进程将如何交替执行，并且在主调代码中不需要任何额外的同步或协同，这个类都能表现出正确的行为，那么就称这个类是线程安全的。

写了一个简陋的程序证明Hashmap是线程不安全的：

```

public class MapTest {

    Map<Integer, MapElement> map = new HashMap<>();

    static class MapElement{

        @Override
        public int hashCode() {
            return 0;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        MapTest test = new MapTest();

        new Thread(){
            @Override
            public void run(){
                int i = 0;
                while(i < 10000){
                    i++;
                    test.map.put(i, new MapElement());
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run(){
                int i = 10000;
                while(i < 20000){
                    i++;
                    test.map.put(i, new MapElement());
                }
            }
        }.start();

        Thread.sleep(1000);

        for(int i = 1; i < 20000; i++) {
            if(!test.map.containsKey(i)){
                System.out.println("" + i);
            }
        }
    }
}

```

主要思路是把所有value都通过hashcode映射到同一个位置，然后不同线程在向该位置的链表插入数据时会互相覆盖，导致有的值写进去了但读不到。

### 原子性

- 当某个计算的正确性取决于多个线程的交替执行时序时，那么就会发生竞态条件。（示例略）

- 假定有两个操作A和B,如果从执行A的线程来看,当另一个线程执行B时,要么将B全部执行完,要么完全不执行B,那么A和B对彼此来说是原子的。

### 加锁机制

- 由于内置锁是可重入的，因此如果某个线程试图获得一个已经由它自己持有的锁，那么这个请求就会成功。

```
public class Father  
{  
    public synchronized void doSomething(){  
        ......  
    }  
}  
  
public class Child extends Father  
{  
    public synchronized void doSomething(){  
        ......  
        super.doSomething();  
    }  
} 
```

由于Father和Child中的doSomething方法都是synchronized方法，每个doSomething方法在执行前都会获取Child对象实例上的锁。如果内置锁不是可重入的，那么在调用super.doSomething时将无法获得该Child对象上的互斥锁，因为这个锁已经被持有。

- 每个共享和可变的变量都应该只由一个锁保护。对于每个包含多个变量的不变性条件，其中涉及的所有变量都需要由同一个锁来保护。

### 活跃性和性能
- 要判断同步代码块的合理大小，需要在各种设计需求之间进行权衡，包括安全性、简单性和性能。有时候，在简单性和性能之间会发生冲突。

- 在执行耗时较长的操作时，一定不要持有锁。

## 第三章 对象的共享
### 可见性
确保当一个线程修改了对象状态后，其他线程能够看到发生的状态变化。

```
public class NoVisibility {  
	private static boolean ready;  
	private static int number;  
	private static class ReaderThread extends Thread { 
 
		public void run() {  
			while (!ready)  
			Thread.yield();  
			System.out.println(number);  
		}  
	}  

	public static void main(String[] args) {  
		new ReaderThread().start();  
		number = 42;  
		ready = true;  
	}  
}  
```
NoVisibilty可能会永远循环下去，因为读线程可能永远都看不到ready的值。NoVisibilty也可能会输出0，因为读线程可能看到了写入ready的值，但却没有看到之后写入number的值。


- Java内存模型要求，变量的读取操作和写入操作都必须是原子操作，但对于非volatile类型的long和double变量，JVM允许将64位的读操作或写操作分解为两个32位的操作。

### 发布与逸出
发布一个对象的意思是指，是对象能够在当前作用域之外的代码中使用。
当某个不应该发布的对象被发布时，这种情况就被称为逸出。

- 当从对象的构造函数中发布对象时，只是发布了一个尚未构造完成的对象。即使发布对象的语句位于构造函数的最后一行也是如此。

```
public class ThisEscape {  
    public ThisEscape(EventSource source) {  
        source.registerListener(  
            new EventListener() {  
                public void onEvent(Event e) {  
                    doSomething(e);  
                }  
            });  
    }  
}  
```
如果在这里发布了EventListener，则其它类在使用EventListener时，ThisEscape未必已经构造完成。
解决方案：使用工厂方法。

```
public class SafeListener {  
    private final EventListener listener;  
  
    private SafeListener() {  
        listener = new EventListener() {  
            public void onEvent(Event e) {  
                doSomething(e);  
            }  
        };  
    }  
  
    public static SafeListener newInstance(EventSource source) {  
        SafeListener safe = new SafeListener();  
        source.registerListener(safe.listener);  
        return safe;  
    }  
}
```

### 线程封闭
如果仅在单线程内访问数据，就不需要同步。这种技术被称为线程封闭。

- Ad-hoc线程封闭：维护线程封闭性的职责完全由程序实现来承担。
- 栈封闭：只能通过局部变量才能访问对象
- ThreadLocal

### 不变性
不可变对象一定是线程安全的。

```
@Immutable  
class OneValueCache {  
   private final BigInteger lastNumber;  
   private final BigInteger[] lastFactors;  
 
   public OneValueCache(BigInteger i,  
                        BigInteger[] factors) {  
       lastNumber  = i;  
       lastFactors = Arrays.copyOf(factors, factors.length);  
   }  
 
   public BigInteger[] getFactors(BigInteger i) {  
       if (lastNumber == null || !lastNumber.equals(i))  
           return null;  
       else  
           return Arrays.copyOf(lastFactors, lastFactors.length);  
   }  
} 

@ThreadSafe  
public class VolatileCachedFactorizer implements Servlet {  
   private volatile OneValueCache cache =  
       new OneValueCache(null, null);  
 
   public void service(ServletRequest req, ServletResponse resp) {  
       BigInteger i = extractFromRequest(req);  
       BigInteger[] factors = cache.getFactors(i);  
       if (factors == null) {  
           factorfactors = factor(i);  
           cache = new OneValueCache(i, factors);  
       }  
       encodeIntoResponse(resp, factors);  
   }  
} 
```

### 安全发布
这一节的内容太繁琐了，暂且略过。

## 第四章 对象的组合
### 设计线程安全的类
- 找出构成对象状态的所有变量
- 找出约束状态变量的不变性条件
- 建立并发访问管理策略

### 实例封闭
将数据封装在对象内部，可以将数据的访问限制在对象的方法上。（实例：Collections.synchronizedList）

- Java监视器模式：把对象的所有可变状态都封装起来，并由对象自己的内置锁来保护

```
public class PrivateLock{
    private final Object myLock=new Object();
    @GuardedBy("myLock") Widget widget;
    void someMethod()
    { 
            synchronized(myLock)
            {
                    //修改或访问Widget的状态
            }
    }
}
```

### 线程安全性的委托
如果类中的各个组件都已经是线程安全的，是否需要再增加一个额外的线程安全层？答案是“视情况而定”。
具体例子略。

### 在现有的线程安全类中添加功能
最安全的方法就是修改原有的类，但这通常无法做到。另一种方法是扩展这个类，假定在设计这个类时考虑了可扩展性。扩展方法比直接将代码添加到类中更加脆弱，因为现在的同步策略实现被分布到多个单独维护的源代码文件中。
更好的方法是组合：

```
@ThreadSafe 

public class ImprovedList<T> implements List<T> { 

	private final List<T> list; 

	public ImprovedList(List<T> list) { 
		this.list = list; 
	} 

	public synchronized boolean putIfAbsent(T x) { 
		boolean contains = list.contains(x); 
		if (contains) 
			list.add(x); 
		return !contains; 
	} 

	public synchronized void clear() { list.clear(); } 

	// ... similarly delegate other List methods 
}  
```

### 同步策略文档化
略

## 第五章 同步构建模块
### 同步容器类
是由Collections.synchronizedXXX等工厂方法创建的。

- 同步容器类的复合操作，如迭代、跳转、条件运算等，是线程安全的，但在其它线程并发地修改容器的情况下，仍可能会抛出异常。

```
public class HiddenIterator {  
    private final Set<Integer> set = new HashSet<Integer>();  
      
    public synchronized void add(Integer i) {set.add(i);}  
    public synchronized void remove(Integer i) {set.remove(i);}  
      
    public void addTenThings() {  
        Random r = new Random();  
        for (int i = 0; i < 10; i++) {  
            add(r.nextInt());  
            System.out.println("DEBUG:added ten elements to"+set);  
        }  
    }  
} 
```
这段代码也可能抛出concurrentmodificationexception，因为toString方法有隐含的迭代器。

### 并发容器

- ConcurrentHashMap:分段锁。迭代器不会抛出concurrentModificationException，但不保证返回最新的内容。size不一定准确。
具有putIfAbsent、replaceIfEqual、removeIfEqual等功能。

- CopyOnWriteArrayList

### 阻塞队列和生产者-消费者模式
BlockingQueue提供了可阻塞的put和take方法。如果队列已经满了，那么put方法将阻塞到有空间可用；如果队列为空，那么take方法将会阻塞直到有元素可用。
BlockingQueue是一个接口，具体实现有LinkedBlockingQueue和ArrayBlockingQueue等。

```
public class FileCrawler implements Runnable{  
    private final BlockingQueue<File> fileQueue;  
    private final FileFilter filter;  
    private final File root;  
      
    public FileCrawler(BlockingQueue<File> queue, FileFilter filter, File root){  
        this.fileQueue = queue;  
        this.filter = filter;  
        this.root = root;  
    }  
      
    public void run(){  
        try{  
            crawl(root);  
        }catch(InterruptedException e){  
            Thread.currentThread().interrupt();  
        }  
    }  
  
    private void crawl(File root) throws InterruptedException{  
        File[] files = root.listFiles(filter);  
        if(files != null){  
            for(File file : files){  
                if(file.isDirectory()){  
                    crawl(file);  
                }else if(!alreadyIndexed(file)){  
                    fileQueue.put(file);  
                }  
            }  
        }  
    }  
}  
  
//建立索引，消费者  
Public class Indexer implements Runnable{  
    private final BlockingQueue<File> queue;  
      
    public Indexer(BlockingQueue<File> queue){  
        this.queue = queue;  
    }  
    ......  
    public void run(){  
        try{  
            while(true){  
                indexFile(queue.take());  
            }  
        }catch(InterruptedException e){  
            Thread.currentThread().interrupt();  
        }  
    }  
}  
  
//开始搜索  
public static void startIndexing(File[] roots){  
    BlockingQueue<File> queue = new LinkedBlockingQueue<File>(BOUND);  
    FileFilter filter = new FileFilter(){  
        public boolean accept(File file){  
            return true;  
        }  
    };  
    for(File file : roots){  
        new Thread(new FileCrawler(queue, filter, root)).start();  
    }  
    for(int i = 0; i < N_CONSUMERS; i++){  
        new Thread(new Indexer(queue)).start();  
    }  
}  
```

- 双端队列这段暂时没看懂。

### 阻塞方法和中断方法
阻塞操作和执行时间很长的普通操作的差别在于，被阻塞的线程必须等待某个不受它控制的事件发生后才能继续执行。
当某方法抛出InterruptedException时，表示该方法是一个阻塞方法。而不抛出InterruptedException的方法并不一定不是阻塞方法，比如调用了阻塞方法的方法，就也是阻塞方法。

### 同步工具类

- CountDownLatch：一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。

- FutureTask：

```
public class Preloader{
	private final FutureTask<ProductInfo> future = 
		new FutureTask<ProductInfo>(new Callable<ProductInfo>(){
			public ProductInfo call() throws DataLoadException{
				return loadProductInfo();
			}
		});
	private final Thread thread = new Thread(future);
	
	public void start() {thread.start();}
	
	public ProductInfo get() throws DataLoadException, InterruptedException{
		try {
			return future.get();
		} catch(ExecutionException e) {
			Throwable cause = e.getCause();
			if (cause instance of DataLoadException) {
				throw (DataLoadException)cause;
			} else {
				throw launderThrowable(cause);
			}
		}
	}
}
```

- Semaphore: 管理着一组虚拟的许可，执行操作时需要首先获得许可。如果没有多余的，则阻塞直至获得许可。

- Barrier：类似于CountDownLatch，能阻塞一组线程直到某个事件发生。

## 第六章 任务执行
### 在线程中执行任务（略）

### Executor框架
```
public interface Executor {
	void execute(Runnable command);
}
```
- SingleThreadExecutor分类：
 - newSingleThreadExecutor：
创建一个单线程的线程池。
 - newFixedThreadPoolExecutor：
创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。
 - newCachedThreadPoolExecutor：
创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，添加新线程来处理任务。线程数上限没有限制。
 - newScheduledThreadPoolExecutor：
创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。

- Executor的生命周期
 - void shutDown();
 - List<Runnable> shutdownNow();
 - boolean isShutdown();
 - boolean isTerminated();
 - boolean awaitTermination(long timeout, TimeUnit unit)

### 找出可利用的并行性
- Callable是一种对延迟计算的比Runnable更好的抽象：它认为主入口点将返回一个值，并可能抛出一个异常。
- Future表示一个任务的生命周期。

```
public interface Callable<V> {
    V call() throws Exception;
}

public interface Future<V> {
    boolean cancel(boolean mayInterruptIfRunning);
    boolean isCancelled();
    boolean isDone();
    V get() throws InterruptedException, ExecutionException;
    V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
```
各种示例略。

## 第七章 取消与关闭
### 任务的取消

```
public class BrokenPrimeProducer extends Thread {
    static int i = 1000;

    private final BlockingQueue<BigInteger> queue;

    private volatile boolean cancelled = false;

    BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        BigInteger p = BigInteger.ONE;
        try {
            while (!cancelled) {
                p = p.nextProbablePrime();
                queue.put(p);
            }
        } catch (InterruptedException cusumed) {
        }
    }

    public void cancel() {
        this.cancelled = false;
    }

    public static void main(String args[]) throws InterruptedException {
        BlockingQueue<BigInteger> queue = new LinkedBlockingQueue<BigInteger>(
                10);
        BrokenPrimeProducer producer = new BrokenPrimeProducer(queue);
        producer.start();
        try {
            while (needMorePrimes())
                queue.take();
        } finally {
            producer.cancel();
        }
    }

    public static boolean needMorePrimes() throws InterruptedException {
        boolean result = true;
        i--;
        if (i == 0)
            result = false;
        return result;
    }
}
```
我们在main中通过queue.take来消费产生的素数（虽然仅仅是取出扔掉），我们只消费了1000个素数，然后尝试取消产生素数的任务，很遗憾，取消不了，因为产生素数的线程产生素数的速度大于我们消费的速度，我们在消费1000后就停止消费了，那么任务将被queue的put方法阻塞。

* Java的API或语言规范并没有将中断与任何取消语意关联起来。但实际上，如果在取消之外的其它操作中使用中断，都是不合适的。

* 调用interrupt并不意味着立即停止目标线程正在进行的工作，而只是传递了请求中断的消息。有些方法，例如wait、sleep和join等，将严格地处理这种请求。

* 由于每个线程拥有各自的中断策略,因此除非你知道中断对该线程的含义,否则就不应该中断这个线程。

* 有两种实用策略可用于处理InterruptedException：
	- 抛出异常
	- 调用interrupt恢复中断状态
* 你不能屏蔽InterruptedException，例如在catch块中捕获到异常却不做任何处理，除非在你的代码中实现了线程的中断策略。




































