# mysqldump tool  big data export  Import

要求:差不多有800多张表, 数据大概有4G多的数据,使用mysql连接工具例如navicat 导出导入都十分的慢,而是大多数的时候由于IO操作卡掉.
或者导入的过程中会导入不完整,后来查了查网上说mysql工具mysqldump工具导出导入相比较来说最快.

>大约4g多数据15分钟导出. 10分钟导入成功

- 导出(模拟test)
windows环境下  
```
#转到C盘根目录执行
 mysqldump -u用戶名 -p密码 -d 数据库名 表名 脚本名;

#1、导出数据库为dbname的表结构（其中用戶名为root,密码为dbpasswd,生成的脚本名为db.sql）
mysqldump -uroot -pdbpasswd -d dbname >db.sql;

#2、导出数据库为dbname某张表(test)结构
mysqldump -uroot -pdbpasswd -d dbname test>db.sql;

#3、导出数据库为dbname所有表结构及表数据（不加-d）
mysqldump -uroot -pdbpasswd  dbname >db.sql;

#4、导出数据库为dbname某张表(test)结构及表数据（不加-d）
mysqldump -uroot -pdbpasswd dbname test>db.sql;

#5、导出到某一路径中.
mysqldump -uroot -pdbpasswd dbname test>c:\export\db.sql;

```

linux环境下与windows命令一样
```
[root@xhf_cloud ~]# mysqldump -umysql -pmysql!@#$@%@psw  blog_demo >/usr/blog_demo.sql
mysqldump: [Warning] Using a password on the command line interface can be insecure.

#可以看到导出的数据
[root@xhf_cloud usr]# ll
total 51136
dr-xr-xr-x.  2 root root    20480 Jan  9 15:57 bin
-rw-r--r--   1 root root     3051 Jan 23 20:30 blog_demo.sql

```

- 导入

>格式:mysqldump -u用户名 -p密码 -h主机 数据库 < 路径

例如将上述导出的blog_demo导入到import_database数据库中
```

[root@xhf_cloud usr]# mysql -umysql -p1234test  import_database < /usr/blog_demo.sql             
mysql: [Warning] Using a password on the command line interface can be insecure.
```

可以看到import_database空数据库中已经有了blog_demo中的全部表结构和数据.(linux windows命令均适用)



- summary<br>
多尝试,找捷径








