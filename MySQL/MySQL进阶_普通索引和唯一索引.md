# MySQL进阶_普通索引和唯一索引

+ 普通索引：mysql>ALTER TABLE table_name ADD INDEX index_name ( column )
> 普通索引（由关键字KEY或INDEX定义的索引）的唯一任务是加快对数据的访问速度。
因此，应该只为那些最经常出现在查询条件（WHEREcolumn=）或排序条件（ORDERBYcolumn）中的数据列创建索引
+ 唯一索引：mysql>ALTER TABLE table_name ADD UNIQUE ( column )
> 如果能确定某个数据列将只包含彼此各不相同的值，在为这个数据列创建索引的时候就
应该用关键字UNIQUE把它定义为一个唯一索引，一方面简化了索引的管理，更由效率，应
一方面可以判断新记录插入时不会出现重复

## 查询过程
+ 普通索引：查找到一个满足的条件的数据之后继续向后查询，直到出现一个不满足的数据
+ 唯一索引：查找到一个满足的条件的数据后会停止查询

因为InnoDB的数据是以页为单位读写的，因此这两中查询方法的性能几乎没有差别

## 更新过程
更新数据时，如果该数据页在内存中，则可以直接更新；若不在内存中，在不影响数据一致性
的前提下会将更新操作缓存在change buffer中，遇到需要访问数据的情况就读取硬盘中的数据
并执行change buffer中的操作，以保证获取正确的数据

应用change buffer到持久页数据的过程被称为merge，显然change buffer可以减少读磁盘的次数
，从而提升执行数据，毕竟读磁盘是相当耗时的操作，同时减少了读入的数据占用buffer pool的
内存

### 使用change buffer的条件
唯一索引在更新数据时需要判断是否唯一，因此必须获取磁盘数据，如果在内存中就可以直接
更新，所以唯一索引无法使用change buffer，实际上也只有普通索引可以使用change buffer

change buffer使用的是buffer pool的内存，因此有容量限制，可以通过innodb_change_buffer_
max_size来设置大小，规则是占用buffer pool的百分比

### change buffer的使用场景
因为change buffer的使用主要是为了减少读磁盘操作，所以对于更新之后不会立即查询的
，即可以在一次change buffer中多次记录更新的状态的场景会使效率提高比较明显，例如
账单和日志类系统

而如果是跟新之后迅速查询，则会导读磁盘的次数没有减少，反而要承受额外维护change 
buffer带来的代价

所以考虑对更新性能的影响，尽量选用普通索引，如果碰到频繁查询的的情况则关闭change 
buffer就好

### change buffer和redo log
change buffer与redo log的机制有一定的相似，但是有本质的不同：
+ redo log的应用是为了减少写磁盘的次数
+ changebuffer的应用是为了减少读磁盘的次数