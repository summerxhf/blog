
# java多线程--锁的可重用性

- 内部锁可重入性 <br>当一个线程请求其他线程已经占有锁的时,请求线程将被阻塞。然后内部锁是可重进入的，因此线程在视图获得它自己占有的锁时，请求会成功。

- 重入<br>：意味着是基于“每线程” 而不是“每次调用”。
例如：子类复写了父类的synchronized类型方法，并调用父类中的方法，如果没有可重入锁可能会造成死锁,如下代码。
```$xslt
public class Widget {
    public synchronized void doSomething(){
        System.out.println("i'm father, in execute...");
    }
}

public class LoggingWidget extends Widget {
    public synchronized void doSomething(){
        System.out.println("child " +  toString() + ": calling doSomething");
        super.doSomething();
    }
}
```
- 原理<br>：每个锁关联一个请求计数器和一个占有他的线程。当计数器为0时，认为锁未被占有的。请求一个未被战友过的锁时，jvm将记录锁的占有者。
并未为请求计数为1。如果同一个线程再次请求这个锁，计数将递增，每次占用线程退出同步锁，计数器递减，直到计数器达到为0时，锁被释放。


