# virtual box starting up with computer

��װ�������(virtual box) ,virtual box��ô�޽���,������������?����vm�а�װ��linuxϵͳ
����Ϊxhf,������һЩ���� �ػ�,�����ű�.

- ���������ű�
```
@echo off
:: by xhf
cd /d "C:\Program Files\Oracle\VirtualBox"
VBoxHeadless --startvm "xhf"
:: VBoxManage startvm "xhf" --type headless
exit
```

- �ػ��ű�
```
@echo off
cd C:\Program Files\Oracle\VirtualBox
vboxmanage controlvm xhf poweroff
pause
exit
```

- �����ű�
```
@echo off
:: by xhf
cd /d "C:\Program Files\Oracle\VirtualBox"
VBoxManage controlvm xhf reset
pause
exit

```
- ��ô����������virtual box Ȼ����������װ��linuxϵͳ

- ����һ: <br>
�������ű�.bat�ŵ����Ŀ¼��
C:\Users\XINGHAIFANG\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup

- ������:<br>
��ʼ�˵��������С�������gpedit.msc����**��������Ա༭��**������������á���Windows���á����ű�������/�ػ�������˫���Ҳ���������������������ԶԻ��򡪡���ӡ����ҵ���Ҫ��ӵ�������������ļ�����ȷ��<br>

���õķ�����, ���õ�½,��һ��̨ʽ����������Ϊ������.
���Ƿ���������, ����Ҳ��ҪԶ�̵�½һ�·�����,�ű���ִ��,�û�δ��¼windows��ʱ�򲢲���ִ��.(��֪�����������, ��ӭ���Թ�~~~~)


