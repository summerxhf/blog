
    
# threadlocal的demo和源码分析

- threadlocal的demo<br>
**1.Context.java 上下文.**<br>
```
package threadlocal;

public class Context {
    private String transactionId = null;
    //getter and setter
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

}

```
**2.MyThreadLocal.java**<br>
```
package threadlocal;

public class MyThreadLocal {
    public static final ThreadLocal userThreadLocal = new ThreadLocal();

    public static void set(Context user){
        userThreadLocal.set(user);
    }
    public static void unset(){
        userThreadLocal.remove();
    }

    public static Context get(){
        return (Context) userThreadLocal.get();
    }
}

```
**3.BusinessService.java**<br>
```
package threadlocal;

/**
 * read from threadlocal and read value
 */
public class BusinessService {
    public void businessMethod(){
        Context context = MyThreadLocal.get();//获取threadlocal中的user context
        System.out.println(context.getTransactionId());//获取用户的事务id.
    }
}

```
**4.ThreadLocalDemo  main()方法**
```
package threadlocal;

public class ThreadLocalDemo extends Thread{
    public static void main(String[] args) {
        Thread threadOne = new ThreadLocalDemo();
        threadOne.start();

        Thread threadTwo = new ThreadLocalDemo();
        threadTwo.start();
    }
    @Override
    public void run(){
       Context context = new Context();
       context.setTransactionId(getName());//线程名称
       MyThreadLocal.set(context);

       new BusinessService().businessMethod();
       MyThreadLocal.unset();//移出threadlocal变量,防止内存泄漏.

    }
}

```
- **threadlocal基本原理和使用场景**<br>
**基本原理**<br>
主要是在多线程场景下为了防止自己的变量被其他线程篡改,可以使用ThreadLocal。<br>
threadlocal 这个类能使线程中的某个值与保存值的对象关联起来。ThreadLocal提供了get与set等访问接口或者方法，
这些方法为每个使用该变量的线程都存有一份独立的副本，因此get总是返回由当前执行线程在调用set时设置的最新值。

**使用场景**<br>
最常见的ThreadLocal的使用场景用来解决数据库连接、session管理。
如下代码：<br>
**数据库连接**
```
package usageScenario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接threadlocal
 */
public class connectionHolderThreadDemo{
   public static String DB_URL="todo:dburl";
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
        public Connection initialValue()  {
            try {
                return DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    public static Connection getConnection(){
        return connectionHolder.get();
    }
}

```
**session管理**
```
package usageScenario;

/**
 * threadlocal 用于session管理(仅仅作为示例,代码会飘红).
 */
public class SessionThreadLocalDemo {
    private static final ThreadLocal threadSession = new ThreadLocal();


    public static Session getSession() throws InfrastructureException {
        Session s = (Session) threadSession.get();
        try {
            if (s == null) {
                s = getSessionFactory().openSession();
                threadSession.set(s);
            }
        } catch (HibernateException ex) {
            throw new InfrastructureException(ex);
        }
        return s;
    }

}

```
例如在建立数据库连接的时候，当频繁的需要使用数据库连接对象，又希望避免每次执行的时候都
要重新new该对象，就可以使用ThreadLocal
- **ThreadLocal源码解析**

ThreadLocal类的get()方法，如下代码
源码中的注释也说明了该方法的作用，返回当前线程的线程本地变量，如果当前线程没有变量值，返回
初始化调用方法setInitialValue()的值。
```
      /**
       * Returns the value in the current thread's copy of this
       * thread-local variable.  If the variable has no value for the
       * current thread, it is first initialized to the value returned
       * by an invocation of the {@link #initialValue} method.
       *
       * @return the current thread's value of this thread-local
       */
      public T get() {
          Thread t = Thread.currentThread();
          ThreadLocalMap map = getMap(t);
          if (map != null) {
              ThreadLocalMap.Entry e = map.getEntry(this);
              if (e != null) {
                  @SuppressWarnings("unchecked")
                  T result = (T)e.value;
                  return result;
              }
          }
          return setInitialValue();
      }
```

set 方法设置当前线程的局部副本变量，子类不需要重写这个方法
```
 /**
     * Sets the current thread's copy of this thread-local variable
     * to the specified value.  Most subclasses will have no need to
     * override this method, relying solely on the {@link #initialValue}
     * method to set the values of thread-locals.
     *
     * @param value the value to be stored in the current thread's copy of
     *        this thread-local.
     */
    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }
```

**1.   ThreadLocal 类中的ThreadLocalMap**(图片来自于网络)
![图片来自网络](https://mmbiz.qpic.cn/mmbiz_png/8Jeic82Or04mib65dVtxHYQia5J0gg88fH46Wx4xMaswgCIjjAcuDic1mAUec0oiaepSzSJNZzMOxdvcSFkK7rgtrMg/?tp=webp&wxfrom=5&wx_lazy=1)

从源码中可以看到变量是放到一个map结构中的Entry中，ThreadLocalMap是ThreadLocal类静态内部类，可以看到源码注释中的说明

```
    /**
     * ThreadLocalMap is a customized hash map suitable only for
     * maintaining thread local values. No operations are exported
     * outside of the ThreadLocal class. The class is package private to
     * allow declaration of fields in class Thread.  To help deal with
     * very large and long-lived usages, the hash table entries use
     * WeakReferences for keys. However, since reference queues are not
     * used, stale entries are guaranteed to be removed only when
     * the table starts running out of space.
     */
    static class ThreadLocalMap { //omit...}
```
ThreadLocalMap 是一种定制的散列映射,仅适用于维护线程本地值。该类是包私有的，帮助处理非常大的和长寿命的使用，
哈希表entries ，初始化大小为16的entry数组，Entry对象用来保存每一个key-value键值对，key为ThreadLocal对象本身，
Entry继承WeakReference,和HashMap的区别,Entry中没有next字段, 不存在链表结构。
```
 static class Entry extends WeakReference<ThreadLocal<?>> {
            /** The value associated with this ThreadLocal. */
            Object value;

            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }
```

**2. ThreadLocalMap Entry中的key hashcode怎么自增?**<br>
每个ThreadLocal对象中都一个hash值,private static final int HASH_INCREMENT = 0x61c88647;,
在插入过程中,根据ThreadLocal对象的hash值,定位到table中的位置i,过程如下: 
- 如果当前位置是空的，在当前位置初始化一个Entry对象放到位置i上；
- 如果当前位置i已经有Entry对象了，如果这个Entry对象的key正好是即将设置的key，那么重新设置该位置Entry中的value。
- 如果位置i已经有了Entry对象了, 并且key也不相等,那么只能找下一个空位置（key是否相等，equals方法，比较值是否一样）。<br>

如下图,可以看到ThreadLocal整体的内部结构

![这里写图片描述](https://img-blog.csdn.net/20180328161446205?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xvdmVzdW1tZXJmb3JldmVy/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

**3.关于ThreadLocal的内存泄漏**<br>
在使用ThreadLocal的时候，key和value放在Entry中，如下代码中可以看到Entry 继承弱引用WeakReference，所以深入代码可以看到
key被保存到了WeakReference对象中。

这就导致了一个问题，ThreadLocal没有外部强引用时，发生GC时会被回收，如果创建ThreadLocal的线程一直持续运行，那么这个Entry对象中的
value有可能一直得不到回收，因为创建ThreadLocal的线程还在，可能会发生内存泄漏。

```
  static class Entry extends WeakReference<ThreadLocal<?>> {
            /** The value associated with this ThreadLocal. */
            Object value;

            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }
```

**4.如何避免ThreadLocal的内存泄漏**<br>
可以在使用完本线程的ThreadLocal后,显示调用remove()方法,就会删除Entry中的key对应的value。

**5.上面提到了弱引用，什么是弱引用和强引用，弱引用一定会发生内存泄漏吗？**<br>
我们知道当我们new的对象多的时候，且给java进程分配的内存不足够大的时候，可能会发生gc异常，会报OutOfMemoryError错误，
使得程序终止运行，也不会随意的回收具有强引用的对象来解决内存不足的问题。
而弱引用恰好相反，java的弱引用指的是java.lang.ref.WeakReference<T> 类。弱引用的对象有短暂的生命周期，在垃圾回收的线程中，扫描所管辖的区域过程中，一旦发现了只有弱引用的对象，
不管内存是否足够与否，都会回收它的内存。只不过垃圾回收线程是一个优先级比较低的线程，因为不一定会很快的发现弱引用的对象。

而java中的虚引用是最弱的引用，虚引用的作用是它指向的对象被回收后，虚引用本身会被加入到引用队列，用于记录它指向的对象已被回收。

(ps:并不是弱引用才会发现内存泄漏,是在一定场合的使用情况下,其实除了强应用之外的引用,是为了避免发生内存泄漏而存在的。所以说内存泄漏并不是因为弱引用和强引用，是因为某些数据结构使用的不恰当，可能会造成内存泄漏)
java中还有软引用和虚引用，软引用和弱引用区别是，只有当对象内存空间不足时，才会回收软引用。)


**下面表格是对强引用、软引用、弱引用、虚引用的区别分析**
<table border="1" cellpadding="0" cellspacing="0" style="font-size:13px;border-collapse:collapse;margin-top:10px;margin-bottom:10px;color:rgb(0,0,102);font-family:verdana, arial, helvetica, sans-serif;"><tbody style="background-color:inherit;"><tr style="background-color:inherit;"><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="font-family:'Courier New';font-size:12px;"><strong>引用类型</strong></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="background-color:inherit;"><span style="font-family:'Courier New';background-color:inherit;"><strong>被垃圾回收时间</strong></span></span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="background-color:inherit;"><span style="font-family:'Courier New';background-color:inherit;"><strong>   用途</strong></span></span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="background-color:inherit;"><span style="font-family:'Courier New';background-color:inherit;"><strong>   生存时间</strong></span></span></span></p></td></tr><tr style="background-color:inherit;"><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">强引用</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">从来不会</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">对象的一般状态</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">JVM停止运行时终止</span></span></p></td></tr><tr style="background-color:inherit;"><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;color:rgb(255,0,0);font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">软引用</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">在内存不足时</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">对象缓存</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">内存不足时终止</span></span></p></td></tr><tr style="background-color:inherit;"><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;color:rgb(255,0,0);font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">弱引用</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">在垃圾回收时</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">对象缓存</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">gc运行后终止</span></span></p></td></tr><tr style="background-color:inherit;"><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;color:rgb(255,0,0);font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">虚引用</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">Unknown</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">Unknown</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">Unknown</span></span></p></td></tr></tbody></table>

- **summary**<br>

写这篇文章用了很长的时间,也许是长久的想要总结而一直被搁置的原因吧,有些观点是来自《java并发编程》，有些观点是来自网络上很多人的博客，
有些是自己理解后的一些观点，望提出宝贵意见，谢谢




