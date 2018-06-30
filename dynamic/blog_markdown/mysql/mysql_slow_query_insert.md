
# mysql insert 慢优化 


- 场景:报警在插入某条语句时报警, 显示平均插入时间为5s,对insert语句优化, 目前知道的考虑下面三点.

1、表中的数据量是否过大。<br>

2、 当表中数据量很大的时候，建立的索引是否过多, 是否需要删除一些没必要的索引.<br>

3、碎片问题，查看表中的碎片。


- mysql碎片产生原因

1)当插入一部分数据后,之后对该数据进行删除操作,该部分空间并不会如我们想象中的立即会释放出来，因为计算机中的地址空间都是连续的，不可能每次擦除一块区域，
就会移动所有的存储单元。也不可能删除掉这块空间后，立即会再次写入。

2）当执行插入操作的时候，会首先使用空白区域，如果某次的插入恰好小于等于某个空白的大小，才会回插入。如果很多次的插入
都大于空白区域的大小就永远不会被插入。这样这块空白的区域就永远没有办法被占用，就形成了碎片。

例如：（来自于网络）

某表一万行，某行10个字节，会占用10万字节存储，执行删除操作，只留下一行，实际内容只剩下10字节，但是mysql插入时，不会立即插入到空白区域，
读取时也会视为10万字节进行处理，所以碎片越多，就会越来越影响性能。

查看碎片空间<br>

 
 
  1. 查询某个表的碎片大小
  ```
  SHOW TABLE STATUS LIKE 'yf_ztyptest';

  ```
 ![查询表空间碎片问题](https://uploads.disquscdn.com/images/d06395c575b28a25c648fdc3aea256fc90691ff7f30e49780d4ca2117648ead7.png 
)

其中Data_free 列就是碎片大小

   2.列出所有已产生碎片的表
   ```
   select table_schema db, table_name, data_free, engine     
   from information_schema.tables 
   where table_schema not in ('information_schema', 'mysql')  and data_free > 0;
   
   ```
   如下图所示:<br>
    ![列出所有表碎片大小](https://uploads.disquscdn.com/images/657738bf90e11ca6cbe7d3e20b69effddb41f095290e4a54c1419cf530326aa5.png 
   )
   
   3.清除碎片表空间.
   
   （1）MyISAM表 <br>
   mysql> optimize table 表名<br>
   （2）InnoDB表<br>
   mysql> alter table 表名 engine=InnoDB;<br>
   
   3)查询表空间大小和碎片大小<br>
   
   ```
   SELECT TABLE_NAME,(DATA_LENGTH+INDEX_LENGTH)/1024/1024 size_mb,data_free/1024/1024 free_mb,TABLE_ROWS FROM information_schema.tables where table_schema='g_lb' 
   
   and TABLE_NAME='da_dz' order by free_mb desc;
   ```
   清除碎片之前的表大小如下:
   
   如下图所示:<br>
    ![列出某个数据库某个表的空间和碎片空间](    https://uploads.disquscdn.com/images/0a183725419bcd44022388a6312030a731cb7b0a7e86596daba2c994b48e5105.png 
  )
   
   
   执行清除表碎片sql
   
   ```
   alter table da_dz engine=InnoDB;
   ```
   
   执行完结果如下:<br>
    ![清除后结果](https://uploads.disquscdn.com/images/2e425f05cd6296b718f69ab008882d23086b242cb0f0b788deecc2fcbf35faf1.png 
)


可见,对碎片整理后, 释放了表空间,数据库的性能也会得到很大的提升.

当然,本次优化中发现并不存在碎片, 而是在在索引建立不合理, 而且对主键重复加了唯一索引(myslq innodb 会对主键和外键自动添加索引)
- summary:大概是对insert语句优化的一些小tips,不断总结中。


      
   
   
   
   
   
   
   
     











 



    
    
    
    
    


