package virtualMachine.theory;

import java.sql.Ref;

/**
 * Created by IntelliJ IDEA.
 * User: XINGHAIFANG
 * Date: 2019/3/26
 * Time: 11:32
 * testGC()方法执行后,objA 和objB是否会被gc呢?
 */
public class ReferenceCountingGc {
    public Object instance = null;
    private static final int _1MB = 1024 * 1024;
    //成员变量占用部分内存.
    private byte[] bigSize = new byte[2 * _1MB];

    /**
     * 引用计数器法,垃圾回收互相引用,会无法回收,添加-XX:+PrintGCDetails参数打印垃圾回收过程.
     * @param args
     */
    public static void main(String[] args) {
        ReferenceCountingGc objA = new ReferenceCountingGc();
        ReferenceCountingGc objB = new ReferenceCountingGc();
        objA.instance = objB;
        objB.instance = objA;
        objA = null;
        objB = null;
        System.gc();
    }

}
