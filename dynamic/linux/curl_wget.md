# curl and wget post get request

curl and wget post get request的使用
- **curl get**<br>
```
curl http://127.0.0.1:8083/zd-basetype/all

```

- **curl post**<br>
1.参数放到body中<br>
```
#-H默认是 application/x-www-form-urlencoded格式
curl -d "user=Summer&passwd=12345678" "http://127.0.0.1:8080/check_your_status"
#json格式请求

curl -l -H "Content-type: application/json" -X POST -d '{"phone":"13521389587","password":"test"}' http://domain/apis/users.json
```

2.参数放到head中<br>
```
#第一个-H在header中设置格式为json格式. 第二个-H 传递的json数组.
curl -l -H "Content-type: application/json" -H "ids:[13,3]" -X POST http://127.0.0.1:8896/his/autoCharging
```

- **wget get**<br>
```
[root@vultr www]# wget "http://www.google.com"
--2018-08-29 15:37:44--  http://www.google.com/
Resolving www.google.com... 74.125.200.99, 74.125.200.105, 74.125.200.103, ...
Connecting to www.google.com|74.125.200.99|:80... connected.
HTTP request sent, awaiting response... 200 OK
Length: unspecified [text/html]
Saving to: “index.html”

    [ <=>                                                                                                                                                                                               ] 11,342      --.-K/s   in 0s      

2018-08-29 15:37:44 (295 MB/s) - “index.html” saved [11342]
```

- **wget post**<br>
```
[root@vultr www]# wget –post-data ‘user=foo&password=bar’ http://www.google.com
[1] 10301
-bash: http://www.google.com: No such file or directory
[root@vultr www]# --2018-08-29 15:38:31--  http://%E2%80%93post-data/
Resolving –post-data... failed: Name or service not known.
wget: unable to resolve host address “–post-data”
--2018-08-29 15:38:31--  http://%E2%80%98user=foo/
Resolving ‘user=foo... failed: Name or service not known.
wget: unable to resolve host address “‘user=foo”
```


- **summary**<br>
 之前一直用wget命令的get方式,今天用post方式且传参在header中.