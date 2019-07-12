package virtualMachine.theory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

import static java.lang.invoke.MethodHandles.lookup;

/**
 * Created by IntelliJ IDEA.
 * User: XINGHAIFANG
 * Date: 2019/6/27
 * Time: 10:47
 */
public class MethodHandleTest {
    static class ClassA{
        public void println(String s){
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Throwable{
        Object obj= System.currentTimeMillis() % 2 ==0?System.out:new ClassA();
        getPrintlnMH(obj).invokeExact("xinghaifang");
    }

    private static MethodHandle getPrintlnMH(Object reveiver) throws Throwable{
        MethodType mt = MethodType.methodType(void.class,String.class);
        return lookup().findVirtual(reveiver.getClass(),"println",mt).bindTo(reveiver);

    }

}
