# linux modify system time

linux 系统时间设定,最近发现系统时间有问题, 于是修改了一下系统时间.

- 查看当前系统时间
```
date "+%Y %m %d %H:%M:%S"
```
- 修改当前系统时间为上海时间
```

rm -rf /etc/localtime
ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
```
- summay <br>
Rome is not build in one day.
冰冻三尺非一日之寒,所以慢慢总结吧.
