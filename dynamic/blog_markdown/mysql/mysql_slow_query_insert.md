
# mysql insert ���Ż� 


- ����:�����ڲ���ĳ�����ʱ����, ��ʾƽ������ʱ��Ϊ5s,��insert����Ż�, Ŀǰ֪���Ŀ�����������.

1�����е��������Ƿ����<br>

2�� �������������ܴ��ʱ�򣬽����������Ƿ����, �Ƿ���Ҫɾ��һЩû��Ҫ������.<br>

3����Ƭ���⣬�鿴���е���Ƭ��


- mysql��Ƭ����ԭ��

1)������һ�������ݺ�,֮��Ը����ݽ���ɾ������,�ò��ֿռ䲢���������������е��������ͷų�������Ϊ������еĵ�ַ�ռ䶼�������ģ�������ÿ�β���һ������
�ͻ��ƶ����еĴ洢��Ԫ��Ҳ������ɾ�������ռ���������ٴ�д�롣

2����ִ�в��������ʱ�򣬻�����ʹ�ÿհ��������ĳ�εĲ���ǡ��С�ڵ���ĳ���հ׵Ĵ�С���Ż�ز��롣����ܶ�εĲ���
�����ڿհ�����Ĵ�С����Զ���ᱻ���롣�������հ׵��������Զû�а취��ռ�ã����γ�����Ƭ��

���磺�����������磩

ĳ��һ���У�ĳ��10���ֽڣ���ռ��10���ֽڴ洢��ִ��ɾ��������ֻ����һ�У�ʵ������ֻʣ��10�ֽڣ�����mysql����ʱ�������������뵽�հ�����
��ȡʱҲ����Ϊ10���ֽڽ��д���������ƬԽ�࣬�ͻ�Խ��ԽӰ�����ܡ�

�鿴��Ƭ�ռ�<br>

 
 
  1. ��ѯĳ�������Ƭ��С
  ```
  SHOW TABLE STATUS LIKE 'yf_ztyptest';

  ```
 ![��ѯ��ռ���Ƭ����](https://uploads.disquscdn.com/images/d06395c575b28a25c648fdc3aea256fc90691ff7f30e49780d4ca2117648ead7.png 
)

����Data_free �о�����Ƭ��С

   2.�г������Ѳ�����Ƭ�ı�
   ```
   select table_schema db, table_name, data_free, engine     
   from information_schema.tables 
   where table_schema not in ('information_schema', 'mysql')  and data_free > 0;
   
   ```
   ����ͼ��ʾ:<br>
    ![�г����б���Ƭ��С](https://uploads.disquscdn.com/images/657738bf90e11ca6cbe7d3e20b69effddb41f095290e4a54c1419cf530326aa5.png 
   )
   
   3.�����Ƭ��ռ�.
   
   ��1��MyISAM�� <br>
   mysql> optimize table ����<br>
   ��2��InnoDB��<br>
   mysql> alter table ���� engine=InnoDB;<br>
   
   3)��ѯ��ռ��С����Ƭ��С<br>
   
   ```
   SELECT TABLE_NAME,(DATA_LENGTH+INDEX_LENGTH)/1024/1024 size_mb,data_free/1024/1024 free_mb,TABLE_ROWS FROM information_schema.tables where table_schema='g_lb' 
   
   and TABLE_NAME='da_dz' order by free_mb desc;
   ```
   �����Ƭ֮ǰ�ı��С����:
   
   ����ͼ��ʾ:<br>
    ![�г�ĳ�����ݿ�ĳ����Ŀռ����Ƭ�ռ�](    https://uploads.disquscdn.com/images/0a183725419bcd44022388a6312030a731cb7b0a7e86596daba2c994b48e5105.png 
  )
   
   
   ִ���������Ƭsql
   
   ```
   alter table da_dz engine=InnoDB;
   ```
   
   ִ����������:<br>
    ![�������](https://uploads.disquscdn.com/images/2e425f05cd6296b718f69ab008882d23086b242cb0f0b788deecc2fcbf35faf1.png 
)


�ɼ�,����Ƭ�����, �ͷ��˱�ռ�,���ݿ������Ҳ��õ��ܴ������.

��Ȼ,�����Ż��з��ֲ���������Ƭ, ����������������������, ���Ҷ������ظ�����Ψһ����(myslq innodb �������������Զ��������)
- summary:����Ƕ�insert����Ż���һЩСtips,�����ܽ��С�


      
   
   
   
   
   
   
   
     











 



    
    
    
    
    


