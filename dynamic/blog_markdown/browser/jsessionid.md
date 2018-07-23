# JESSIONID 怎么生成的?

    JSESSIONID cookie is created/sent when session is created. 
Session is created when your code calls request.getSession() or request.getSession(true) for the first time. 
If you just want get session, but not create it if it doesn't exists, use request.getSession(false) -- this will return you a session or null. 
In this case, new session is not created, and JSESSIONID cookie is not sent.
 (This also means that session isn't necessarily created on first request... you and your code is in control when the session is created)


系统中获取当前用户的信息的时候, 采用的是获取cookie中的jsessionId，最初以为jessionId是客户端写入的，后来发现
jessionid是服务端调用request.getSession()的同时, 会发送给客户端, 客户端的cookie中会有jessionid字段。如下如所示:<br>

![图片来源于本网站]( https://uploads.disquscdn.com/images/ea0fafd8f1312c7c2fbbc5e4e25e17ba20c5e09ebc30c55e225d196eb1146d8e.png )


写入原理,如下图所示<br>


![图片来源于网络](https://img-blog.csdn.net/20160519223653910)