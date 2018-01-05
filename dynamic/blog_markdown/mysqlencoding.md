
# mysql 5.7.20 encoding set  utf8

**background**
when i insert chinese into mysql database,it's become ? ,messy code. so i think i need to set mysql database encoding.

most article told me modify my.cnf,but mysql 5.7 setting is different form it's low version.

**1 see the mysql version cmd.**

```
[root@xhf_cloud etc]# mysql -V
mysql  Ver 14.14 Distrib 5.7.20, for Linux (x86_64) using  EditLine wrapper
```
**2 modify my.cnf
my my.cnf is path is /etc/my.cnf**

you can use find comd to find it.

```
[root@xhf_cloud etc]# vi my.cnf
# For advice on how to change settings please see
# http://dev.mysql.com/doc/refman/5.7/en/server-configuration-defaults.html

[mysqld]
character-set-server=utf8
```

**3 look over encoding**
```
mysql> show variables like "%character%";
+--------------------------+----------------------------+
| Variable_name            | Value                      |
+--------------------------+----------------------------+
| character_set_client     | utf8                       |
| character_set_connection | utf8                       |
| character_set_database   | utf8                       |
| character_set_filesystem | binary                     |
| character_set_results    | utf8                       |
| character_set_server     | utf8                       |
| character_set_system     | utf8                       |
| character_sets_dir       | /usr/share/mysql/charsets/ |
+--------------------------+----------------------------+
8 rows in set (0.01 sec)
```
summary


we need pay attention to  the differences in software versions
