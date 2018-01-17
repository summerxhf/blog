# spring boot properties read

spring可以读取配置文件,spring读取配置文件的方式更加简单了,使用ConfigurationProperties标签就可以.

1. **程序结构**<br>
main<br>
----java<br>
--------com.hf.properties<br>
------------DefaultProperties<br>
------------SimpleController<br>
------------SpecialProperties<br>
----resource<br>
---------application.properties<br>
        
2. **pom.xml**依赖如下<br>
```
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>1.3.2.RELEASE</version>
        </dependency>
```

3. **Properties** 文件<br>
spring boot默认加载的配置文件application.properties.
我们也可以出了加载默认配置文件,可以加载自己额外的配置文件.
application.properties
```
server.info.address=192.168.1.1
server.info.username=user1
server.info.password=password
```
自己额外的配置文件,server.properties
```
server.info.address=192.168.1.2
server.info.username=user2
server.info.password=password2
```

4. **java实体**<br>
加载配置文件中的属性我们建立了DefaultProperties.java(和默认配置文件匹配)和SpecialProperties.java(和额外的配置文件匹配)
DefaultProperties.java
```
package com.hf.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix  = "server.info")
public class DefaultProperties {
    //只有默认的配置文件才可以,value标签.
    @Value("${server.info.address}")
    private String address;
    @Value("${server.info.username}")
    private String username;
    @Value("${server.info.password}")
    private String password;

    public String toString(){
        return "DefaultProperties{" +
                "address=" + address + ","
                +"username=" + username +"," +
                "password=" + password + "}";

    }
}

```
SpecialProperties.java
```
package com.hf.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Configuration
@ConfigurationProperties(locations = "classpath:server.properties",prefix = "server.info")
public class SpecialProperties {

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String address;
    private String username;
    private String password;
    public String toString(){
        return "SpecialProperties{" +
                "address=" + address + ","
                +"username=" + username +"," +
                "password=" + password + "}";

    }
}

```
注意:这里使用了Value标签,但是test中发现貌似在默认的配置文件起作用,所以在自己定义的配置文件的实体,使用get() set()方式.


5. **java controller**<br>
```
package com.hf.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@ComponentScan(value = "com.hf.properties")
public class SimpleController {
    @Autowired
    private DefaultProperties defaultProperties;
    @Autowired
    private SpecialProperties specialProperties;

    @RequestMapping(value = "/properties",method = RequestMethod.GET)
    public String getProperties(){
        return defaultProperties.toString() + "<br>" + specialProperties.toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleController.class,args);
    }
}

```

@EnableAutoConfiguration这个标签是根据jar依赖来猜想你如何配置 spring, 由于spring-boot-starter-web添加了Tomcat和Spring MVC ,所以auto-configuration将假定你正在开发一个web并相应的对spring进行设置.

@ComponentScan 隐士指定扫描basePackage的路径.

可以看到我们在main方法中使用了SpringApplication,启动SpringApplication后,可以通过访问http://localhost:8080/properties 来查看读取配置文件的结果.
结果如下:

```
DefaultProperties{address=192.168.1.1,username=user1,password=password}
SpecialProperties{address=192.168.1.2,username=user2,password=password2}
```
**summary**:<br>
不急功近利,感觉自己有问题的时候,也许问题并没有想象中那么严重,不要放大自己的weakness,加油