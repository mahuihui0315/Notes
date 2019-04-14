# MySQL进阶_深入理解MVCC

案例：k初始值为1

事务A | 事务B | 事务C
-- | -- | --
start transaction with consistency snapshot; | null | null
null | start transaction with consistency snapshot |null
null | null | update t set k=k+1 where id=1
null | update t set k=k+1 where id=1;select k from t where id=1 | null
select k from t where id =1;commit; | commit; | null

**注：** begin start transaction 并不是事务的起点，执行第一条语句才算开始；如果需要马上
启动就使用start transaction with consistency snapshot
## MVCC的快照工作原理

在可重复读隔离级别下开启事务会对整个数据库做一次快照，但并不是直接复制数据库

InnoDB中每一个事务都有唯一的事务id，且申请顺序严格递增的，每行数据也有多个版本，
每次更新数据，都会生成一个新的数据版本，并把事务id复制给该版本，同时旧的数据版本
也会保留。当需要回滚数据时就通过这些更新版本向前计算数据

每一个事务都会有一个数组用来保存事务启动瞬间所有活跃的事务id，当前事务通过检查
一个版本数据的事务id来判断该数据是否可见，例如一个版本数据的事务id小于数组中最小的
id则该数据可见，同理若大于最大id则不可见

简单来说，一个数据版本，对于当前事务试图来说，除了自己的更新总是可见之外，又三种情况：
1. 版本未提交，不可见
2. 版本已提交，但是在试图创建之后提交的，不可见
3. 版本已提交，且是在试图创建之前提交的，可见

## 更新逻辑

事务更新逻辑执行规则是：先读后写，而读，只能读当前的值，在案例中就是事务B更新时
读到的k值为2，如果是读到了历史版本则会导致，事务C的更新失效
