# mysql ��sql�Ż�֮index_merge


- ����:��sql explain ��ѯ���½��

![explainִ�н��](https://uploads.disquscdn.com/images/3d6371538541f197d6ad1b7a54ee7bd347ac1c8139fa25f66fd44b9a8e8283df.png)

explain���˵��:<br>

- explain <br>
id��SQL ���ִ��˳����<br>
select_type��SQL ���ִ�е����ͣ���Ҫ������ͨ��ѯ�����ϲ�ѯ���Ӳ�ѯ֮��ĸ��Ӳ�ѯ<br>
table��SQL ���ִ�������õ����ݱ�<br>
type����ʾ����ʹ�õ�����<br>
possible_keys��ָ�� MySQL ���ڸ����ݱ���ʹ����Щ���������ڲ�ѯ<br>
key��SQL ���ִ��ʱ��ʹ�õ�����<br>
key_len��SQL ���ִ��ʱ��ʹ�õ������ĳ��ȡ��ڲ���ʧ��ȷ�Ե�����£�����Խ��Խ��<br>
ref����ʾ��������һ�б�ʹ����<br>
rows��MySQL ��Ϊ����������������������ݵ�����<br>
Extra���ṩ MySQL �Ż���һϵ�ж�����Ϣ<br>


��sqlִ�мƻ���ʾ<br>
1.select_type: SIMPLE˵���Ǽ򵥵�select ��ѯ.<br>
2. table:ʹ�õ���Щ��<br>
3.type: ref��sf2��ͨ��������(����Ψһ����), index_merge :ʹ���˶������������, ��Ϊand֮���ѯ.<br>
4.possible_keys: ����ʹ�õ�������<br>
5.key: ʹ�õ�������<br>
6.key_len: ʹ�������ĳ���,Խ��Խ��.<br>
7.ref: ��ʾʹ���ĸ��л��߳�������keyһ��ӱ���ѡ��.<br>

8.rows: ִ�е�����, ��һ��Ԥ��ֵ.<br>
9.extra: explain��ѯ����Ҫ��Ϣ, <br>
    Using intersect(idx_sfid,idx_cfid); Using where; Using temporary<br>
    : ʹ�ý��������,ʹ������ʱ��(�������⽻�������� �����ϵ���ʱ��)<br>
    Using index condition; Using where:<br>
    ��ѯ����������; ʹ����where�Ӿ���������Щ�н�����һ�ű�ƥ��,����ʹ������Щ�з��ظ��û�.
    
���ʹ��index_merge����<br>

����ʹ��mysql��  FORCE INDEX(idx_service_time), ����ǿ��ʹ�õ�������,����ʹ�ý�������, ���ܶ�.(���������Ƚϴ��ʱ��)<br>

�Ż���ĵ�ִ�мƻ�����ͼ��ʾ<br>

![explain�Ż�����](https://uploads.disquscdn.com/images/08afe4ffc8086919036df08b8721385147ada2bedc967e6074bd19026e8278d9.png)
  
FORCE INDEX �ǰ���mysqlִ���������Ż����ֶ���ָ��ʹ���ĸ�������Ҳ��ȥ�����������<br>
�����ֹĳ������<br>
```
select * from table ignore index(PRI) limit 2;(��ֹʹ������)
```
ǿ��ʹ������<br>
```
select * from table force index(ziduan1_index) limit 2;(ǿ��ʹ������"ziduan1_index")

```

����ͨ�����mysqlhints��������index_merge ����.<br>

- �������ֶ�ִ��sql��ʱ��,��һ��ִ�е�ʱ���Ƚ���Щ, �ڶ���ִ�е�ʱ���ȽϿ�Щ,Ϊʲô��?<br>

����Щ��Ҫ����������ֶ�û���������, ��Ҫ��ȫ�����ɨ��, ��һ����Ҫ����������н�ȫ����ص��ڴ���, ����Щ ��
�ڶ�����Ϊ���ص�ȫ�����ڴ��У����Բ�ѯ��ܿ죬�����������������һ�ε�ʱ��Ͳ���Ҫȫ��ɨ�裬�ͻ�ܿ졣<br>һ��sql������ʱ��ῴ�������sql��ƽ��ʱ�䣬����ƽ��ʱ��ָ��
��Ļ�,����Ҫ�Ż��ˡ�

summary��<br>

����mysql��˵û����oracle��ôǿ���sqlִ����, ���������ڴ洢���ѣ�������Ҫ��дsql��ע�⡣
����дС��sql�������sql��ҵ���߼��Ķ������Էŵ�java�����У������ڴ���ִ�У�Ҫ�ȴ�Ӳ����ȡ��
Ҫ��ĺܶࣨ��ʹmysql�Լ������ڴ滺�棩��

