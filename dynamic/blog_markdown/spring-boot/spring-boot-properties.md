# spring boot properties read

spring���Զ�ȡ�����ļ�,spring��ȡ�����ļ��ķ�ʽ���Ӽ���,ʹ��ConfigurationProperties��ǩ�Ϳ���.

1. **����ṹ**<br>
main<br>
----java<br>
--------com.hf.properties<br>
------------DefaultProperties<br>
------------SimpleController<br>
------------SpecialProperties<br>
----resource<br>
---------application.properties<br>
        
2. **pom.xml**��������<br>
```
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>1.3.2.RELEASE</version>
        </dependency>
```

3. **Properties** �ļ�<br>
spring bootĬ�ϼ��ص������ļ�application.properties.
����Ҳ���Գ��˼���Ĭ�������ļ�,���Լ����Լ�����������ļ�.
application.properties
```
server.info.address=192.168.1.1
server.info.username=user1
server.info.password=password
```
�Լ�����������ļ�,server.properties
```
server.info.address=192.168.1.2
server.info.username=user2
server.info.password=password2
```

4. **javaʵ��**<br>
���������ļ��е��������ǽ�����DefaultProperties.java(��Ĭ�������ļ�ƥ��)��SpecialProperties.java(�Ͷ���������ļ�ƥ��)
DefaultProperties.java
```
package com.hf.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix  = "server.info")
public class DefaultProperties {
    //ֻ��Ĭ�ϵ������ļ��ſ���,value��ǩ.
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
ע��:����ʹ����Value��ǩ,����test�з���ò����Ĭ�ϵ������ļ�������,�������Լ�����������ļ���ʵ��,ʹ��get() set()��ʽ.


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

@EnableAutoConfiguration�����ǩ�Ǹ���jar������������������� spring, ����spring-boot-starter-web�����Tomcat��Spring MVC ,����auto-configuration���ٶ������ڿ���һ��web����Ӧ�Ķ�spring��������.

@ComponentScan ��ʿָ��ɨ��basePackage��·��.

���Կ���������main������ʹ����SpringApplication,����SpringApplication��,����ͨ������http://localhost:8080/properties ���鿴��ȡ�����ļ��Ľ��.
�������:

```
DefaultProperties{address=192.168.1.1,username=user1,password=password}
SpecialProperties{address=192.168.1.2,username=user2,password=password2}
```
**summary**:<br>
����������,�о��Լ��������ʱ��,Ҳ�����Ⲣû����������ô����,��Ҫ�Ŵ��Լ���weakness,����