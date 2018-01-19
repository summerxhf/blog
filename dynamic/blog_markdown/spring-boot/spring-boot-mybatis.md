# spring boot with mybatis

mybatis��һ������ĳ־ò���, ��֧�ֶ��ƻ�SQL���洢�����Ѿ��߼�ӳ�䡣mybatis�����˼������е�jdbc������ֶ����ò����Լ���ȡ�������mybatis����ʹ�ü򵥵�xml����ע�������ú�ӳ��ԭ����Ϣ�����ӿں�java��POJOs��plain old java object����ͨjava����ӳ������ݿ��еļ�¼��

��ƪ��Ҫ��������spring boot ��mybatis���ʹ�ã�һ���򵥵�demo��

- ������
```
����main
��  ����java
��  ��  ����com
��  ��      ����hf
��  ��          ����---mybatis
��  ��              ��  Application.java
��  ��              ��
��  ��              ����controller
��  ��              ��      UserController.java
��  ��              ��
��  ��              ����mapper
��  ��              ��      UserMapper.java
��  ��              ��
��  ��              ����model
��  ��              ��      User.java
��  ��              ��
��  ��              ����service
��  ��                      UserService.java
��  ��
��  ����resources
��      ��  application.properties
��      ��  log4j.properties
��      ��
��      ����mybatis
��              UserMapper.xml
��
����test
    ����java
```

- DB ��ӱ������
```
CREATE TABLE public.users (
  id INTEGER,
  name CHARACTER VARYING(20),
  age INTEGER,
  password CHARACTER VARYING(10)
);
INSERT INTO users VALUES (1, 'user1', 27, 'password');
```

- pom.xml�ļ�
```
 <properties>
        <spring.boot.version>1.3.2.RELEASE</spring.boot.version>
    </properties>
    <dependencies>
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.2.8</version>
    </dependency>
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis-spring</artifactId>
        <version>1.2.2</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>${spring.boot.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
        <version>${spring.boot.version}</version>
    </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>4.2.4.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>6.0.6</version>
        </dependency>
    </dependencies>
```
- UserMapper.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.hf.mapper.UserMapper">

    <select id="findUserInfo" resultType="com.hf.model.User">
        select name, age,password from users;
    </select>

</mapper>
```

- UserMapper
```
package com.hf.mapper;

import com.hf.model.User;

public interface UserMapper {
    User findUserInfo();
}

```

- UserService
```
package com.hf.service;

import com.hf.mapper.UserMapper;
import com.hf.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User getUserInfo() {
        return userMapper.findUserInfo();
    }
}

```

- User model
```
package com.hf.model;

public class User {
    private String name;
    private Integer age;
    private String password;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

```

- UserController
```
package com.hf.controller;

import com.hf.model.User;
import com.hf.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class UserController {
    private Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public User getUserInfo() {
        User user = userService.getUserInfo();
        if(user!=null){
            System.out.println("user.getName():"+user.getName());
            logger.info("user.getAge():"+user.getAge());
        }
        return user;
    }

}

```
- Application<br>
Լ����������,һ���������spring��Ҫ��xml������SqlSessionFactoryBean,DataSource ��DataSourceTransactionManager . ����������ʹ��@Bean������. @MapperScan��ɨ��ָ��Ŀ¼�Ľӿ�,
DataSource Bean �����properties �ļ��е����ݿ��������, ����Application��������:
```
package com.hf.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@EnableAutoConfiguration
@Configuration
@ComponentScan(value = "com.hf")
@MapperScan("com.hf")
public class Application {
    private static Logger logger = Logger.getLogger(Application.class);

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new org.apache.tomcat.jdbc.pool.DataSource();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        logger.info("SpringBoot Start Success");
    }
}

```

- summay<br>
����~







