# centos6 mysql yum install

in my company i neet test myself code,so i need install mysql in my virtual box linux system. as follow step by step.

- 1 if already install mysql
```
# yum list installed | grep mysql
```

- 2 if you system has already installed
```
# yum -y remove mysql-libs.x86_64
```
- 3 download mysql rpm file,i will install mysql 5.6
```
# wget http://repo.mysql.com/mysql-community-release-el6-5.noarch.rpm
# rpm -ivh mysql-community-release-el6-5.noarch.rpm
```
- 4 if already has install file,use follow cmd
```
#yum repolist all | grep mysql
```
- 5 install mysql (select y)
```
# yum install mysql-community-server
```
- 6 start mysql service
```
# service mysqld start
```
- 7 mysql user root default is not pwd,we can set password for root
```
# mysql -u root
# use mysql;
# update user set password=PASSWORD("input root password here") where User='root';
```
- 8 authorization root or other users can visit localhost  testdb.

```
#root
# grant all privileges on testdb.* to root@localhost identified by 'rootpassword';
# flush privileges;

# other mysql users;
# grant all privileges on testdb.* to aaa@localhost identified by 'aaa-password';
# flush privileges;
```

- 9 create database ,using utf8 encoding
```
# CREATE DATABASE `database` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
# exit;
```

- 10 set mysql start with computer
```
# chkconfig --list | grep mysqld
# chkconfig mysqld on
# example
[root@xhf ~]# chkconfig --list | grep mysqld
mysqld          0:off   1:off   2:off   3:on    4:on    5:on    6:off
[root@xhf ~]# chkconfig mysqld on
[root@xhf ~]# chkconfig --list | grep mysqld
mysqld          0:off   1:off   2:on    3:on    4:on    5:on    6:off
```
- 11 mysql secutiry setting if you need setting
```
# mysql_secure_installation
```
- summay<br>
good afternoon ! my name is xhf





