# mysql 慢sql优化之index_merge


- 场景:慢sql explain 查询如下结果

![explain执行结果](https://uploads.disquscdn.com/images/3d6371538541f197d6ad1b7a54ee7bd347ac1c8139fa25f66fd44b9a8e8283df.png)

explain大概说明:<br>

- explain <br>
id：SQL 语句执行顺序编号<br>
select_type：SQL 语句执行的类型，主要区别普通查询、联合查询和子查询之类的复杂查询<br>
table：SQL 语句执行所引用的数据表<br>
type：显示连接使用的类型<br>
possible_keys：指出 MySQL 能在该数据表中使用哪些索引有助于查询<br>
key：SQL 语句执行时所使用的索引<br>
key_len：SQL 语句执行时所使用的索引的长度。在不损失精确性的情况下，长度越短越好<br>
ref：显示索引的哪一列被使用了<br>
rows：MySQL 认为必须检查的用来返回请求数据的行数<br>
Extra：提供 MySQL 优化器一系列额外信息<br>


在sql执行计划显示<br>
1.select_type: SIMPLE说明是简单的select 查询.<br>
2. table:使用的哪些表<br>
3.type: ref表sf2普通索引引用(不是唯一索引), index_merge :使用了多个独立的索引, 且为and之间查询.<br>
4.possible_keys: 可能使用到的索引<br>
5.key: 使用到的索引<br>
6.key_len: 使用索引的长度,越短越好.<br>
7.ref: 显示使用哪个列或者常数列与key一起从表中选择.<br>

8.rows: 执行的行数, 是一个预估值.<br>
9.extra: explain查询的重要信息, <br>
    Using intersect(idx_sfid,idx_cfid); Using where; Using temporary<br>
    : 使用交叉的索引,使用了临时表(尽量避免交叉索引和 磁盘上的临时表)<br>
    Using index condition; Using where:<br>
    查询覆盖了索引; 使用了where从句来限制那些行将与下一张表匹配,或者使用了哪些行返回给用户.
    
解决使用index_merge问题<br>

可以使用mysql的  FORCE INDEX(idx_service_time), 这样强制使用单个索引,而不使用交叉索引, 会快很多.(当数据量比较大的时候)<br>

优化后的的执行计划如下图所示<br>

![explain优化后结果](https://uploads.disquscdn.com/images/08afe4ffc8086919036df08b8721385147ada2bedc967e6074bd19026e8278d9.png)
  
FORCE INDEX 是帮助mysql执行器进行优化，手动的指定使用哪个索引，也有去掉索引等命令。<br>
例如禁止某个索引<br>
```
select * from table ignore index(PRI) limit 2;(禁止使用主键)
```
强制使用索引<br>
```
select * from table force index(ziduan1_index) limit 2;(强制使用索引"ziduan1_index")

```

上述通过添加mysqlhints来避免了index_merge 问题.<br>

- 发现在手动执行sql的时候,第一次执行的时候会比较慢些, 第二次执行的时候会比较快些,为什么呢?<br>

当有些必要添加索引的字段没有添加索引, 需要对全表进行扫描, 第一次需要从物理磁盘中将全表加载到内存中, 会慢些 。
第二次因为加载的全表在内存中，所以查询会很快，所以添加了索引，第一次的时候就不需要全表扫描，就会很快。<br>一般sql报警的时候会看访问这个sql的平均时间，所以平均时间指标
大的话,就需要优化了。

summary：<br>

对于mysql来说没有像oracle那么强大的sql执行器, 仅仅是用于存储而已，所以需要在写sql的注意。
尽量写小的sql，单表的sql，业务逻辑的东西可以放到java代码中，这样内存中执行，要比从硬盘中取得
要快的很多（即使mysql自己会有内存缓存）。

