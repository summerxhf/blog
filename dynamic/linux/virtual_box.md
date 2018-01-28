# virtual box starting up with computer

安装完虚拟机(virtual box) ,virtual box怎么无界面,开机自启动呢?我在vm中安装的linux系统
名称为xhf,下面是一些开机 关机,重启脚本.

- 开机启动脚本
```
@echo off
:: by xhf
cd /d "C:\Program Files\Oracle\VirtualBox"
VBoxHeadless --startvm "xhf"
:: VBoxManage startvm "xhf" --type headless
exit
```

- 关机脚本
```
@echo off
cd C:\Program Files\Oracle\VirtualBox
vboxmanage controlvm xhf poweroff
pause
exit
```

- 重启脚本
```
@echo off
:: by xhf
cd /d "C:\Program Files\Oracle\VirtualBox"
VBoxManage controlvm xhf reset
pause
exit

```
- 怎么开机自启动virtual box 然后自启动安装的linux系统

- 方法一: <br>
把启动脚本.bat放到这个目录下
C:\Users\XINGHAIFANG\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup

- 方法二:<br>
开始菜单——运行——输入gpedit.msc——**本地组策略编辑器**——计算机配置——Windows设置——脚本（启动/关机）——双击右侧的启动——弹出启动属性对话框——添加——找到你要添加到程序或批处理文件——确定<br>

我用的方法二, 不用登陆,用一个台式机的主机作为服务器.
但是发现有问题, 就是也需要远程登陆一下服务器,脚本才执行,用户未登录windows的时候并不会执行.(有知道解决方案的, 欢迎留言哈~~~~)


