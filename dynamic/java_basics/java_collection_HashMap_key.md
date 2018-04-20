# java集合--Map HashMap key不允许重复


- HashMap的key是否允许重复<br>
HashMap中的key是不允许重复的,HashMap中不允许key的hashCode()相同,并且key的equals()相同。

HashMap中put()方法的实现过程是:首先根据hash(key)得到key的hashcode(),hashmap再根据获得hashcode找到插入的位置所在的链,
在这个链里面放的是hashcode相同的Entry键值对,在找个这个链后,会通过equals()方法判断是否已经存在要插入的键值对, 而equals比较的其实就是key。

如下图：
![HashMap内部数据结构图](https://img-blog.csdn.net/20160126094822495)

只有key的hash()方法和equals()都相同的时候, 会认为是同一个key。

1. key为普通Object类型<br>
我们定义个dog对象
```

public class Dog {
    String color;
    Dog(String c){
        color = c;
    }
    public String toString(){
        return color + " dog";
    }
}


```
通过main方法调用
```
import java.util.HashMap;
import java.util.Map;

public class TestHashMap {
    public static void main(String[] args) {
        HashMap<Dog,Integer> hashMap = new HashMap<Dog, Integer>();
        Dog dog1 = new Dog("red");
        Dog dog2 = new Dog("black");
        Dog dog3 = new Dog("white");
        Dog dog4 = new Dog("white");

        hashMap.put(dog1,10);
        hashMap.put(dog2,15);
        hashMap.put(dog3,5);
        hashMap.put(dog4,20);

        //print size
        System.out.println("HashMap size --" + hashMap.size());
        //loop HashMap
        for(Map.Entry<Dog,Integer> entry:hashMap.entrySet()){
            System.out.println(entry.getKey().toString() + " - " + entry.getValue());
        }
    
    }
}

```
输出结果如下
```
"C:\Program Files\Java\jdk1.8.0_121\bin\java" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2017.3.1\lib\idea_rt.jar=65310:C:\Program Files\JetBrains\IntelliJ IDEA 2017.3.1\bin" -Dfile.encoding=UTF-8 -classpath "C:\Program Files\Java\jdk1.8.0_121\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\ext\zipfs.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_121\jre\lib\rt.jar;D:\code\basci_map\target\classes" TestHashMap
HashMap size --4
black dog - 15
white dog - 20
red dog - 10
white dog - 5
```

我们看到虽然给两个Dog对象设置同样的color，当时HashMap并没有覆盖前一个对象.


如果把每个链当做桶，通过Hash()方法找到对应桶, 通过equals()方法找到在桶中的位置,以及覆盖equals值相同的value,
当然如果两个对象的hash()不同但是equals相同, 则key不会被覆盖，因为在不同的桶中，所以我们可以看一下这两个对象的hash()方法返回结果

```
  //计算两个对象的hashcode
        int dog3HashCode = dog3.hashCode();
        int dog4HashCode = dog4.hashCode();
        System.out.println("dog3对象的hashcode值为--"+ dog3HashCode + "--dog4对象的hashcode值为--" + dog4HashCode);
```

输出结果如下:
```
dog3对象的hashcode值为--21685669--dog4对象的hashcode值为--2133927002
```

两个对象的hashcode值不一样,一定不会被认为是重复的key, 并且两个对象的equals方法也是继承自Object的equals(),
Object的equals方法如下，
```
 public boolean equals(Object obj) {
        return (this == obj);
    }
```

怎么让两个dog对象设置为重复key呢?
可以看到两个dog的hash和equals均不相同, 如果我们把Dog类中的hash()方法和equals()方法都进行重写,如下代码:
```

public class Dog {
    String color;
    Dog(String c){
        color = c;
    }
    public boolean equals(Object o){
        return ((Dog)o).color.equals(this.color);
    }
    public int hashCode(){
       return color.length();
    }
    public String toString(){
        return color + " dog";
    }
}


```

返回结果如下:
```
HashMap size --3
red dog - 10
black dog - 15
white dog - 20
dog3对象的hashcode值为--5--dog4对象的hashcode值为--5
```

"=="对于包装类型来说比较的是两个对象的地址，很明显new了两个Dog，虽然color一样，但是在栈中存放着两个引用类型的变量，
并不是同一个地址，所以equals方法返回false，HashMap会认为是两个key。
    
```
 public boolean equals(Object obj) {
        return (this == obj);
    }
```

key为包装类型的对象的时候，key是否重复判断主要还是看key的hashCode() ,和key的equals()方法是否相同。


- summary<br>
HashMap中的key是否允许重复,取决于key的hashCode()方法以及key的equals()方法,所以当key为String类型的时候,
String的equals()重写了Object的equals()方法,所以判断key是否相同与Key为Object类型则不同,当然其他的类型也一样,
例如Integer 和Long,Boolean等







