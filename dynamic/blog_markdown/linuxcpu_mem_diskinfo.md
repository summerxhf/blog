
# linux cpu and memory and disk knowledge


# <font size="3">cpu ��Ϣ�鿴</font> <br>

---

```
#�鿴����CPU���� 
cat /proc/cpuinfo| grep "physical id"| sort| uniq| wc -l

#�鿴ÿ������CPU��core�ĸ���(������)
cat /proc/cpuinfo| grep "cpu cores"| uniq

#�鿴�߼�CPU�ĸ���
cat /proc/cpuinfo| grep "processor"| wc -l

#�ܺ��� = ����CPU���� X ÿ������CPU�ĺ��� 
#���߼�CPU�� = ����CPU���� X ÿ������CPU�ĺ��� X ���߳���

#�鿴CPU��Ϣ���ͺţ�
cat /proc/cpuinfo | grep name | cut -f2 -d: | uniq -c
```

---

# <font size="3">�ڴ���Ϣ</font><br>
> �����ʽ:free [����]

free����:��ʾϵͳʹ�úͿ����ڴ����,���������ڴ桢������swap�ڴ桢�Լ����ں�ʹ�õ�buffer�������ڴ潫�����ԡ�<br>
�������:<br>
free���Ӳ���Ĭ��ʹ��kbΪ��λ��ʾ�ڴ�.<br>
-b����byte��ʾ�ڴ������<br>
-k����KBΪ��λ<br>
-m����mΪ��λ<br>
-g����GBΪ��λ<br>

-o������ʾ������������<br>
-s<�������>�������۲��ڴ�ʹ�����(ʹ��kb��λ)<br>
-t����ʾ�ڴ��ܺ���<br>
-V����ʾ�汾��Ϣ<br>

�����������н��

```
#Ĭ�ϵ�λkb
[root@xhf_cloud ~]# free
             total       used       free     shared    buffers     cached
Mem:       1019972     897744     122228        180      13272     183208
-/+ buffers/cache:     701264     318708
Swap:      2097148      57112    2040036

#��λbyte
[root@xhf_cloud ~]# free -b
             total       used       free     shared    buffers     cached
Mem:    1044451328  918827008  125624320     180224   14520320  187912192
-/+ buffers/cache:  716394496  328056832
Swap:   2147479552   58482688 2088996864

#��λMB
[root@xhf_cloud ~]# free -m
             total       used       free     shared    buffers     cached
Mem:           996        876        119          0         13        179
-/+ buffers/cache:        683        312
Swap:         2047         55       1992
#���total
[root@xhf_cloud ~]# free -t
             total       used       free     shared    buffers     cached
Mem:       1019972     896704     123268        176      14444     183572
-/+ buffers/cache:     698688     321284
Swap:      2097148      57112    2040036
Total:     3117120     953816    2163304

#ÿ5sִ��һ��,��λkb
[root@xhf_cloud ~]# free -s5
             total       used       free     shared    buffers     cached
Mem:       1019972     897432     122540        180      14268     183532
-/+ buffers/cache:     699632     320340
Swap:      2097148      57112    2040036

             total       used       free     shared    buffers     cached
Mem:       1019972     894912     125060        172      14272     183524
-/+ buffers/cache:     697116     322856
Swap:      2097148      57112    2040036

```
**free�����������**
�������� | ����
---|---
Mem | cpu�ڴ�
-/+ buffers/cache |���̻���ͻ������ܴ�С
Swap | �����ڴ�(ʵ�ʻ�IO)


������� | ����
---|---
total | �ܼ������ڴ��С
used | ��ʹ�ö��ռ�
free | δʹ�ÿռ�
shared|������̹����ڴ�
cached|���̻����С
buffers|���̻�����(IO��Ƶ�ʳ������)

�����ڴ�= Mem free+buffer+cached 



1�ڴ�ʲôʱ�򽻻�?
1.1��С�ڶֵ��ʱ���ڴ�ͻύ��,�鿴�ֵ����
```
#cat /proc/meminfo 
[root@xhf_cloud ~]# cat /proc/meminfo
MemTotal:        1019972 kB
MemFree:          122124 kB
Buffers:           29476 kB
Cached:           156632 kB
SwapCached:         4836 kB
Active:           411376 kB
Inactive:         427260 kB
Active(anon):     292640 kB
Inactive(anon):   359912 kB
Active(file):     118736 kB
Inactive(file):    67348 kB
Unevictable:           0 kB
Mlocked:               0 kB
SwapTotal:       2097148 kB
SwapFree:        2010380 kB
Dirty:                28 kB
Writeback:             0 kB
AnonPages:        648064 kB
Mapped:            25620 kB
Shmem:                20 kB
Slab:              36064 kB
SReclaimable:      15816 kB
SUnreclaim:        20248 kB
KernelStack:        3072 kB
PageTables:         4760 kB
NFS_Unstable:          0 kB
Bounce:                0 kB
WritebackTmp:          0 kB
CommitLimit:     2607132 kB
Committed_AS:    1058316 kB
VmallocTotal:   34359738367 kB
VmallocUsed:        7588 kB
VmallocChunk:   34359726756 kB
HardwareCorrupted:     0 kB
AnonHugePages:    126976 kB
HugePages_Total:       0
HugePages_Free:        0
HugePages_Rsvd:        0
HugePages_Surp:        0
Hugepagesize:       2048 kB
DirectMap4k:        8048 kB
DirectMap2M:     1040384 kB

```

���Կ���ϵͳ�ڴ�1GB swap�ռ�2GB(�������õ�swap,swap��ƪ˵��)


---


# <font size="3">Ӳ����Ϣ</font>

linux�ṩ����һ����������̿ռ�
- ���linux�ļ�ϵͳ�Ŀ��ÿռ�<br>
  df(Disk Free)������ʽ:df [options] [devices]

```
[root@xhf_cloud ~]# df -h
 
Filesystem      Size  Used Avail Use% Mounted on
/dev/vda1        25G  6.0G   18G  26% /
tmpfs           499M     0  499M   0% /dev/shm
```
  
- ָ���ļ����ļ���ÿ����Ŀ¼
du(Disk usage)������ʽ: <br>

```
#ָ����Ŀ¼ռ�ÿռ�
[root@xhf_cloud /]# du -sh var
973M    var
[root@xhf_cloud /]# du -sh usr
2.1G    usr

#dataĿ¼�Լ���Ŀ¼,h�鿴��ʽ����ʾk��λ
[root@xhf_cloud /]# du -h /data/
28K     /data/zookeeper/3/version-2
48K     /data/zookeeper/3/logs/version-2
56K     /data/zookeeper/3/logs
100K    /data/zookeeper/3
28K     /data/zookeeper/2/version-2
48K     /data/zookeeper/2/logs/version-2
56K     /data/zookeeper/2/logs
96K     /data/zookeeper/2
16K     /data/zookeeper/1/version-2
48K     /data/zookeeper/1/logs/version-2
56K     /data/zookeeper/1/logs
84K     /data/zookeeper/1
284K    /data/zookeeper
4.0K    /data/mysql
292K    /data/

#��ʾϵͳ�еľ����ļ�(������Ŀ¼)��С
[root@xhf_cloud conf]# du -ah /usr/local/tomcat/apache-tomcat-7.0.82_1/conf/
8.0K    /usr/local/tomcat/apache-tomcat-7.0.82_1/conf/catalina.properties
4.0K    /usr/local/tomcat/apache-tomcat-7.0.82_1/conf/Catalina/www.codingfuns.com
4.0K    /usr/local/tomcat/apache-tomcat-7.0.82_1/conf/Catalina/localhost
12K     /usr/local/tomcat/apache-tomcat-7.0.82_1/conf/Catalina
4.0K    /usr/local/tomcat/apache-tomcat-7.0.82_1/conf/context.xml
4.0K    /usr/local/tomcat/apache-tomcat-7.0.82_1/conf/logging.properties
168K    /usr/local/tomcat/apache-tomcat-7.0.82_1/conf/web.xml
8.0K    /usr/local/tomcat/apache-tomcat-7.0.82_1/conf/server.xml
16K     /usr/local/tomcat/apache-tomcat-7.0.82_1/conf/catalina.policy
4.0K    /usr/local/tomcat/apache-tomcat-7.0.82_1/conf/tomcat-users.xml
228K    /usr/local/tomcat/apache-tomcat-7.0.82_1/conf/

#��ʾ�ļ��޸�
[rd@dev.tomcat.bj6.01 home]$du -ah --time /home/dev/tomcat/conf/
12K     2017-03-10 15:50        /home/dev/tomcat/conf/catalina.policy
4.0K    2017-03-10 15:50        /home/dev/tomcat/conf/catalina.properties
4.0K    2017-03-10 15:50        /home/dev/tomcat/conf/logging.properties
4.0K    2017-06-06 17:17        /home/dev/tomcat/conf/context.xml
8.0K    2017-05-02 15:36        /home/dev/tomcat/conf/server.xml
164K    2017-03-10 15:50        /home/dev/tomcat/conf/web.xml
4.0K    2017-03-10 15:50        /home/dev/tomcat/conf/Catalina/localhost
8.0K    2017-03-10 15:50        /home/dev/tomcat/conf/Catalina
4.0K    2017-03-10 15:50        /home/dev/tomcat/conf/tomcat-users.xml
212K    2017-07-17 20:57        /home/dev/tomcat/conf/


#ÿ����Ŀ¼ռ�ÿռ�
[root@xhf_cloud /]# du -sh *
6.9M    bin
51M     boot
292K    data
160K    dev
7.6M    etc
68K     home
291M    lib
20M     lib64
16K     lost+found
4.0K    media
4.0K    mnt
581M    opt
0       proc
46M     root
11M     sbin
4.0K    selinux
4.0K    srv
2.1G    swapfile
0       sys
8.0K    system
4.0K    temp
2.0M    tmp
2.1G    usr
973M    var
```

- summary<br>
�鿴cpu��Ϣ���鿴�ڴ���Ϣfree���鿴������Ϣdf -h ��du -h ��  du -ah