# mysqldump tool  big data export  Import

Ҫ��:�����800���ű�, ���ݴ����4G�������,ʹ��mysql���ӹ�������navicat �������붼ʮ�ֵ���,���Ǵ������ʱ������IO��������.
���ߵ���Ĺ����лᵼ�벻����,�������˲�����˵mysql����mysqldump���ߵ���������Ƚ���˵���.

>��Լ4g������15���ӵ���. 10���ӵ���ɹ�

- ����(ģ��test)
windows������  
```
#ת��C�̸�Ŀ¼ִ��
 mysqldump -u�Ñ��� -p���� -d ���ݿ��� ���� �ű���;

#1���������ݿ�Ϊdbname�ı�ṹ�������Ñ���Ϊroot,����Ϊdbpasswd,���ɵĽű���Ϊdb.sql��
mysqldump -uroot -pdbpasswd -d dbname >db.sql;

#2���������ݿ�Ϊdbnameĳ�ű�(test)�ṹ
mysqldump -uroot -pdbpasswd -d dbname test>db.sql;

#3���������ݿ�Ϊdbname���б�ṹ�������ݣ�����-d��
mysqldump -uroot -pdbpasswd  dbname >db.sql;

#4���������ݿ�Ϊdbnameĳ�ű�(test)�ṹ�������ݣ�����-d��
mysqldump -uroot -pdbpasswd dbname test>db.sql;

#5��������ĳһ·����.
mysqldump -uroot -pdbpasswd dbname test>c:\export\db.sql;

```

linux��������windows����һ��
```
[root@xhf_cloud ~]# mysqldump -umysql -pmysql!@#$@%@psw  blog_demo >/usr/blog_demo.sql
mysqldump: [Warning] Using a password on the command line interface can be insecure.

#���Կ�������������
[root@xhf_cloud usr]# ll
total 51136
dr-xr-xr-x.  2 root root    20480 Jan  9 15:57 bin
-rw-r--r--   1 root root     3051 Jan 23 20:30 blog_demo.sql

```

- ����

>��ʽ:mysqldump -u�û��� -p���� -h���� ���ݿ� < ·��

���罫����������blog_demo���뵽import_database���ݿ���
```

[root@xhf_cloud usr]# mysql -umysql -p1234test  import_database < /usr/blog_demo.sql             
mysql: [Warning] Using a password on the command line interface can be insecure.
```

���Կ���import_database�����ݿ����Ѿ�����blog_demo�е�ȫ����ṹ������.(linux windows���������)



- summary<br>
�ೢ��,�ҽݾ�








