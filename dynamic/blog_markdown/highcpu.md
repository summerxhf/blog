# High CPU trouble shoot


- ʲô������cup쭸ߣ�
1. ������Ƶ���⣬Ч�ʵ��¡�(eg���� ��ѭ�� �㷨Ч�ʵ͵�)
2. io��Ƶ��(����д����̻��߶�������)
3. swap�ռ佻��Ƶ�������ʺ���Ҳ����io�����������ڴ治�㹻ԭ��
4. jdk Ƶ��gc
5. �������������


- ���˼·
1. ͨ������top�鿴ϵͳ�����������
2. �鿴���̵��߳��������,top -H pid
3. jvisualvm ���߽���thread dump

- linux top use

linx��top����������windows����Դ������
����

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
top ���ٰ�1  �鿴�߼�cpuÿ��cupִ�����,�����ҵ�ֻ�ǵ��˵�����û�취�����仯.
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

ÿ�д������˼,��һ��

| ��һ�в���| ���� |
| ---|---
| 15:43:17 |ϵͳʱ�� |
| 4:37 | ϵͳ���������ھ����˶೤ʱ�� |
| 1 user | ��ǰ1���û����� |
| lovad average | ϵͳ1����,5����,15���Ӹ�����Ϣ|
�ڶ���
| �ڶ��в��� | ���� |
| ---|---
| Tasks |ϵͳʱ�� |
| 87total | 87������ |
| 1 running | ��ǰһ������������ |
| 86 sleeping | ϵͳ1����,5����,15���Ӹ�����Ϣ|
| 0 stopped | ֹͣ�Ľ����� |
| 0 zombie | ��ʬ���� |
������
| �����в��� | ���� |
| ---|---
| Cpu0/cpus |����cpu��Ϣ/cpu����Ϣ |
| 1.4%us | �û�����ռcpu�ٷֱ� |
| 0.3%sy | �ں�ռ�ðٷֱ� |
| 0.0%ni | �ı����ȼ��Ľ���ռcpu�ٷֱ�|
| 98.2%id | ����cpu�ٷֱ� |
| 0.0%wa | �ȴ�IOcpu�ٷֱ� |
| 0.0%hi | cpuӲ�жϰٷֱ� |
| 0.0%si | cpu���жϰٷֱ� |
| 0.0%st | cpu����Ը�ȴ��ٷֱ� |
���ĺ͵�����
```
PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND                                                             
 4197 root      20   0 2283m 253m  13m S  0.6 25.5   0:45.43 java
```
| ���� | ���� |
| ---|---
| Men |�ڴ� |
| swap | swap�ռ� |
������Ϣ
 PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND 
 | ���� | ���� |
| ---|---
| PID |����ID |
| USER | ���������� |
| PR | �������ȼ�,ԽСԽ���� |
| NI | ֵ |
| VIRT | ����ռ�������ڴ� |
| RES | ����ռ�������ڴ� |
| SHR | ����ʹ�ù����ڴ� |
| S | ����״̬:s���� r���� z��ʬ N���ȼ�Ϊ���� |
| %CPU | ����cpuʹ���� |
| SHR | ����ʹ�ù����ڴ� |
| %MEM | ʹ�������ڴ����ڴ�ٷֱ� |
| TIME+ | ����������ռ��cpuʱ�� |
| COMMAND | ������������ |


����������һ�����cpu�İ���
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
���п��Կ���,������,˵��7��cpu�õ���4����,ƽ���������Ϊ7

top �����shift +H �л�����**����**��ʽչʾ���ǰ���**�߳�**��ʽչʾ

ͨ��top�ҵ����ظߵĽ���,�Ӷ�չʾ�ý��̵��߳��������,��������
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

- jmap ����dump�ļ�

```
[root@xhf_cloud /]# jmap -dump:file=DumpFile_tomcat.txt 4197 
Dumping heap to /DumpFile_tomcat.txt ...
Heap dump file created
```

���Կ����ļ�:
```
-rw-------    1 root root   76316900 Jan 21 03:13 DumpFile_tomcat.txt
```

- jvisualvm ���ӻ�����鿴dump
  ѡ���ļ�-->װ��-->ѡ����������������DumpFile_tomcat.txt�ļ�,���Կ���������Ϣ
```
    �ļ���С: 72.8 MB

    �ֽ�����: 64,171,715
    ������: 12,744
    ʵ������: 722,129
    �������: 99
    �������ո��ڵ�: 3,300
    �ȴ��������ݹҶ�����: 0d
    .....�Լ�������ϵͳ����
    ��
    ���Կ������ʵ����
    
    
    
```

- jhat�鿴

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
�������:http://www.codingfuns.com:7000/ �鿴�����.


- summay

Learning needs to be repeated.




