# spring boot change port
我们在main方法中使用
**1.修改application.properties中添加**
```
=8090
```

**2.添加vm参数**
```
-Dserver.port=8090
```

可以配置其他的设置,[点击这里](https://docs.spring.io/spring-boot/docs/1.3.3.RELEASE/reference/html/common-application-properties.html)
