# centos6 install tomcat

centos6 ��װtomcat,�����ÿ�������,������ʹ�Ƿ���������,����Ҳ�����ֶ�����linux��.

```
wget http://mirror.cogentco.com/pub/apache/tomcat/tomcat-8/v8.5.27/bin/apache-tomcat-8.5.27.tar.gz

tar xzf  apache-tomcat-8.5.27.tar.gz
```

����tomcat��������
����Tomcat�Ŀ�������������/etc/rc.d/rc.local�ĵײ�����������ݡ�


```
touch /var/lock/subsys/local
export JAVA_HOME=/opt/jdk1.8.0_161
export RE_HOME=/opt/jdk1.8.0_161/jre
/opt/apache-tomcat-8.5.27/bin/startup.sh
```

- summary<br>
linuxϵ��,�����´��Լ��ٰ�װ������ʱ��Ϳ��Կ��Լ��Ĳ�����, �ظ��ظ��ͺ�.

