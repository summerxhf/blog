package virtualMachine.theory;

import sun.awt.windows.ThemeReader;

/**
 * Created by IntelliJ IDEA.
 * User: XINGHAIFANG
 * Date: 2019/6/26
 * Time: 10:54
 */
public class DeadLoopClass {
    static {
        if(true){
            System.out.println(Thread.currentThread()+ "init DeadLoopClass");
            while (true){

            }
        }
    }

    public static void main(String[] args) {
        Runnable script = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + "start");
                DeadLoopClass deadLoopClass = new DeadLoopClass();
                System.out.println(Thread.currentThread()+" run over");
            }
        };

        Thread thread1 = new Thread(script);
        Thread thread2 = new Thread(script);
        thread1.start();
        thread2.start();
    }
}
