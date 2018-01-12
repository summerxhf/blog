# High CPU trouble shoot


- 什么会引起cup飙高？
1. 程序设计的糟糕，效率低下。(eg死锁 死循环 算法效率低等)
2. io较频繁(大量写入磁盘或者读出磁盘)
3. swap空间交换频繁（本质和上也属于io，但可能是内存不足够原因）
4. jdk 频繁gc
5. 以其他情况而定


- 解决思路
1. 通过工具top查看系统进程运行情况
2. 查看进程的线程运行情况,top -H pid
3. jvisualvm 工具进行thread dump

- linux top use

linx的top命令类似于windows的资源管理器
如下

```
[root@xhf_cloud git_branch]# top
top - 15:42:11 up  4:36,  1 user,  load average: 0.00, 0.00, 0.00
Tasks:  89 total,   1 running,  88 sleeping,   0 stopped,   0 zombie
Cpu(s):  0.7%us,  0.7%sy,  0.0%ni, 98.7%id,  0.0%wa,  0.0%hi,  0.0%si,  0.0%st
Mem:   1019972k total,   768476k used,   251496k free,     9004k buffers
Swap:  2097148k total,    89796k used,  2007352k free,   161540k cached

  PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND                                                             
 5031 root      20   0 98.4m 3872 2924 S  0.7  0.4   0:00.02 sshd                                                                 
 4197 root      20   0 2283m 253m  13m S  0.3 25.5   0:45.27 java                                                                 
    1 root      20   0 19356 1116  872 S  0.0  0.1   0:01.38 init                                                                 
    2 root      20   0     0    0    0 S  0.0  0.0   0:00.00 kthreadd                                                             
    3 root      RT   0     0    0    0 S  0.0  0.0   0:00.00 migration/0                                                          
    4 root      20   0     0    0    0 S  0.0  0.0   0:00.07 ksoftirqd/0                                                          
    5 root      RT   0     0    0    0 S  0.0  0.0   0:00.00 stopper/0                                                            
    6 root      RT   0     0    0    0 S  0.0  0.0   0:00.05 watchdog/0                                                           
    7 root      20   0     0    0    0 S  0.0  0.0   0:02.55 events/0                                                             
    8 root      20   0     0    0    0 S  0.0  0.0   0:00.00 events/0                                                             
    9 root      20   0     0    0    0 S  0.0  0.0   0:00.00 events_long/0                                                        
   10 root      20   0     0    0    0 S  0.0  0.0   0:00.00 events_power_ef                                                      
   11 root      20   0     0    0    0 S  0.0  0.0   0:00.00 cgroup                                                               
   12 root      20   0     0    0    0 S  0.0  0.0   0:00.00 khelper  
```
top 后再按1  查看逻辑cpu每个cup执行情况,无奈我的只是单核的所以没办法看出变化.
```
[root@xhf_cloud git_branch]# top
top - 15:43:17 up  4:37,  1 user,  load average: 0.00, 0.00, 0.00
Tasks:  87 total,   1 running,  86 sleeping,   0 stopped,   0 zombie
Cpu0  :  1.4%us,  0.3%sy,  0.0%ni, 98.2%id,  0.0%wa,  0.0%hi,  0.0%si,  0.0%st
Mem:   1019972k total,   767460k used,   252512k free,     9100k buffers
Swap:  2097148k total,    89796k used,  2007352k free,   161548k cached

  PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND                                                             
 4197 root      20   0 2283m 253m  13m S  0.6 25.5   0:45.43 java                                                                 
    1 root      20   0 19356 1116  872 S  0.0  0.1   0:01.38 init                                                                 
    2 root      20   0     0    0    0 S  0.0  0.0   0:00.00 kthreadd                                                             
    3 root      RT   0     0    0    0 S  0.0  0.0   0:00.00 migration/0                                                          
    4 root      20   0     0    0    0 S  0.0  0.0   0:00.07 ksoftirqd/0
```

每行代表的意思,第一行

| 第一行参数| 意义 |
| ---|---
| 15:43:17 |系统时间 |
| 4:37 | 系统开机到现在经过了多长时间 |
| 1 user | 当前1个用户在线 |
| lovad average | 系统1分钟,5分钟,15分钟负载信息|
第二行
| 第二行参数 | 意义 |
| ---|---
| Tasks |系统时间 |
| 87total | 87个进程 |
| 1 running | 当前一个进程在运行 |
| 86 sleeping | 系统1分钟,5分钟,15分钟负载信息|
| 0 stopped | 停止的进程数 |
| 0 zombie | 僵尸进程 |
第三行
| 第三行参数 | 意义 |
| ---|---
| Cpu0/cpus |单个cpu信息/cpu总信息 |
| 1.4%us | 用户进程占cpu百分比 |
| 0.3%sy | 内核占用百分比 |
| 0.0%ni | 改变优先级的进程占cpu百分比|
| 98.2%id | 空闲cpu百分比 |
| 0.0%wa | 等待IOcpu百分比 |
| 0.0%hi | cpu硬中断百分比 |
| 0.0%si | cpu软中断百分比 |
| 0.0%st | cpu非自愿等待百分比 |
第四和第五行
```
PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND                                                             
 4197 root      20   0 2283m 253m  13m S  0.6 25.5   0:45.43 java
```
| 参数 | 意义 |
| ---|---
| Men |内存 |
| swap | swap空间 |
进程信息
 PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND 
 | 参数 | 意义 |
| ---|---
| PID |进程ID |
| USER | 进程所有者 |
| PR | 进程优先级,越小越优先 |
| NI | 值 |
| VIRT | 进程占用虚拟内存 |
| RES | 进程占用物理内存 |
| SHR | 进程使用共享内存 |
| S | 进程状态:s休眠 r运行 z僵尸 N优先级为负数 |
| %CPU | 进程cpu使用率 |
| SHR | 进程使用共享内存 |
| %MEM | 使用物理内存总内存百分比 |
| TIME+ | 进程启动后占用cpu时间 |
| COMMAND | 进程启动名称 |


从网上找了一个多核cpu的案例
```
[rdtfr@bl685cb4-t ^]$ top
top - 09:10:44 up 20 days, 16:51,  4 users,  load average: 3.82, 4.40, 4.40
Tasks: 1201 total,  10 running, 1189 sleeping,   0 stopped,   2 zombie
Cpu0  :  1.3%us,  2.3%sy,  0.0%ni, 96.4%id,  0.0%wa,  0.0%hi,  0.0%si,  0.0%st
Cpu1  :  1.3%us,  2.6%sy,  0.0%ni, 96.1%id,  0.0%wa,  0.0%hi,  0.0%si,  0.0%st
Cpu2  :  1.0%us,  2.0%sy,  0.0%ni, 92.5%id,  0.0%wa,  0.0%hi,  4.6%si,  0.0%st
Cpu3  :  3.9%us,  7.8%sy,  0.0%ni, 83.2%id,  0.0%wa,  0.0%hi,  5.2%si,  0.0%st
Cpu4  :  4.2%us, 10.4%sy,  0.0%ni, 63.8%id,  0.0%wa,  0.0%hi, 21.5%si,  0.0%st
Cpu5  :  6.8%us, 12.7%sy,  0.0%ni, 80.5%id,  0.0%wa,  0.0%hi,  0.0%si,  0.0%st
Cpu6  :  2.9%us,  7.2%sy,  0.0%ni, 85.3%id,  0.0%wa,  0.0%hi,  4.6%si,  0.0%st
Cpu7  :  6.2%us, 13.0%sy,  0.0%ni, 75.3%id,  0.0%wa,  0.0%hi,  5.5%si,  0.0%st
Mem:  32943888k total, 32834216k used,   109672k free,   642704k buffers
Swap: 35651576k total,  5761928k used, 29889648k free, 16611500k cached

```
从中可以看到,负载项,说明7核cpu用到了4个多,平均负载最多为7

top 命令后shift +H 切换按照**进程**方式展示还是按照**线程**方式展示

通过top找到负载高的进程,从而展示该进程的线程运行情况,如下命令
top -H pid 
```
[root@xhf_cloud git_branch]# top -H -p  4197
top - 16:16:39 up  5:10,  1 user,  load average: 0.00, 0.00, 0.00
Tasks:  41 total,   0 running,  41 sleeping,   0 stopped,   0 zombie
Cpu(s):  0.3%us,  0.3%sy,  0.0%ni, 99.0%id,  0.0%wa,  0.0%hi,  0.0%si,  0.3%st
Mem:   1019972k total,   773808k used,   246164k free,    11676k buffers
Swap:  2097148k total,    89792k used,  2007356k free,   163528k cached

  PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND                                                             
 4197 root      20   0 2283m 253m  13m S  0.0 25.5   0:00.00 java                                                                 
 4198 root      20   0 2283m 253m  13m S  0.0 25.5   0:01.35 java                                                                 
 4199 root      20   0 2283m 253m  13m S  0.0 25.5   0:01.94 java                                                                 
 4200 root      20   0 2283m 253m  13m S  0.0 25.5   0:00.02 java                                                                 
```

- jmap 生成dump文件

```
[root@xhf_cloud /]# jmap -dump:file=DumpFile_tomcat.txt 4197 
Dumping heap to /DumpFile_tomcat.txt ...
Heap dump file created
```

可以看到文件:
```
-rw-------    1 root root   76316900 Jan 21 03:13 DumpFile_tomcat.txt
```

- jvisualvm 可视化界面查看dump
  选择文件-->装入-->选择我们下载下来的DumpFile_tomcat.txt文件,可以看到基本信息
```
    文件大小: 72.8 MB

    字节总数: 64,171,715
    类总数: 12,744
    实例总数: 722,129
    类加载器: 99
    垃圾回收根节点: 3,300
    等待结束的暂挂对象数: 0d
    .....以及环境和系统属性
    上
    可以看到类的实例数
    
    
    
```

- jhat查看

```
[root@xhf_cloud /]# jhat DumpFile_tomcat.txt 
Reading from DumpFile_tomcat.txt...
Dump file created Thu Jan 11 03:13:18 UTC 2018
Snapshot read, resolving...
Resolving 734873 objects...
Chasing references, expect 146 dots..................................................................................................................................................
Eliminating duplicate references..................................................................................................................................................
Snapshot resolved.
Started HTTP server on port 7000
Server is ready.

```
可以浏览:http://www.codingfuns.com:7000/ 查看堆情况.


- summay

Learning needs to be repeated.




