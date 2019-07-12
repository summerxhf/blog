package virtualMachine.theory;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: XINGHAIFANG
 * Date: 2019/7/5
 * Time: 9:38
 */
public class VectorSafeCode {
    private static Vector<Integer> vector = new Vector<Integer>();

    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }

            Thread removeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (vector){
                        for (int i = 0; i < vector.size(); i++) {
                            vector.remove(i);
                        }
                    }

                }
            });

            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (vector){
                        for (int i = 0; i < vector.size(); i++) {
                            System.out.println(vector.get(i));
                        }
                    }
                }
            });


            removeThread.start();
            printThread.start();
            while (Thread.activeCount() > 40) ;
        }
    }
}