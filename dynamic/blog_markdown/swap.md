# swap space
>swap:�ڴ潻���ռ�,swap�Ĺ�������Ӧ�������ڴ治�����������ڴ���չ��¼�Ĺ��ܡ�ֻ�е��ڴ治���ʱ��Ϊ���ú����������˳��ִ�У�����ڴ����ݲ�ʹ�õĳ�������ݾͻᱻŲ��swap�У��ڴ�ͻ�ճ�������Ҫ�ĳ�����ء�

- �ڴ�ʹ�õ����ٵ�ʱ��ʼʹ��swap��
���Բ鿴����
```
Last login: Sun Jan 14 07:24:43 2018 from 114.252.62.207
  oot@xhf_cloud ~]# vi /proc/sys/vm/swappiness
60
```
�����������:0-100,0�Ǹ���ϵͳ���̽��������Ƴ������ڴ档
100�����Ƿ����Ƴ���swap�����С�

һ����˵Ӳ���豸�㹻�Ļ������Բ���Ҫʹ��swap�ռ䡣
��Ϊswap���ܻᵼ��
1. �ڴ������Ĳ������Ա�oom�쳣�����ǿ�����չ�ڴ�ķ�ʽ�����ӽ��ڴ档
2. ��ΪƵ����swap�����cpu쭸ߣ�swap�Ͼ��Ǵ����ϵ�һ���������ݽ�������io��
3. swap�ռ�ռ��ͨ������֡�application is out of memory������


- centos 6 recommended swap size


<html>
<!--�������������-->
<table title="Recommended System Swap Space" summary="Recommended System Swap Space" border="1" cellspacing="0" cellpadding="0">
<tbody>
<tr>
<td valign="top"><b>Amount of RAM in the system </b></td>
<td valign="top"><b>Recommended swap space </b></td>
<td valign="top"><b>Recommended swap space if allowing for hibernation </b></td>
</tr>
<tr>
<td valign="top"><b>С�ڵ���</b><b>&nbsp;2GB </b></td>
<td valign="top">2 times the amount of RAM</td>
<td valign="top">3 times the amount of RAM</td>
</tr>
<tr>
<td valign="top"><b>&gt;&nbsp;2GB �C 8GB </b></td>
<td valign="top">Equal to the amount of RAM</td>
<td valign="top">2 times the amount of RAM</td>
</tr>
<tr>
<td valign="top"><b>&gt;&nbsp;8GB �C 64GB </b></td>
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



- ������swap�ռ�?

https://www.digitalocean.com/community/tutorials/how-to-add-swap-on-centos-6

- �鿴swap�ռ�
```
[root@xhf_cloud ~]# swapon -s
Filename                                Type            Size    Used    Priority
/swapfile                               file            2097148 150444  -1
```


- summary

����ڴ��㹻,������Ҫʹ��swap�ռ�.



