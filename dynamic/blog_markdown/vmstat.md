# vmstat Memory monitoring

vmstate ���Բ鿴cpu���ڴ桢io��ʹ�������

����
```

[root@xhf_cloud git_branch]# vmstat 
procs -----------memory---------- ---swap-- -----io---- --system-- -----cpu-----
 r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st
 0  0 150808 105764  38720 219852    0    1     1     7   71   23  0  0 99  0  0
 
 #ÿ�����ӡһ��,����ӡ5��
 [root@xhf_cloud git_branch]# vmstat 2 5
procs -----------memory---------- ---swap-- -----io---- --system-- -----cpu-----
 r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st
 0  0 150808 107324  38728 219880    0    1     1     7   71   23  0  0 99  0  0
 0  0 150808 108672  38728 219876    0    0     0     0   73  179  1  0 99  0  0
 0  0 150808 107408  38728 219880    0    0     0    18   95  194  1  1 97  0  0
 0  0 150808 107408  38728 219880    0    0     0    14   67  174  1  0 99  0  0
 0  0 150808 107408  38728 219880    0    0     0     0   63  165  0  0 100  0  0
```

����˵��


| procs | memory | swap |  io| system | cpu |
|--- | ---|--- | ---|--- | --- | 
| r: ���ж����н�������| swpd:�����ڴ��С |si:ÿ��ӽ�����д���ڴ��С |bi:ÿ���ȡ�Ŀ��� | in:ÿ���ж���,����ʱ���ж� | us:�û�����ʱ�� |
| b: �ȴ�io��������| free:�����ڴ��С |so:ÿ��д�뽻�����ڴ��С |bo:ÿ��д��Ŀ��� | cs:ÿ���������л��� |sy:ϵͳ����ִ��ʱ�� |
|        | buff:�����ڴ��С |        |   |                                                                |id:�ռ�ʱ�� |
|        | cache:�����ڴ��С |       |   |                                                                | wa:�ȴ�ioʱ�� |
|        | free:�����ڴ��С |


summay:���Զ�̬�Ŀ���swap���ڴ潻�����.

�ο�:https://www.thomas-krenn.com/en/wiki/Linux_Performance_Measurements_using_vmstat

