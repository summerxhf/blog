# spring boot change port
������main������ʹ��SpringApplication.run(ResourcesController.class,args),Ĭ�ϵ�tomcat�Ķ˿ں���8080,���ǿ����޸�
Ĭ�ϵĶ˿ں�,���ַ�ʽ����<br>
**1.�޸�application.properties�����**
```
=8090
```

**2.���vm����**
```
-Dserver.port=8090
```

������������������,[�������](https://docs.spring.io/spring-boot/docs/1.3.3.RELEASE/reference/html/common-application-properties.html)
