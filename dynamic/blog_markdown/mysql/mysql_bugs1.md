
# com.mysql.cj.jdbc.exceptions.PacketTooBigException
最近自己的网站写博客报500错误,查看日志发现是mysql的异常,是配置问题,error as follow:
```
Cause: com.mysql.cj.jdbc.exceptions.PacketTooBigException: Packet for query is too large (9,431 > 1,024). You can change this value on the server by setting the 'max_allowed_packet' variable.
; SQL []; Packet for query is too large (9,431 > 1,024). You can change this value on the server by setting the 'max_allowed_packet' variable.; nested exception is com.mysql.cj.jdbc.exceptions.PacketTooBigException: Packet for query is too large (9,431 > 1,024). You can change this value on the server by setting the 'max_allowed_packet' variable.] with root cause
com.mysql.cj.jdbc.exceptions.PacketTooBigException: Packet for query is too large (9,431 > 1,024)
```
above problem is max_allowed_packet set too small,default 1024byte ,so we should set it large.
- 1 A permanent set
```
[mysqld]
#xhf add for connection
max_allowed_packet =20M
```
- 2 a short time set
```
#mysql cmd
set global max_allowed_packet = 2*1024*1024*10;
```

- 3 look over our setting.

```
mysql> show VARIABLES like '%max_allowed_packet%';
+--------------------------+------------+
| Variable_name            | Value      |
+--------------------------+------------+
| max_allowed_packet       | 1073741824 |
| slave_max_allowed_packet | 1073741824 |
+--------------------------+------------+
2 rows in set (0.01 sec)

```

- 4 summary
it's hard not to make mistakes in the same place. so i need summary my errors;


