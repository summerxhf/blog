# centos 6.9 ��������

- 1 ����˵��<br>
����������˵��:
��һ������Ϊ��������: eth0
�ڶ�������Ϊ��������: eth1��û�������Ļ���ҲҪ�����������ڵڶ��������ϣ�

- 2 �����Զ���ȡIP����
```
1) #vi  /etc/sysconfig/network-scripts/ifcfg-eth0
2)�޸�ONBOOT=yes
3)# service network restart #��������
```

- 3 �������þ�̬IP
```
# vi  /etc/sysconfig/network-scripts/ifcfg-eth0
BOOTPROTO=static   #���þ�̬IP��ַ
ONBOOT=yes  #�����Զ�������������
IPADDR=192.168.21.129  #����IP��ַ
NETMASK=255.255.255.0  #������������
GATEWAY=192.168.21.2   #��������
DNS1=8.8.8.8 #������DNS
DNS2=8.8.4.4 #���ñ�DNS
IPV6INIT=no  #��ֹIPV6
:wq!  #�����˳�
```
```
�޸����ִ����������
# service ip6tables stop   #ֹͣIPV6����
# chkconfig ip6tables off  #��ֹIPV6��������
# service network restart  #������������
# ifconfig  #�鿴IP��ַ
```

