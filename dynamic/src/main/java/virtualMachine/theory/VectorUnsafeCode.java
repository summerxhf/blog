package virtualMachine.theory;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: XINGHAIFANG
 * Date: 2019/7/2
 * Time: 10:04
 * vector 对象本身是线程安全的, 但是在方法的使用上可能出现线程不安全的代码,如下例子;
 * 尽管Vector的get() remove() size()方法都是同步的,但是在多线程环境中, 如果不在方法调用端额外做同步措施的话,这段代码仍然
 * 是线程不安全的;如果另外一个线程恰好在错误的时间里删除一个元素,导致序号i已经不再可用的话,在访问i就会报ArrayIndexOutOfBoundsException;
 */
public class VectorUnsafeCode {
    private static Vector<Integer> vector = new Vector<Integer>();
    public static void main(String[] args) {
        while(true){
            for(int i=0;i<10;i++){
                vector.add(i);
            }

            Thread removeThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    for (int i=0;i<vector.size();i++){
                        vector.remove(i);
                    }
                }
            });

            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0;i<vector.size();i++){
                        System.out.println(vector.get(i));
                    }
                }
            });


            removeThread.start();
            printThread.start();
            while (Thread.activeCount()>40);
        }
    }
}
