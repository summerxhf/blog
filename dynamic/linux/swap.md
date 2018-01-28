# swap space
>swap:内存交换空间,swap的功能是在应付物理内存不足情况下造成内存扩展记录的功能。只有当内存不足的时候，为了让后续程序可以顺利执行，因此内存中暂不使用的程序和数据就会被挪到swap中，内存就会空出来给需要的程序加载。

- 内存使用到多少的时候开始使用swap？
可以查看配置
```
Last login: Sun Jan 14 07:24:43 2018 from 114.252.62.207
  oot@xhf_cloud ~]# vi /proc/sys/vm/swappiness
60
```
这个参数设置:0-100,0是告诉系统进程交换避免移出物理内存。
100：则是疯狂的移除到swap缓存中。

一般来说硬件设备足够的话，可以不需要使用swap空间。
因为swap可能会导致
1. 内存如果真的不够可以报oom异常，我们可以扩展内存的方式来增加将内存。
2. 因为频繁的swap会造成cpu飙高，swap毕竟是磁盘上的一块区域，数据交换属于io。
3. swap空间占满通常会出现“application is out of memory”错误。


- centos 6 recommended swap size


<html>
<!--在这里插入内容-->
<table title="Recommended System Swap Space" summary="Recommended System Swap Space" border="1" cellspacing="0" cellpadding="0">
<tbody>
<tr>
<td valign="top"><b>Amount of RAM in the system </b></td>
<td valign="top"><b>Recommended swap space </b></td>
<td valign="top"><b>Recommended swap space if allowing for hibernation </b></td>
</tr>
<tr>
<td valign="top"><b>小于等于</b><b>&nbsp;2GB </b></td>
<td valign="top">2 times the amount of RAM</td>
<td valign="top">3 times the amount of RAM</td>
</tr>
<tr>
<td valign="top"><b>&gt;&nbsp;2GB – 8GB </b></td>
<td valign="top">Equal to the amount of RAM</td>
<td valign="top">2 times the amount of RAM</td>
</tr>
<tr>
<td valign="top"><b>&gt;&nbsp;8GB – 64GB </b></td>
<td valign="top">0.5 times the amount of RAM</td>
<td valign="top">1.5 times the amount of RAM</td>
</tr>
<tr>
<td valign="top"><b>&gt;&nbsp;64GB </b></td>
<td valign="top">4GB of swap space</td>
<td valign="top">No extra space needed</td>
</tr>
</tbody>
</table>
</html>



- 如何添加swap空间?

https://www.digitalocean.com/community/tutorials/how-to-add-swap-on-centos-6

- 查看swap空间
```
[root@xhf_cloud ~]# swapon -s
Filename                                Type            Size    Used    Priority
/swapfile                               file            2097148 150444  -1
```


- summary

如果内存足够,尽量不要使用swap空间.



