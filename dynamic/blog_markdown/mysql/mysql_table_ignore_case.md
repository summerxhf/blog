# mysql table ignore case

mysql 默认大小写规则<br>
1、数据库名与表名是严格区分大小写的；<br>
2、表的别名是严格区分大小写的；<br>
3、列名与列的别名在所有的情况下均是忽略大小写的；<br>
4、变量名也是严格区分大小写的；<br>

为了兼容旧的版本,就只能不区分大小写,在mysql中可以设置,如下.
vim  /etc/my.cnf
```
[mysqld]
# xhf add 1 no 0 yes
lower_case_table_names=1
```

默认0 不忽略大小写,1 忽略大小写.

