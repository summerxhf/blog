# vmstat Memory monitoring

vmstate 可以查看cpu、内存、io的使用情况。

如下
```

[root@xhf_cloud git_branch]# vmstat 
procs -----------memory---------- ---swap-- -----io---- --system-- -----cpu-----
 r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st
 0  0 150808 105764  38720 219852    0    1     1     7   71   23  0  0 99  0  0
 
 #每两秒打印一次,共打印5次
 [root@xhf_cloud git_branch]# vmstat 2 5
procs -----------memory---------- ---swap-- -----io---- --system-- -----cpu-----
 r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st
 0  0 150808 107324  38728 219880    0    1     1     7   71   23  0  0 99  0  0
 0  0 150808 108672  38728 219876    0    0     0     0   73  179  1  0 99  0  0
 0  0 150808 107408  38728 219880    0    0     0    18   95  194  1  1 97  0  0
 0  0 150808 107408  38728 219880    0    0     0    14   67  174  1  0 99  0  0
 0  0 150808 107408  38728 219880    0    0     0     0   63  165  0  0 100  0  0
```

参数说明


| procs | memory | swap |  io| system | cpu |
|--- | ---|--- | ---|--- | --- | 
| r: 运行队列中进程数量| swpd:虚拟内存大小 |si:每秒从交换区写到内存大小 |bi:每秒读取的块数 | in:每秒中断数,包括时钟中断 | us:用户进程时间 |
| b: 等待io进程数量| free:可用内存大小 |so:每秒写入交换区内存大小 |bo:每秒写入的块数 | cs:每秒上下文切换数 |sy:系统进程执行时间 |
|        | buff:缓冲内存大小 |        |   |                                                                |id:空间时间 |
|        | cache:缓存内存大小 |       |   |                                                                | wa:等待io时间 |
|        | free:可用内存大小 |


summay:可以动态的看到swap的内存交换情况.

参考:https://www.thomas-krenn.com/en/wiki/Linux_Performance_Measurements_using_vmstat

