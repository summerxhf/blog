# linux文件权限目录配置

**如下图所示:**<br>
![图片来源于本网站](  https://uploads.disquscdn.com/images/af8ee9f070d55780f9598dbfb53b07f0af6f35cb0487e9dcc198eba35269262b.png )

![图片来源于本网站](  https://uploads.disquscdn.com/images/c4841fe49fcf56e270e1c15d78580cbf9044cf43b978e183d0dc00611f05b47e.png )

**- r w x r w x r - -**(中间空格为了显示方便)
- **(档案类型)**
    1. 当为[d]的时候,则为目录;
    2. 当为[-]的时候则为文件;
    3. 当为[l]则表示连接档(link file);
    4. 当为[b]则表示装置文件里面可供存储的接口设备(可随机存储装置);
    5. 党委[c]的时候则表示为装置文件里面串行端口设备,入键盘,鼠标(一次性读取装置);

- rwx为一组, 其中[r] 可读read; [w]可写write;[x]可执行,可以执行命令权限execute; [-]没有权限; 
- **从左到右依次分为:**<br>
    1. 档案拥有者权限
    2. 档案所属群组权限
    3. 其他人权限
- **数字说明**<br>
    [-][rwx][r-x][r--]
 <br>&nbsp;&nbsp;1  234  567  890
    1. 1代表这个文件名为目录或者文件,本例中为文件.
    2. 234为:拥有者的权限,可读,可写,可执行.
    3. 567:同群组用户权力,本例中可读可执行(rx)
    4. 890:其他用户权力,本利中为可读(r)
    ps: rwx 的位置不会变,有改权限就会显示字符,没有改权限就是(-)


就像windows超级用户Administrator一样,linux中有root超级用户,root用户不受系统权限所限制,所以即使文件权限为[----------],root账户不受系统权限所限制, 无论权限如何, 预设root都可以存取.


- **修改权限类型**

    命令如下:<br>
    chgrp(change group缩写),改变文件所属群组<br>
    chown(change owner缩写):改变文件拥有者<br>
    chmod(change mode缩写):改变文件的权限,SUID,SGID,SBIT等特性.<br>
    **1. chgrp,改变某个文件为某个群组权限, 需要在/etc/group 文件中存在该群组.**<br>
    例如我的群组文件中的群组如下:<br>
    
```
    postfix:x:89:
    sshd:x:74:
    screen:x:84:
    mysql:x:27:
    dbus:x:81:
    nginx:x:499:
```
    
```
    #不存在该群组
    [rd@dev.tomcat.bj6.01 data]$chgrp hahahhahhahha test
chgrp: invalid group: ‘hahahhahhahha’
```
    
```
    #存在该群组.设置为ngxin群组.
    [root@vultr local]# chgrp  nginx  test.ini 
[root@vultr local]# ll
-rw-r--r--   1 root nginx       9 Aug  6 16:50 test.ini
```
<br>**2.chown改变文件拥有者权限**<br>
chown,改变文件拥有者权限,和chgrp一样,需要在/etc/passwd这个文件中 有记录的用户名称才可以改变.
    
例如部分用户
```
    postfix:x:89:89::/var/spool/postfix:/sbin/nologin
sshd:x:74:74:Privilege-separated SSH:/var/empty/sshd:/sbin/nologin
mysql:x:27:27:MySQL Server:/var/lib/mysql:/bin/bash
dbus:x:81:81:System message bus:/:/sbin/nologin
nginx:x:498:499::/home/nginx:/bin/bash
~

~
"/etc//passwd" 23L, 1029C
```
   
```
    # 设置文件所有者
    [root@vultr local]# chown nginx test.ini 
[root@vultr local]# ll
-rw-r--r--   1 nginx nginx       9 Aug  6 16:50 test.ini
[root@vultr local]# 
```
    
```
    #设置不存在的用户名.
    [root@vultr local]# chown  ahahah test.ini 
chown: invalid user: `ahahah'
[root@vultr local]# 
```
    
    
```
    #设置owner同时设置文件group
    [root@vultr local]# chown nginx:nginx test.ini 
[root@vultr local]# ll
```
    
    
```
    # 设置目录的时候, 进行递归(recursive)持续变更,连同目录下的所有文件都进行变更.
    [root@vultr local]# chown -R nginx  test 
[root@vultr local]# ll
drwxr-xr-x   2 nginx root    4096 Aug  6 17:04 test
```
    
ps:通过cp复制,只能复制文件,不能复制文件的权限, 文件的权限需要设置.
<br>**3.改变权限**<br>
chmod<br>
    
**3.1数字类型改变权限**
    
>        可以用数字来代表各个权限,各个权限的分数对照表如下:
    
    r:4
    w:2
    x:1
    
    例如(owner/group/others)各自的三个权限(r/w/x),当权限为[-rwxrwx---]则是
    
    owner = rwx = 4+2+1=7
    group = rwx = 4+2+1=7
    others = --- =0+0+0 =0
    
    所以该文件的权限数字是770 ,如下
    
    [root@vultr www]# chmod 777 root.sh 
    [root@vultr www]# ll
    -rwxrwxrwx 1 root root  254 Jun 12 14:21 root.sh
    
    
    
**3.2符号类型改变权限**<br>
       ![图片来源于本网站]( https://uploads.disquscdn.com/images/14ee0d1c7c59a466cfa4a635de6456ab090d42d3525ba1bbd13252f33381ce8c.png )
       
      例如设定一个权限类型为[-rwxr-xr-x] 基本上的意思是
       user(u):具有可读、可写、可执行的权限；
       group与others（g/0）具有可读与执行的权限;可以用一下命令执行
       
       [root@vultr www]# chmod u=rwx,go=rx root.sh
       [root@vultr www]# ll
       -rwxr-xr-x 1 root root  254 Jun 12 14:21 root.sh
       
       
       如果要为全部人添加写入权限呢,不动其他的权限
       
       [root@vultr www]# chmod a+w root.sh 
       [root@vultr www]# ll
       -rwxrwxrwx 1 root root  254 Jun 12 14:21 root.sh
       
       如果要为全部人去掉可执行权限, 而不动其他的权限.
       [root@vultr www]# chmod a-x root.sh
       [root@vultr www]# ll
       -rw-rw-rw- 1 root root  254 Jun 12 14:21 root.sh
       
       
- **权限对文件的意义**
    - r(read)   可以读取此文件,例如可以读取文本文件的文字内容等.
    - w(Write):可以编辑、新增或者是修改文件的内容（但是**不包含删除该文件**）
    - x（eXecute）：改文件具有可以被系统执行的权限。
- **权限对目录的重要性**
    - r(read contents in directory)  可以读取目录结构列表权限，但是不一定可以进入，进入和x要区别开。
    - w 和文件意义不同，可以拥有如下权限，1、建立新的文件与目录 2、删除已经存在的文件和目录 3、将已经存在的文件目录更名 3、迁移该目录内的文件、目录位置。
    - x（access directory）与文件不同，文件可以执行， 但是目录不可以，目录中x代表用户是否能够进入改目录成为工作目录。
   

- **summary**<br>
夏天赶快过去吧~ 热热的,头发被汗浇透的感觉真不好~ 虽然立秋了,还是没有秋天的味道~<br> 以上的内容来自学习鸟哥的私房菜,作为学习内容总结一下~