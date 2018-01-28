# centos 6.9 网络配置

- 1 网卡说明<br>
两块网卡的说明:
第一块网卡为配置外网: eth0
第二块网卡为配置内网: eth1（没有外网的机器也要将内网配置在第二块网卡上）

- 2 网卡自动获取IP配置
```
1) #vi  /etc/sysconfig/network-scripts/ifcfg-eth0
2)修改ONBOOT=yes
3)# service network restart #重启网卡
```

- 3 网卡配置静态IP
```
# vi  /etc/sysconfig/network-scripts/ifcfg-eth0
BOOTPROTO=static   #启用静态IP地址
ONBOOT=yes  #开启自动启用网络连接
IPADDR=192.168.21.129  #设置IP地址
NETMASK=255.255.255.0  #设置子网掩码
GATEWAY=192.168.21.2   #设置网关
DNS1=8.8.8.8 #设置主DNS
DNS2=8.8.4.4 #设置备DNS
IPV6INIT=no  #禁止IPV6
:wq!  #保存退出
```
```
修改完后执行以下命令
# service ip6tables stop   #停止IPV6服务
# chkconfig ip6tables off  #禁止IPV6开机启动
# service network restart  #重启网络连接
# ifconfig  #查看IP地址
```

