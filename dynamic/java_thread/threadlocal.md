
    
# threadlocal��demo��Դ�����

- threadlocal��demo<br>
**1.Context.java ������.**<br>
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
        Context context = MyThreadLocal.get();//��ȡthreadlocal�е�user context
        System.out.println(context.getTransactionId());//��ȡ�û�������id.
    }
}

```
**4.ThreadLocalDemo  main()����**
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
       context.setTransactionId(getName());//�߳�����
       MyThreadLocal.set(context);

       new BusinessService().businessMethod();
       MyThreadLocal.unset();//�Ƴ�threadlocal����,��ֹ�ڴ�й©.

    }
}

```
- **threadlocal����ԭ���ʹ�ó���**<br>
**����ԭ��**<br>
��Ҫ���ڶ��̳߳�����Ϊ�˷�ֹ�Լ��ı����������̴߳۸�,����ʹ��ThreadLocal��<br>
threadlocal �������ʹ�߳��е�ĳ��ֵ�뱣��ֵ�Ķ������������ThreadLocal�ṩ��get��set�ȷ��ʽӿڻ��߷�����
��Щ����Ϊÿ��ʹ�øñ������̶߳�����һ�ݶ����ĸ��������get���Ƿ����ɵ�ǰִ���߳��ڵ���setʱ���õ�����ֵ��

**ʹ�ó���**<br>
�����ThreadLocal��ʹ�ó�������������ݿ����ӡ�session����
���´��룺
**���ݿ�����**
```
package usageScenario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ���ݿ�����threadlocal
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
**session����**
```
package usageScenario;

/**
 * threadlocal ����session����(������Ϊʾ��,�����Ʈ��).
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
�����ڽ������ݿ����ӵ�ʱ�򣬵�Ƶ������Ҫʹ�����ݿ����Ӷ�����ϣ������ÿ��ִ�е�ʱ��
Ҫ����new�ö��󣬾Ϳ���ʹ��ThreadLocal
- **ThreadLocalԴ�����**

ThreadLocal���get()���������´���
Դ���е�ע��Ҳ˵���˸÷��������ã����ص�ǰ�̵߳��̱߳��ر����������ǰ�߳�û�б���ֵ������
��ʼ�����÷���setInitialValue()��ֵ��
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

set �������õ�ǰ�̵߳ľֲ��������������಻��Ҫ��д�������
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

**1.   ThreadLocal ���е�ThreadLocalMap**
![ͼƬ��������](https://mmbiz.qpic.cn/mmbiz_png/8Jeic82Or04mib65dVtxHYQia5J0gg88fH46Wx4xMaswgCIjjAcuDic1mAUec0oiaepSzSJNZzMOxdvcSFkK7rgtrMg/?tp=webp&wxfrom=5&wx_lazy=1)

��Դ���п��Կ��������Ƿŵ�һ��map�ṹ�е�Entry�У�ThreadLocalMap��ThreadLocal�ྲ̬�ڲ��࣬���Կ���Դ��ע���е�˵��

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
ThreadLocalMap ��һ�ֶ��Ƶ�ɢ��ӳ��,��������ά���̱߳���ֵ�������ǰ�˽�еģ���������ǳ���ĺͳ�������ʹ�ã�
��ϣ��entries ����ʼ����СΪ16��entry���飬Entry������������ÿһ��key-value��ֵ�ԣ�keyΪThreadLocal������
Entry�̳�WeakReference,��HashMap������,Entry��û��next�ֶ�, ����������ṹ��
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

**2. ThreadLocalMap Entry�е�key hashcode��ô����?**
ÿ��ThreadLocal�����ж�һ��hashֵ,private static final int HASH_INCREMENT = 0x61c88647;,
�ڲ��������,����ThreadLocal�����hashֵ,��λ��table�е�λ��i,��������: 
- �����ǰλ���ǿյģ��ڵ�ǰλ�ó�ʼ��һ��Entry����ŵ�λ��i�ϣ�
- �����ǰλ��i�Ѿ���Entry�����ˣ�������Entry�����key�����Ǽ������õ�key����ô�������ø�λ��Entry�е�value��
- ���λ��i�Ѿ�����Entry������, ����keyҲ�����,��ôֻ������һ����λ�ã�key�Ƿ���ȣ�equals�������Ƚ�ֵ�Ƿ�һ������<br>

����ͼ,���Կ���ThreadLocal������ڲ��ṹ

![����дͼƬ����](https://img-blog.csdn.net/20180328161446205?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xvdmVzdW1tZXJmb3JldmVy/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

**3.����ThreadLocal���ڴ�й©**
��ʹ��ThreadLocal��ʱ��key��value����Entry�У����´����п��Կ���Entry �̳�������WeakReference���������������Կ���
key�����浽��WeakReference�����С�

��͵�����һ�����⣬ThreadLocalû���ⲿǿ����ʱ������GCʱ�ᱻ���գ��������ThreadLocal���߳�һֱ�������У���ô���Entry�����е�
value�п���һֱ�ò������գ���Ϊ����ThreadLocal���̻߳��ڣ����ܻᷢ���ڴ�й©��

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

**4.��α���ThreadLocal���ڴ�й©**
������ʹ���걾�̵߳�ThreadLocal��,��ʾ����remove()����,�ͻ�ɾ��Entry�е�key��Ӧ��value��

**5.�����ᵽ�������ã�ʲô�������ú�ǿ���ã�������һ���ᷢ���ڴ�й©��**
����֪��������new�Ķ�����ʱ���Ҹ�java���̷�����ڴ治�㹻���ʱ�򣬿��ܻᷢ��gc�쳣���ᱨOutOfMemoryError����
ʹ�ó�����ֹ���У�Ҳ��������Ļ��վ���ǿ���õĶ���������ڴ治������⡣
��������ǡ���෴��java��������ָ����java.lang.ref.WeakReference<T> �ࡣ�����õĶ����ж��ݵ��������ڣ����������յ��߳��У�ɨ������Ͻ����������У�һ��������ֻ�������õĶ���
�����ڴ��Ƿ��㹻��񣬶�����������ڴ档ֻ�������������߳���һ�����ȼ��Ƚϵ͵��̣߳���Ϊ��һ����ܿ�ķ��������õĶ���

java�л��������ú������ã������ú������������ǣ�ֻ�е������ڴ�ռ䲻��ʱ���Ż���������á�

��java�е������������������ã������õ���������ָ��Ķ��󱻻��պ������ñ���ᱻ���뵽���ö��У����ڼ�¼��ָ��Ķ����ѱ����ա�

�������Ƕ�ǿ���á������á������á������õ��������
<table border="1" cellpadding="0" cellspacing="0" style="font-size:13px;border-collapse:collapse;margin-top:10px;margin-bottom:10px;color:rgb(0,0,102);font-family:verdana, arial, helvetica, sans-serif;"><tbody style="background-color:inherit;"><tr style="background-color:inherit;"><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="font-family:'Courier New';font-size:12px;"><strong>��������</strong></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="background-color:inherit;"><span style="font-family:'Courier New';background-color:inherit;"><strong>����������ʱ��</strong></span></span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="background-color:inherit;"><span style="font-family:'Courier New';background-color:inherit;"><strong>   ��;</strong></span></span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="background-color:inherit;"><span style="font-family:'Courier New';background-color:inherit;"><strong>   ����ʱ��</strong></span></span></span></p></td></tr><tr style="background-color:inherit;"><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">ǿ����</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">��������</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">�����һ��״̬</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">JVMֹͣ����ʱ��ֹ</span></span></p></td></tr><tr style="background-color:inherit;"><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;color:rgb(255,0,0);font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">������</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">���ڴ治��ʱ</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">���󻺴�</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">�ڴ治��ʱ��ֹ</span></span></p></td></tr><tr style="background-color:inherit;"><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;color:rgb(255,0,0);font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">������</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">����������ʱ</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">���󻺴�</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">gc���к���ֹ</span></span></p></td></tr><tr style="background-color:inherit;"><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;color:rgb(255,0,0);font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">������</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">Unknown</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">Unknown</span></span></p></td><td valign="top" style="border:1px solid rgb(153,153,153);padding:5px 10px;background-color:inherit;"><p style="margin-top:5px;margin-bottom:5px;background-color:inherit;"><span style="background-color:inherit;font-size:14px;"><span style="font-family:'Courier New';background-color:inherit;">Unknown</span></span></p></td></tr></tbody></table>






