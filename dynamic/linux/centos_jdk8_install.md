# centos6 jdk8 install

下载最新的jdk,可以从页面中http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html获取下载链接.
Download latest Java SE Development Kit 8 release from its official download page or use following commands to download from shell.


```
# cd /opt/
# wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u161-b12/2f38c3b165be4555a1fa6e98c45e0808/jdk-8u161-linux-x64.tar.gz"
# tar xzf jdk-8u161-linux-x64.tar.gz

[root@xhf ~]# cd /opt/
[root@xhf opt]# ll
total 0
[root@xhf opt]# wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u161-b12/2f38c3b165be4555a1fa6e98c45e0808/jdk-8u161-linux-x64.tar.gz"

[root@xhf opt]# tar xzf jdk-8u161-linux-x64.tar.gz


```


```

[root@xhf jdk1.8.0_161]# alternatives --config java
[root@xhf jdk1.8.0_161]# alternatives --install /usr/bin/java java /opt/jdk1.8.0_161/bin/java 2

[root@xhf opt]# alternatives --config java

There is 1 program that provides 'java'.

  Selection    Command
-----------------------------------------------
*+ 1           /opt/jdk1.8.0_161/bin/java

Enter to keep the current selection[+], or type selection number: 1

```
上述步骤已经安装完毕, 接下来设置javac路径

```
[root@xhf opt]# alternatives --install /usr/bin/jar jar /opt/jdk1.8.0_161/bin/jar 2
[root@xhf opt]# alternatives --install /usr/bin/javac javac /opt/jdk1.8.0_161/bin/javac 2
[root@xhf opt]# alternatives --set jar /opt/jdk1.8.0_161/bin/jar
[root@xhf opt]# alternatives --set javac /opt/jdk1.8.0_161/bin/javac
```
查看安装的jdk版本
```
[root@xhf opt]# java -version
java version "1.8.0_161"
Java(TM) SE Runtime Environment (build 1.8.0_161-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.161-b12, mixed mode)
```

设置java环境变量
Setup JAVA_HOME, JRE_HOME and PATH environment variables

```
[root@xhf opt]# export JAVA_HOME=/opt/jdk1.8.0_161
[root@xhf opt]# export JRE_HOME=/opt/jdk1.8.0_161/jre
[root@xhf opt]# export PATH=$PATH:/opt/jdk1.8.0_161/bin:/opt/jdk1.8.0_161/jre/bin
```

开机启动设置(环境变量)
Also put all above environment variables in /etc/environment file for auto loading on system boot.

```
[root@xhf opt]# cat /etc/environment
export JAVA_HOME=/opt/jdk1.8.0_161
export JRE_HOME=/opt/jdk1.8.0_161/jre
export PATH=$PATH:/opt/jdk1.8.0_161/bin:/opt/jdk1.8.0_161/jre/bin
```

参考:https://tecadmin.net/install-java-8-on-centos-rhel-and-fedora/#
