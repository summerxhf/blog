package virtualMachine.theory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import static java.lang.invoke.MethodHandles.lookup;

/**
 * Created by IntelliJ IDEA.
 * User: XINGHAIFANG
 * Date: 2019/6/27
 * Time: 11:06
 */
public class Test {
   class GrandFather{
       void thinking(){
           System.out.println("i am grandfather");
       }
   }

   class Father extends GrandFather{
       void thinking(){
           System.out.println("i am father");
       }
   }

   class son extends Father{
       void thinking(){
           try{
               MethodType methodType = MethodType.methodType(void.class);
               MethodHandle methodHandle = lookup().findSpecial(GrandFather.class,"thinking",methodType,getClass());
               methodHandle.invoke(this);
           }catch (Throwable e){

           }
       }
   }

    public static void main(String[] args) {
        (new Test().new son()).thinking();
    }
}
