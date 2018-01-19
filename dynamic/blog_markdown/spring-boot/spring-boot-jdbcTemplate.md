# spring boot use JdbcTemplate 
jdbcTemplate: spring jdbcTemplate是一款轻量级ORM框架,使用它替换原生的JDBC操作数据库会轻松很多.<br>
本人将介绍在spring boot中使用jdbcTemplate.

- 项目目录
```
├─main                                                                       
│  ├─java                                                                              
│  │  └─com
│  │      └─hf 
│  │          └─jdbc 
│  │                  SimpleController.java
│  │ 
│  └─resources 
│          application.properties
│
└─test
    └─java    
```

- 数据库

```
CREATE TABLE tb_user
(
  id SERIAL PRIMARY KEY NOT NULL,
  username VARCHAR(20) NOT NULL,
  comment VARCHAR(500)
)
```

- pom.xml依赖
```
 <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>1.3.2.RELEASE</version>
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

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
            <version>1.3.2.RELEASE</version>
        </dependency>
    </dependencies>
```

- application.properties
```
spring.datasource.url=jdbc:mysql://localhost:3306/blog_demo
spring.datasource.username=root
spring.datasource.password=**root**
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

- SimpleController.java 
```
package com.hf.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
@EnableAutoConfiguration
public class SimpleController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(){
        jdbcTemplate.update("INSERT INTO tb_user (username,comment) VALUES (?,?)",new Object[]{"username1", "comment1"});
        return "insert success!";
    }
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public String delete(){
        jdbcTemplate.update("DELETE FROM tb_user WHERE id>?",10);
        return "delete success!";
    }
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public String update(){
        jdbcTemplate.update("UPDATE tb_user SET  username=?,comment=? ", new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException{
                ps.setString(1,"update_username");
                ps.setString(2,"update_comment");
            }
        });
        return "update success!";
    }
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public String query(){
        String name = jdbcTemplate.queryForObject("select username from tb_user where id = ?",String.class,1);
        return "username :" + name ;
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleController.class,args);
    }
}

```
- 分别调用对应的方法
添加
```
http://localhost:8080/add
insert success!

#table
1	username1	comment1
2	username1	comment1
3	username1	comment1
4	username1	comment1
5	username1	comment1
6	username1	comment1
7	username1	comment1
8	username1	comment1
9	username1	comment1
10	username1	comment1
11	username1	comment1
12	username1	comment1
13	username1	comment1
14	username1	comment1
15	username1	comment1
```

修改
```
http://localhost:8080/update
update success!


1	update_username	update_comment
2	update_username	update_comment
3	update_username	update_comment
4	update_username	update_comment
5	update_username	update_comment
6	update_username	update_comment
7	update_username	update_comment
8	update_username	update_comment
9	update_username	update_comment
10	update_username	update_comment
11	update_username	update_comment
12	update_username	update_comment
13	update_username	update_comment
14	update_username	update_comment
15	update_username	update_comment
```

删除
```
http://localhost:8080/delete 
delete success!

# 可以看到id大于10的已经被删除了

1	update_username	update_comment
2	update_username	update_comment
3	update_username	update_comment
4	update_username	update_comment
5	update_username	update_comment
6	update_username	update_comment
7	update_username	update_comment
8	update_username	update_comment
9	update_username	update_comment
10	update_username	update_comment

```
查询
```
http://localhost:8080/query

username :update_username
```
-summary<br>
简单粗暴,原理上的慢慢来





