##第一章
略。

##第二章 线程安全性

###什么是线程安全性
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

###原子性
- 当某个计算的正确性取决于多个线程的交替执行时序时，那么就会发生竞态条件。（示例略）

- 假定有两个操作A和B,如果从执行A的线程来看,当另一个线程执行B时,要么将B全部执行完,要么完全不执行B,那么A和B对彼此来说是原子的。

###加锁机制
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

###活跃性和性能
- 要判断同步代码块的合理大小，需要在各种设计需求之间进行权衡，包括安全性、简单性和性能。有时候，在简单性和性能之间会发生冲突。

- 在执行耗时较长的操作时，一定不要持有锁。

##第三章 对象的共享
###可见性
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

###线程封闭
如果仅在单线程内访问数据，就不需要同步。这种技术被称为线程封闭。

- Ad-hoc线程封闭：维护线程封闭性的职责完全由程序实现来承担。
- 栈封闭：只能通过局部变量才能访问对象
- ThreadLocal

###不变性
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

###安全发布
这一节的内容太繁琐了，暂且略过。

