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









