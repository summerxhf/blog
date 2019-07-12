package virtualMachine.theory;

/**
 * Created by IntelliJ IDEA.
 * User: XINGHAIFANG
 * Date: 2019/7/1
 * Time: 11:29
 * dcl 双锁检测 double check lock
 */
public class Singleton {
    private volatile static Singleton instance;

    public static Singleton getInstance(){
        if(instance==null){
            synchronized (Singleton.class){
                if(instance==null){
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }

    public static void main(String[] args) {
        Singleton.getInstance();
    }
}
