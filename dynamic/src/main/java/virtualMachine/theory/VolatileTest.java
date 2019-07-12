package virtualMachine.theory;

/**
 * Created by IntelliJ IDEA.
 * User: XINGHAIFANG
 * Date: 2019/7/1
 * Time: 11:17
 * volatile 自增运算测试
 */
public class VolatileTest {
    public static volatile int race = 0;
    public static void increase(){
        race++;
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_COUNT];
        for(int i=0;i<THREADS_COUNT;i++){
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<1000;i++){
                        increase();
                    }
                }
            });
            threads[i].start();
        }
        while (Thread.activeCount()>1){
            Thread.yield();
        }

        System.out.println(race);
    }
}
