# linux modify system time

linux ϵͳʱ���趨,�������ϵͳʱ��������, �����޸���һ��ϵͳʱ��.

- �鿴��ǰϵͳʱ��
```
date "+%Y %m %d %H:%M:%S"
```
- �޸ĵ�ǰϵͳʱ��Ϊ�Ϻ�ʱ��
```

rm -rf /etc/localtime
ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
```
- summay <br>
Rome is not build in one day.
�������߷�һ��֮��,���������ܽ��.
