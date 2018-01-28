# centos6 install tomcat

centos6 安装tomcat,并设置开机启动,这样即使是服务器重启,我们也不用手动重启linux了.

```
wget http://mirror.cogentco.com/pub/apache/tomcat/tomcat-8/v8.5.27/bin/apache-tomcat-8.5.27.tar.gz

tar xzf  apache-tomcat-8.5.27.tar.gz
```

设置tomcat开机启动
设置Tomcat的开机自启动，在/etc/rc.d/rc.local的底部添加下面内容。


```
touch /var/lock/subsys/local
export JAVA_HOME=/opt/jdk1.8.0_161
export RE_HOME=/opt/jdk1.8.0_161/jre
/opt/apache-tomcat-8.5.27/bin/startup.sh
```

- summary<br>
linux系列,这样下次自己再安装环境的时候就可以看自己的博客了, 重复重复就好.

