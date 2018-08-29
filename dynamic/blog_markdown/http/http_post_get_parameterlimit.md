# http get post parameter limit

今天定时任务项目上报错，报错如下。
```
[ERROR][2018-08-28 23:30:00] [c.k.c.s.AutoChargingTaskService] [定时任务出现异常Server returned HTTP response code: 400 for URL: http://127.0.0.1:7191/xxx/autoCharging]
java.io.IOException: Server returned HTTP response code: 400 for URL: http://127.0.0.1:7191/xxx/autoCharging
        at sun.net.www.protocol.http.HttpURLConnection.getInputStream0(HttpURLConnection.java:1876)
        at sun.net.www.protocol.http.HttpURLConnection.getInputStream(HttpURLConnection.java:1474)
        at com.ksyun.chss.service.AutoChargingTaskService.automaticChargingTask(AutoChargingTaskService.java:72)
        at com.ksyun.chss.service.AutoChargingTaskService$$FastClassBySpringCGLIB$$591036df.invoke(<generated>)
        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)
        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:747)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
        at org.springframework.aop.interceptor.AsyncExecutionInterceptor.lambda$invoke$0(AsyncExecutionInterceptor.java:115)
        at java.util.concurrent.FutureTask.run(FutureTask.java:266)
        at java.lang.Thread.run(Thread.java:745)
```

但是前一天跑的好好的,今天为什么报400的错误呢?定时任务并没有做太多的业务逻辑操作,其中主要是通过http post 方式来调用另外一系统的接口, 
看了一下入参的大小,报错的前一天入参大小大概Total: 2017 条(每条是id,再用逗号拼接). 而报错的参数id总数是Total: 2182,大概计算了一下入参的json格式的
大小,大概是8.1k, 并且发现项目中调用另外系统参数的传递竟然在header中, 在网上查了查, post虽然说再参数上没有限制, 但是post到服务器上的参数大小有限制的。

> header的限制在于服务端, tomcat7.X的默认限制为8k.

- 不同服务端的限制总结<br>
**Apache** 1.3, 2.0, 2.2, 2.3: 8190 Bytes (for each header field)<br>
**IIS**:<br>
4.0: 2097152 Bytes (for the request line plus header fields)<br>
5.0: 131072 Bytes, 16384 Bytes with Windows 2000 Service Pack 4 (for the request line plus header fields)<br>
6.0: 16384 Bytes (for each header fields)<br>
**Tomcat**:<br>
5.5.x/6.0.x: 49152 Bytes (for the request line plus header fields) <br>
7.0.x: 8190 Bytes (for the request line plus header fields)

<br>
所以最好不要把参数传递放到header中, header中主要存放请求类型以及cookies等信息, 而post中的数据最好是存放到body中, 但是对于body中的数据是否也有大小限制呢? 
的确是有的, 也需要配置tomcat.

> post在提交表单的时候,在表单元素数量上没有限制,但是只在所以名称的总大小上(size)上有限制。<br>
而get仅允许1024个字符。post 的数据size限制在2MB到128kb之间，当然这并不适用于文件， enctype='multipart/form-data' ，如果适用IIS5.0上传文件，上传90~100Mb的文件是没有问题的。


tomcat中显示了post请求数据大小为2m, 可能一般情况下是够了,可以配置tomcat server.xml maxPostSize的大小,设置为0, 则没有限制,如下代码.

```
<Connector URIEncoding="utf-8" connectionTimeout="20000" port="8088" protocol="HTTP/1.1" redirectPort="8443" maxPostSize="0"/>

```
- summary<br>
400的错误是参数错误, 405错误是请求限制,404找不到页面, 又重新认识了一下http post, 以及注意尽量不要在header中传参,尤其是请求数据量比较size大的参数.
