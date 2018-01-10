
# linux cpu and memory and disk knowledge


# <font size="3">cpu 信息查看</font> <br>

---

```
#查看物理CPU个数 
cat /proc/cpuinfo| grep "physical id"| sort| uniq| wc -l

#查看每个物理CPU中core的个数(即核数)
cat /proc/cpuinfo| grep "cpu cores"| uniq

#查看逻辑CPU的个数
cat /proc/cpuinfo| grep "processor"| wc -l

#总核数 = 物理CPU个数 X 每颗物理CPU的核数 
#总逻辑CPU数 = 物理CPU个数 X 每颗物理CPU的核数 X 超线程数

#查看CPU信息（型号）
cat /proc/cpuinfo | grep name | cut -f2 -d: | uniq -c
```

---

# <font size="3">内存信息</font><br>
> 命令格式:free [参数]

free功能:显示系统使用和空闲内存情况,包括物理内存、交互区swap内存、以及被内核使用的buffer。共享内存将被忽略。<br>
命令参数:<br>
free不加参数默认使用kb为单位显示内存.<br>
-b：以byte显示内存情况。<br>
-k：以KB为单位<br>
-m：以m为单位<br>
-g：以GB为单位<br>

-o：不显示缓冲区调节列<br>
-s<间隔秒数>：持续观察内存使用情况(使用kb单位)<br>
-t：显示内存总和列<br>
-V：显示版本信息<br>

如下命令运行结果

```
#默认单位kb
[root@xhf_cloud ~]# free
             total       used       free     shared    buffers     cached
Mem:       1019972     897744     122228        180      13272     183208
-/+ buffers/cache:     701264     318708
Swap:      2097148      57112    2040036

#单位byte
[root@xhf_cloud ~]# free -b
             total       used       free     shared    buffers     cached
Mem:    1044451328  918827008  125624320     180224   14520320  187912192
-/+ buffers/cache:  716394496  328056832
Swap:   2147479552   58482688 2088996864

#单位MB
[root@xhf_cloud ~]# free -m
             total       used       free     shared    buffers     cached
Mem:           996        876        119          0         13        179
-/+ buffers/cache:        683        312
Swap:         2047         55       1992
#多个total
[root@xhf_cloud ~]# free -t
             total       used       free     shared    buffers     cached
Mem:       1019972     896704     123268        176      14444     183572
-/+ buffers/cache:     698688     321284
Swap:      2097148      57112    2040036
Total:     3117120     953816    2163304

#每5s执行一次,单位kb
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
**free结果参数解释**
基本参数 | 含义
---|---
Mem | cpu内存
-/+ buffers/cache |磁盘缓存和缓冲区总大小
Swap | 虚拟内存(实际会IO)


结果参数 | 含义
---|---
total | 总计物理内存大小
used | 已使用多大空间
free | 未使用空间
shared|多个进程共享内存
cached|磁盘缓存大小
buffers|磁盘缓冲区(IO高频率冲击缓和)

可用内存= Mem free+buffer+cached 



1内存什么时候交换?
1.1当小于额定值的时候内存就会交换,查看额定值命令
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

可以看出系统内存1GB swap空间2GB(后来配置的swap,swap下篇说明)


---


# <font size="3">硬盘信息</font>

linux提供了了一下命令检查磁盘空间
- 检查linux文件系统的可用空间<br>
  df(Disk Free)基本格式:df [options] [devices]

```
[root@xhf_cloud ~]# df -h
 
Filesystem      Size  Used Avail Use% Mounted on
/dev/vda1        25G  6.0G   18G  26% /
tmpfs           499M     0  499M   0% /dev/shm
```
  
- 指定文件和文件的每个子目录
du(Disk usage)基本格式: <br>

```
#指定子目录占用空间
[root@xhf_cloud /]# du -sh var
973M    var
[root@xhf_cloud /]# du -sh usr
2.1G    usr

#data目录以及子目录,h查看方式会显示k单位
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

#显示系统中的具体文件(不是子目录)大小
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

#显示文件修改
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


#每个子目录占用空间
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
查看cpu信息、查看内存信息free、查看磁盘信息df -h 和du -h 、  du -ah