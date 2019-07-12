package virtualMachine.theory;

/**
 * Created by IntelliJ IDEA.
 * User: XINGHAIFANG
 * Date: 2019/6/28
 * Time: 11:05
 */
public class AutomaticPackingTest {

    public static void main(String[] args) {
        Integer a =1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e =321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d);
        System.out.println(d == f);
//        System.out.println(c == (a+b));
        System.out.println(c.equals(a+b));
        System.out.println(g == (a + b));
        System.out.println(g.equals(a + b));
    }
}
