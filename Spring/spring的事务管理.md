# Spring的事务管理
## Spring的事务管理API

### PlatformTransactionManager
+ spring用于管理事务的真正对象
   + DataSourceTransactionManager
   > 底层使用JDBC管理事务
   + HibernateTransactionManager
   > 底层使用Hibernate管理事务
   
### TransactionDefinition
+ 用于管理事务的相关信息
   + 隔离级别
   + 超时信息
   + 传播行为
   + ...
   
### TransactionStatus
+ 用于记录事务管理过程中事务的状态

## spring事务的传播行为
**简介：** 用于管理复杂业务中不同方法相互调用的问题
### Spring提供了7中事务传播行为
#### 保证多个操作在同一个事务中
假设方法B中使用了方法A
+ propagation_required
> 默认值，如果A中有事务，就使用A的事务，没有就创建一个事务，将操作包含起来
+ propagation_supports
> 如果A中有事务，就使用A的事务，没有就不使用事务
+ propagation_mandatory
> 如果A中有事务，就使用A的事务，没有就抛出异常