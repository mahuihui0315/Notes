# MySQL进阶_如何给字符串增加索引
对于支持邮箱登录的系统，需要在邮箱地址字段上建立索引，一般来说有两种方式
+ 全字段索引
> mysql> alter tablename add index index1(email);
+ 字段前缀索引
> mysql> alter tablename add index index2(email(6));

## 全字段索引
显而易见，全字段建立索引会消耗更多的空间，但是查询方面，判断邮箱符合条件、获取主键、得到数据，只需要一次，系统认为 只扫描了一行

## 字段前缀索引
使用前缀索引在判断邮箱时可能会出现多条符合记录的条件，因此需要获取主键、通过主键获取数据进而判断是否符合条件，带来多次扫描的性能消耗

但是可以通过增加前缀的前度来减少重复的键值，增加区分度，减少查询次数

### 确定前缀长度
```
mysql> select 
  count(distinct left(email,4)）as L4,
  count(distinct left(email,5)）as L5,
  count(distinct left(email,6)）as L6,
  count(distinct left(email,7)）as L7,
from tablename;
```
可以通过上述sql语句来查看，当前缀为某一值的时候记录数是多少，以此来判断到底该使用多长的前缀

### 前缀索引对覆盖索引的影响
覆盖索引可以不回表而直接返回数据，因为索引中已经包含了所需要查询的数据。但是如果使用前缀索引，因为截取了部分索引而导致信息不全，所以不能使用覆盖索引

## 其他方式

### 倒序存储
像身份证这种前几位的重复度非常高，可以考虑使用倒序存储，使用后面几位重复度较低的进行查询
```
mysql> select field_list from t where id_card = reverse('input_id_card_string');
```

### hash字段
通过在表上再建立一个hash字段来存储特定字段的hash值进行查询
```
mysql> alter table t add id_card_crc int unsigned, add index(id_card_crc);
```
在每次插入新数据时通过函数crc32获取hash值存储到该字段，但是crc32得到的值也可能相同，因此还需要判断输入查询值是否精确相同
```
mysql> select field_list from t where id_card_crc=crc32('input_id_card_string') and id_card='input_id_card_string'；
```
### 倒序和hash的异同点

#### 相同点
都无法范围查询

#### 不同点
1. 额外占用的空间，不同于倒序，hash需要一个额外的字段，但是倒序会随着区分度的上升而增加索引的空间消耗
2. CPU方面，倒序在读写时都需要使用reverse函数，hash方式也需要调用crc32函数
3. 查询效率，hash值虽然可能会重复但是也只是很小的几率，而倒序仍旧是使用前缀的方式，还是会增加扫描行数