# Transaction

## 简介
+ 为了确保逻辑的成功
+ 一组操作中包含多个单一的逻辑，只要有一个没有成功，所有的数据都会回到初始状态（回滚）

## 特性

### 原子性
+ 事务中包含的逻辑不可分割
### 一致性
+ 事务执行前后数据保持一致
### 隔离性
+ 事务执行期间不受到其他事务影响
### 持久性
+ 事务执行成功，数据应持久保存到硬盘上

## MySQL命令行操作

### 关闭/开启自动提交
```
show variables like ‘%commit%’
set autocommit off
```
1. 开启transaction
> start transaction

2. 提交或者回滚结束transaction
> commit   
> rollback

## 代码操作
```
Class.forName(driverClass);
Connection conn=DriverManager.getConnection(url,username,password);
conn.setAutoCommit(false);
```
+ 成功：提交数据
> conn.commit();
+ 失败：执行回滚
> conn.rollback();
+ transaction只针对调用它的conn

## 安全隐患

### 读
+ 脏读
> 一个数据读到了另一个未提交的数据   
> 同时开启两个数据库窗口，一个开启事务，更改不提交，另一个窗口可以读到未提交的数据
+ 不可重复读
> 一个窗口在transaction中修改了数据，导致另一个窗口前后读到的数据不一致
+ 幻读
> 一个事物读到了别的事物插入的数据

### 写
#### 丢失更新
> 两个事务不同时间修改一个数据（非序列化），会造成上次修改无效
#### 解决方法
1. 悲观锁：认为一定会出现丢失更新   

> `select * from 表名 for update;`    
> A事务修改时，B事务会卡住

2. 乐观锁：认为一定不会出现丢失更新   
> 表中添加一个新列version，A更新数据同时也更新version，B事务更新数据之前查询version是否相同，不同就重新查询

## 隔离级别
1. Read Uncommitted
> 解决脏读   

2. Read Committed
> 解决不可重复读

3. Repeatable Read
> 解决幻读

4. Serializable
> 级别最高，可以隔离上述问题，但是效率过低
### 查询及修改隔离级别
+ 查询：select @@tx_isolation
+ 修改：set session transaction isolation level 隔离级别
